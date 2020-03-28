package com.gxn.diamond.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    long id;
    int userId,status,addressId;
    String productIds,productNames,productImgs,numbers;
    double price,discountPrice,payPrice;

}
