package com.gxn.diamond.dao;

import com.gxn.diamond.domain.model.ShopingCar;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author GXN
 * @version ${version}
 * @createdDate 2019/4/22
 */
public interface IShoppingCarDAO {

    @Insert("insert into shoping_car" +
            " (" +
            "  user_id," +
            "  product_id,num,price,total_money,kind,created,modified" +
            " )" +
            " values " +
            " (" +
            "  #{userId},#{productId},#{num},#{price},#{totalMoney},#{kind},now(),now() " +
            " ) "
    )
    boolean add2ShopCar(ShopingCar shopingCar);


    @Select("SELECT  id from " +
            " shoping_car where user_id=#{userId} and product_id=#{productId} "
    )
    Integer getByUserIdProduct(@Param("userId") int userId,@Param("productId") int productId);

    @Update("update shoping_car set num = num+${num} where id=#{id}")
    void updateNum(@Param("id") Integer id,@Param("num") int num);

    @Select("SELECT * FROM " +
            " shoping_car " +
            "where user_id=#{userId}")
    List<ShopingCar> queryList(int userId);

    @Delete("delete from " +
            " shoping_car where user_id=#{userId} and product_id=#{productId}"
    )
    boolean deleteProduct(int userId, int productId);

    @Update("UPDATE shoping_car " +
            " set num=num+${addNum} " +
            " where user_id=#{userId} and product_id=#{productId} "
    )
    boolean changeProductNum(int userId, int productId, int addNum);
}
