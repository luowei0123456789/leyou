package com.luowei.itempojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_stock")
public class Stock{

    @Id
    private Long skuId;

    private Integer seckillStock;

    private Integer seckillTotal;

    private Integer stock = 9999;
}
