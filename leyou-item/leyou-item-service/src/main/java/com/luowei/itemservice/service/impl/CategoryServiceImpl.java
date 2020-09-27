package com.luowei.itemservice.service.impl;

import com.luowei.itempojo.Category;
import com.luowei.itemservice.mapper.CategoryMapper;
import com.luowei.itemservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据parentId查询子类目
     * @param pid
     * @return
     */
    @Override
    public List<Category> queryCategoriesByPid(Long pid) {
        Category record = new Category();
        record.setParentId(pid);
        return categoryMapper.select(record);
    }

    @Override
    public List<String> queryNamesByIds(List<Long> asList) {
        List<Category> categories = categoryMapper.selectByIdList(asList);
        ArrayList<String> strings = new ArrayList<>();
        for (Category category: categories){
            strings.add(category.getName());
        }
        return strings;
    }
}
