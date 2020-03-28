package com.gxn.diamond.provider.controller;

import com.google.common.collect.Lists;
import com.gxn.diamond.domain.model.Product;
import com.gxn.diamond.domain.model.ProductType;
import com.gxn.diamond.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProuductController {


    @Autowired
    ProductService productService;


    @RequestMapping(value = "/getproducttype")
    public List<ProductType> getAllProductType(){
        List<ProductType> productTypes= Arrays.asList();
        try{
            productTypes=productService.getAllProductType();
        }catch (Exception e){
            e.printStackTrace();
        }
        return productTypes;
    }

    @RequestMapping(value = "/getproductbytype")
    public List<Product> getProductByType(
            @RequestParam(value = "typeId") Integer typeId){
        List<Product> products= Arrays.asList();
        try{
            products=productService.getProductByType(typeId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }

    @RequestMapping(value = "/getproductbyid")
    public List<Product> getProductById(
            @RequestParam(value = "id") String id){
        List<Product> products= Lists.newArrayList();
        try{
            products=productService.getById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }

    @RequestMapping(value = "/addproduct")
    public String addProduct(Product product){
        return productService.addProduct(product);
    };
}
