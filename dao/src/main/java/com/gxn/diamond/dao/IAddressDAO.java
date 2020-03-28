package com.gxn.diamond.dao;

import com.gxn.diamond.domain.form.LocationForm;
import com.gxn.diamond.domain.model.Address;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface IAddressDAO {


    @Select("SELECT " +
            "  *  " +
            "FROM " +
            "   address " +
            "WHERE " +
            "   user_id=#{userId} order by id desc"
    )
    List<Address> getByUserId(@Param("userId") int userId);

    @Select("SELECT " +
            "  *  " +
            "FROM " +
            "   address " +
            "WHERE " +
            "   id=#{id}"
    )
    Address getById(@Param("id") int id);

    @Insert("INSERT " +
            "  INTO " +
            " address " +
            " (" +
            "  name," +
            "  phone," +
            "  area_id," +
            "  user_id," +
            "  street," +
            "  modified," +
            "  created" +
            " )" +
            " VALUES " +
            "  (" +
            "   #{name}," +
            "   #{phone}," +
            "   #{areaId}," +
            "   #{userId}," +
            "   #{street}," +
            "   NOW()," +
            "   NOW()" +
            " ) "
    )
    void addAddress(LocationForm locationForm);

    @Update("UPDATE " +
            "  address " +
            "SET " +
            "   name = #{name}," +
            "   phone = #{phone}," +
            "   area_id=#{areaId}," +
            "   street = #{street}," +
            "   modified = NOW() " +
            "WHERE " +
            "     id = #{id} " +
            " AND user_id=#{userId} "
    )
    boolean update(LocationForm locationForm);

    @Delete("DELETE " +
            "  FROM " +
            "     address " +
            "WHERE  " +
            "   id= #{id} ")
    boolean deleteById(int id);

    @Update("update address set is_first=1 where id=#{id} and user_id=#{userId}")
    boolean setDefaultAddress1(@Param(value = "id") int id,@Param(value = "userId") int userId);

    @Update("update address set is_first=0 where id <>#{id} and user_id=#{userId}")
    boolean setDefaultAddress2(@Param(value = "id") int id,@Param(value = "userId") int userId);

    @Select("select * from address where user_id=#{userId} and is_first=1")
    List<Address> getDefaultAddress(@Param(value = "userId") int userId);

}
