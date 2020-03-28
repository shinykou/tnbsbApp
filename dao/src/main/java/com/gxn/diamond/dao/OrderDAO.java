package com.gxn.diamond.dao;

import com.gxn.diamond.domain.model.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderDAO {

    @Select("select * from order_detail where id=#{id} ")
    Order getOrderById(long id);

    @Insert("insert into order_detail (user_id,status,address_id,product_ids,product_names,product_imgs,numbers,price,discount_price,pay_price,created,modified) values" +
            "(#{userId},0,#{addressId},#{productIds},#{productNames},#{productImgs},#{numbers},#{price},#{discountPrice},#{payPrice},now(),now())")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    boolean addOrder(Order order);

    @Select("select * from order_detail where user_id=#{userId} ")
    List<Order> getOrderListByUser(@Param(value = "userId") int userId);

    @Update("update order_detail set status=#{status} , modified=now() where id=#{id}")
    boolean updateOrderStatus(@Param(value = "id") long id, @Param(value = "status") int status);

}
