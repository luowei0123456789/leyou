package com.luowei.itemservice.service.impl;

import com.luowei.itempojo.SpecGroup;
import com.luowei.itempojo.SpecParam;
import com.luowei.itemservice.mapper.SpecGroupMapper;
import com.luowei.itemservice.mapper.SpecParamMapper;
import com.luowei.itemservice.service.SpecGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.provider.certpath.CertId;

import java.util.List;

@Service
public class SpecGroupServiceImpl implements SpecGroupService {
    @Autowired
    private SpecGroupMapper groupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 根据分类id查询分组
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return groupMapper.select(specGroup);
    }

    /** * 根据条件查询规格参数 * @param gid * @return */
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setGeneric(generic);
        param.setSearching(searching);
        return specParamMapper.select(param);
    }
}
