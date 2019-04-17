package com.gxn.diamond.dao;

import com.gxn.diamond.domain.model.Image;
import com.gxn.diamond.domain.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ImageDAO {
    @Select({
            "SELECT * FROM images WHERE app_key = #{appKey} and type = #{type}"
    })
    List<Image> getImageList(@Param("appKey") String appKey, @Param("type") String type);

    @Insert({
            "insert into images (app_key,type,name,image_url) values (#{appKey},#{type},#{name},#{imageUrl})"
    })
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int addImage(Image image);
}
