package com.gxn.diamond.dao;

import com.gxn.diamond.domain.form.LocationForm;
import com.gxn.diamond.domain.model.Address;
import org.apache.ibatis.annotations.*;

import java.util.Set;


public interface IAddressDAO {


    @Select("SELECT " +
            "  *  " +
            "FROM " +
            "   address " +
            "WHERE " +
            "   user_id=#{userId}"
    )
    Set<Address> getByUserId(@Param("userId") int userId);

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
            "   ared_id=#{aredId}," +
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
}
