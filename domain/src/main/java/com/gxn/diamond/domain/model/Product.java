package com.gxn.diamond.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    String title,imgUrls,name,content;
    int typeId,status;
    float price;
}
