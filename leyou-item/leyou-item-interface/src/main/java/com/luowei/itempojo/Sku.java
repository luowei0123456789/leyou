package com.luowei.itempojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "tb_sku")
public class Sku{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long spuId;

    private String title;

    private String images;

    private Long price;

    private String indexes;

    private String own_spec;

    private Boolean enable;

    private Date createTime;

    private Date lastUpdateTime;

    @Transient
    private Integer stock;
}
