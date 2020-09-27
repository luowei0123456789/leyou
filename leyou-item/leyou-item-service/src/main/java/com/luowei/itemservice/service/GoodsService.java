package com.luowei.itemservice.service;

import com.github.pagehelper.PageInfo;
import com.luowei.itempojo.bo.SpuBo;

public interface GoodsService {
    PageInfo<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows);
}
