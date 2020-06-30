package com.gxn.diamond.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.gxn.diamond.common.helper.HttpClientUtil;
import com.gxn.diamond.dao.UserDAO;
import com.gxn.diamond.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    @Autowired
    UserDAO userDAO;

    @Cacheable(value = "30",key = "'User'+#user.openId")
    public User getUser(User user){
        System.out.println(user.key());
        System.out.println("get user from mysql");
        User user1=userDAO.getUser(user);
        return user1;
    }

   @CachePut(value = "30",key = "'User'+#openId")
    public User updateMobile(String openId,String mobile){
       System.out.println("update Mobile");
       userDAO.updateMobile(openId,mobile);
       User user=new User();
       user.setOpenId(openId);
       return userDAO.getUser(user);
   }

   public String addUser(User user){
        try{
        if(userDAO.getUser(user)==null){
            int i=userDAO.addUser(user);
            if(i>0){
                return "add user success!";
            }
            else {
                return "add user fail!";
            }
        }else{
            int i=userDAO.updateUser(user);
            if(i>0){
                return "update user success!";
            }
            else {
                return "update user fail";
            }
        }
        }
        catch (Exception e){
            e.printStackTrace();
            return "add user fail!";
        }
   }

    public JSONObject getOpenId(String code){
        String url="https://api.weixin.qq.com/sns/jscode2session?appid=wxaa4696b6ff0a313a&secret=baf00005f46fbeed93261c99e48d497e&js_code=CODE&grant_type=authorization_code";
        return HttpClientUtil.doGet(url.replace("CODE",code));
    }





}
