package com.gxn.diamond.domain.vo;

import com.google.common.collect.Lists;
import com.gxn.diamond.domain.model.Order;
import com.gxn.diamond.domain.model.Product;

import java.util.List;


public class OrderVO {
    Order order;
    List<Product> products;

    public OrderVO(Order order) {
        this.order = order;
        setProducts(order);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(Order order) {
        List<Product> products= Lists.newArrayList();
        String[] productIds=order.getProductIds().split(",");
        String[] productNames=order.getProductNames().split(",");
        String[] productImgs=order.getProductImgs().split(",");
        String[] numbers=order.getNumbers().split(",");
        for(int i=0;i<productIds.length;i++){
            Product product=new Product();
            product.setName(productNames[i]);
            product.setImgUrls(productImgs[i]);
            product.setInventory(Integer.parseInt(numbers[i]));
            products.add(product);
        }
        this.products = products;
    }
}
