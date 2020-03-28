package com.gxn.diamond.service.impl;

import com.gxn.diamond.dao.IShoppingCarDAO;
import com.gxn.diamond.domain.model.ShopingCar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author GXN
 * @version ${version}
 * @createdDate 2019/4/22
 */
@Slf4j
@Service
public class ProductShoppingService {

    @Autowired private IShoppingCarDAO shoppingCarDAO;
    @Autowired private ProductService productService;

    public boolean addProduct2ShopCar(int userId,int productId,int num){
        try{
            Integer id = shoppingCarDAO.getByUserIdProduct(userId,productId);
            if ( id !=null)shoppingCarDAO.updateNum(id,num);
            return shoppingCarDAO.add2ShopCar(new ShopingCar(userId,productId,num,productService.getById(productId+"").get(0)));
        }catch (Exception e){
            log.error("addProduct",e);
        }
        return false;
    }

    public List<ShopingCar> queryList(int userId){
        return shoppingCarDAO.queryList(userId);
    }

    public boolean delete(int userId,int productId){
        return shoppingCarDAO.deleteProduct(userId,productId);
    }

    public boolean changeNum(int userId,int productId,int addNum){
        return shoppingCarDAO.changeProductNum(userId,productId,addNum);
    }
}
