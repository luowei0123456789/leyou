package com.luowei.itemservice.service;

import com.luowei.itempojo.Category;

import java.util.List;

public interface CategoryService {

    List<Category> queryCategoriesByPid(Long pid);

    List<String> queryNamesByIds(List<Long> asList);
}
