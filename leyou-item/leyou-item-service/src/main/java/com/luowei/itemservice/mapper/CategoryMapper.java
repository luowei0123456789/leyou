package com.luowei.itemservice.mapper;

import com.luowei.itempojo.Category;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category>, SelectByIdListMapper<Category,Long> {
}
