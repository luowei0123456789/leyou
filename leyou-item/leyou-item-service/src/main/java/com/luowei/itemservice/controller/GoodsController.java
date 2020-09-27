package com.luowei.itemservice.controller;

import com.github.pagehelper.PageInfo;
import com.luowei.itempojo.Sku;
import com.luowei.itempojo.SpuDetail;
import com.luowei.itempojo.bo.SpuBo;
import com.luowei.itemservice.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("spu/page")
    public ResponseEntity<PageInfo<SpuBo>> querySpuBoByPage(
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "saleable", required = false)Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows
    ){
        PageInfo<SpuBo> pageResult = this.goodsService.querySpuBoByPage(key, saleable, page, rows);
        if(CollectionUtils.isEmpty(pageResult.getList())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo){
        this.goodsService.saveGoods(spuBo);
       return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        this.goodsService.updateGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable Integer spuId){
        SpuDetail spuDetail = this.goodsService.querySpuDetailBySpuId(spuId);
        if (spuDetail==null){
            return  ResponseEntity.notFound().build();
        }
       return ResponseEntity.ok(spuDetail);
    }

    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam Long spuId){
        List<Sku> skus= this.goodsService.querySkuBySpuId(spuId);
        if (CollectionUtils.isEmpty(skus)){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skus);
    }
}
