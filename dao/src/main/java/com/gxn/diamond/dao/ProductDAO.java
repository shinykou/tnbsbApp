package com.gxn.diamond.dao;



import com.gxn.diamond.domain.model.Product;
import com.gxn.diamond.domain.model.ProductType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface ProductDAO {

    @Select({
            "SELECT * FROM product_type WHERE  id = #{id} limit 1"
    })
    ProductType getProductTypeById(@Param("id") int id);

    @Select({
            "SELECT * FROM product_type where status=1"
    })
    List<ProductType> getAllProductType();

    @Select({
            "SELECT * FROM product_detail where type_id=#{typeId} and status=1"
    })
    List<Product> getProductList(@Param("typeId") int typeId);


    @Select("SELECT * FROM product_detail WHERE  id = #{id} "
    )
    Product getProductById(int id);
}
