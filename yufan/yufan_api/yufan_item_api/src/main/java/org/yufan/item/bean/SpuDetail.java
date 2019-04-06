package org.yufan.item.bean;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Table(name = "yf_spu_detail")
public class SpuDetail {

    @Id
    private Long spuId;

    private String description;


    private String packingList;

    private String afterService;

}
