package com.gxn.diamond.domain.model;

import lombok.Data;


@Data
public class Address {

    private int id;
    private String name ;
    private String phone;
    private int areaId;
    private int userId;
    private String street;
    private String created;
    private String modified;
}
