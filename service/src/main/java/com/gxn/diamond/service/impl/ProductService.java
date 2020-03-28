package com.gxn.diamond.service.impl;

import com.gxn.diamond.dao.ProductDAO;
import com.gxn.diamond.domain.model.Product;
import com.gxn.diamond.domain.model.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDAO productDAO;

    public List<ProductType> getAllProductType(){
        return productDAO.getAllProductType();
    }

    public List<Product> getProductByType(int typeId){
        return productDAO.getProductList(typeId);
    }

    public List<Product> getById(String id){
        return productDAO.getProductById(id);
    }

    public String addProduct(Product product){
        int i=0;
        i=productDAO.addProduct(product);
        if(i>0){
            return "add product success!";
        }else return "add product fail!";

    }


}
