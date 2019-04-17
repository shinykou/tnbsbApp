package com.gxn.diamond.provider.controller;

import com.gxn.diamond.domain.model.User;
import com.gxn.diamond.service.ServiceImpl;
import com.gxn.diamond.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ServiceImpl service;


    @RequestMapping(value="/getuser")
    public User getUser(User user){
        User user1=userService.getUser(user);
        return user1;
    }


    @RequestMapping(value="/updatemobile")
    public User updateMobile(@RequestParam(value = "openId") String openId, @RequestParam(value = "mobile") String mobile){
        User user=userService.updateMobile(openId,mobile);
        return user;
    }

    @RequestMapping(value = "/adduser")
    public String addUser(User user){
        return userService.addUser(user);

    }




}
