package com.gxn.diamond.domain.model;

/**
 * @author gaoxiaoning
 * @version ${version}
 * @createdDate 2019/4/11
 */
public class ShopingCar {
    private int id;
    private int userId;
    private int productId;
    private int num;
    private String kind;
    private float price;
    private float totalMoney;
    private String created;
    private String modied;

    public ShopingCar(int userId, int productId, int num,Product product) {
        this.userId = userId;
        this.productId = productId;
        this.num = num;
        this.kind = product.getTitle();
        this.price = product.getPrice();
        this.totalMoney = this.price*this.num;
    }
}
