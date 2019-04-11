package com.gxn.diamond.provider.controller;


import com.gxn.diamond.domain.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value="/getuser")
     public User getUserByOpenid(@RequestParam(value = "openId",required = true) String openId){
         return new User(openId,null,null,null);
     }

}
