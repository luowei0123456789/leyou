package com.luowei.itemservice.service;

import com.github.pagehelper.PageInfo;
import com.luowei.itempojo.Sku;
import com.luowei.itempojo.SpuDetail;
import com.luowei.itempojo.bo.SpuBo;

import java.util.List;

public interface GoodsService {
    PageInfo<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows);

    SpuDetail querySpuDetailBySpuId(Integer spuId);

    List<Sku> querySkuBySpuId(Long spuId);

    void saveGoods(SpuBo spuBo);

    void updateGoods(SpuBo spuBo);
}
