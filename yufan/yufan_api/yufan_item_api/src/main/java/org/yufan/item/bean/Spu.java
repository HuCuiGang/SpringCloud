package org.yufan.item.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@Table(name = "yf_spu")
public class Spu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long brandId;
    private Long cid1;
    private Long cid2;
    private Long cid3;
    private String title;

    private String subTitle;
    private Boolean saleable;
    private Boolean valid;

    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date lastUpdateTime;



}
