package com.gxn.diamond.dao;

import com.gxn.diamond.domain.model.Area;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author GXN
 * @version ${version}
 * @createdDate 2019/4/12
 */
@Component
public interface IAreaDAO {

    @Select("SELECT  " +
            "  * " +
            "FROM " +
            "  area " +
            "WHERE " +
            "  parent_id=#{parentId}"
    )
    Set<Area> getAllByParentId(int parentId);

    @Select("SELECT " +
            "   * " +
            "FROM " +
            "   area " +
            "WHERE  " +
            "   id = #{id}"
    )
    Area getById(int id);



}
