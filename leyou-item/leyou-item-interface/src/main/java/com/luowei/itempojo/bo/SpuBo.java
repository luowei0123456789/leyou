package com.luowei.itempojo.bo;

import com.luowei.itempojo.Sku;
import com.luowei.itempojo.Spu;
import com.luowei.itempojo.SpuDetail;
import lombok.Data;

import java.util.List;

@Data
public class SpuBo extends Spu {
    private String cname;

    private String bname;

    private SpuDetail spuDetail;

    private List<Sku> skus;
}
