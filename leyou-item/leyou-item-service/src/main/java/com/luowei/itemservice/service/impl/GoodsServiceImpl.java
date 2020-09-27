package com.luowei.itemservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luowei.itempojo.Sku;
import com.luowei.itempojo.Spu;
import com.luowei.itempojo.SpuDetail;
import com.luowei.itempojo.Stock;
import com.luowei.itempojo.bo.SpuBo;
import com.luowei.itemservice.mapper.*;
import com.luowei.itemservice.service.CategoryService;
import com.luowei.itemservice.service.GoodsService;
import com.sun.prism.impl.Disposer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Override
    public PageInfo<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        // 搜索条件
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }

        // 分页条件
        PageHelper.startPage(page, rows);

        // 执行查询
        List<Spu> spus = this.spuMapper.selectByExample(example);
        PageInfo<SpuBo> pageInfo = new PageInfo<>();
        PageInfo<Spu> pageInfo1 = new PageInfo<>(spus);

        List<SpuBo> spuBos = new ArrayList<>();
        spus.forEach(spu -> {
            SpuBo spuBo = new SpuBo();
            // copy共同属性的值到新的对象
            BeanUtils.copyProperties(spu, spuBo);
            // 查询分类名称
            List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(names, "/"));

            // 查询品牌的名称
            spuBo.setBname(this.brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());

            spuBos.add(spuBo);
        });

        pageInfo.setList(spuBos);
        pageInfo.setTotal(pageInfo1.getTotal());
        return pageInfo;
    }

    @Override
    public SpuDetail querySpuDetailBySpuId(Integer spuId) {
        SpuDetail spuDetail = this.spuDetailMapper.selectByPrimaryKey(spuId);
        return spuDetail;
    }

    @Override
    public List<Sku> querySkuBySpuId(Long spuId) {
        Sku record = new Sku();
        record.setSpuId(spuId);
        return this.skuMapper.select(record);
    }

    @Override
    public void saveGoods(SpuBo spuBo) {
        // 新增spu // 设置默认字段
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        this.spuMapper.insertSelective(spuBo);

        // 新增spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        this.spuDetailMapper.insertSelective(spuDetail);

        saveSkuAndStock(spuBo);
    }

    @Override
    @Transactional
    public void updateGoods(SpuBo spuBo) {
        // 查询以前sku
        List<Sku> skus = this.querySkuBySpuId(spuBo.getId()); // 如果以前存在，则删除
        if(!CollectionUtils.isEmpty(skus)) {
            List ids = skus.stream().map(s -> s.getId()).collect(Collectors.toList());
            Example example = new Example(Stock.class);
            example.createCriteria().andIn("skuId", ids);
            this.stockMapper.deleteByExample(example);
            // 删除以前的sku
            Sku record = new Sku();
            record.setSpuId(spuBo.getId());
            this.skuMapper.delete(record);
    }

        // 新增sku和库存
        saveSkuAndStock(spuBo);
        // 更新spu
        spuBo.setLastUpdateTime(new Date());
        spuBo.setCreateTime(null);
        spuBo.setValid(null);
        spuBo.setSaleable(null);
        this.spuMapper.updateByPrimaryKeySelective(spuBo);
        // 更新spu详情
        this.spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());
}

    private void saveSkuAndStock(SpuBo spuBo) {
        spuBo.getSkus().forEach(sku -> {
            // 新增sku
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());

            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insertSelective(sku);
            // 新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insertSelective(stock);
        });
    }

}
