package com.luowei.itemservice.service;

import com.luowei.itempojo.SpecGroup;
import com.luowei.itempojo.SpecParam;

import java.util.List;

public interface SpecGroupService {
    List<SpecGroup> queryGroupsByCid(Long cid);

    List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching);
}
