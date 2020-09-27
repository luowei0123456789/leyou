package com.luowei.itemservice.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.luowei.itempojo.Brand;
import com.luowei.itempojo.Category;

import java.util.List;

public interface BrandService {
    PageInfo<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    void saveBrand(Brand brand, List<Long> cids);

    List<Brand> queryByBrandId(Long bid);

    List<Brand> queryBrandsByCid(Long cid);
}
