package com.gxn.diamond.service.impl;


import com.gxn.diamond.dao.OrderDAO;
import com.gxn.diamond.dao.ProductDAO;
import com.gxn.diamond.domain.model.Order;
import com.gxn.diamond.domain.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {


     @Autowired
     OrderDAO orderDAO;
     @Autowired
     ProductDAO productDAO;

     final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Transactional(rollbackFor=RuntimeException.class)
    public boolean changeInventoryWhenPay(long orderId){
        try{
            boolean result=false;
            Order order=orderDAO.getOrderById(orderId);
            HashMap<Integer,Integer> map=new HashMap<>();
            String[] productIds=order.getProductIds().split(",");
            String[] numbers=order.getNumbers().split(",");
            for (int i=0;i<productIds.length;i++){
                map.put(Integer.parseInt(productIds[i]),Integer.parseInt(numbers[i]));
            }
            logger.info("订单购买："+map.toString());

            List<Product> products= productDAO.getProductById(order.getProductIds());

            logger.info("商品库存："+products);
            Optional<Boolean> isallsuccess=products.stream().map(product -> productDAO.updateInventoryById(product.getId(),product.getInventory()-map.get(product.getId()))).reduce((a, b)->a&&b);
            if(isallsuccess.isPresent()&&isallsuccess.get()==true){
                result= orderDAO.updateOrderStatus(orderId,1);
            }
            return result;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

    }



}
