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

    public Product getById(int id){
        return productDAO.getProductById(id);
    }


}
