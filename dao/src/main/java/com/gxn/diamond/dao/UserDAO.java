package com.gxn.diamond.dao;

import com.gxn.diamond.domain.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
public interface UserDAO {
    @Select({
            "SELECT * FROM user WHERE open_id = #{openId} or id = #{id} limit 1"
    })
    User getUser(User user);

    @Insert({
            "Insert into user (open_id,mobile,nick_name,avatar_url,gender,city,province,country) values(#{openId},#{mobile},#{nickName},#{avatarUrl},#{gender},#{city},#{province},#{country})"
    })
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int addUser(User user);

    @Update({
            "update user set mobile=#{mobile} where open_id=#{openId}"
    })
    int updateMobile(@Param("openId") String openId,@Param("mobile") String mobile);

    @Update({
            "update user set mobile=#{mobile}, nick_name=#{nickName}, avatar_url=#{avatarUrl},gender=#{gender},city=#{city},province=#{province},country=#{country} where open_id=#{openId}"
    })
    int updateUser(User user);



}
