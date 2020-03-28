package com.gxn.diamond.provider.controller;

import com.gxn.diamond.domain.form.LocationForm;
import com.gxn.diamond.domain.model.Address;
import com.gxn.diamond.domain.model.User;
import com.gxn.diamond.service.ServiceImpl;
import com.gxn.diamond.service.impl.LocationServiceImpl;
import com.gxn.diamond.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    LocationServiceImpl locationService;



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


    ///////////////////////////地址//////////////////////////////////

    @RequestMapping(value = "/addadressbyuser")
    public String addAddressByUser(LocationForm locationForm){
        if(locationService.addNewLocation(locationForm)){
            return "add address success!";
        }else return "add address fail!";
    }

    @RequestMapping(value = "/deleteaddressbyid")
    public String deleteAddressById(@RequestParam(value = "id") int id){
        if(locationService.deleteLocation(id)){
            return "delete address success!";
        }else return "delete address fail!";
    }

    @RequestMapping(value = "/getaddressbyuser")
    public List<Address> getAddressByUserId(@RequestParam(value = "userId")int userId){
        return locationService.getAddressByUserId(userId);
    }

    @RequestMapping(value = "/updateaddress")
    public String updateLocation(LocationForm locationForm){
        if(locationService.updateLocation(locationForm)){
            return "update address success!";
        }else{
            return "update address fail!";
        }
    }

    @RequestMapping(value = "/setdefaultaddress")
    public String setDefaultAddress(@RequestParam(value = "id")int id,@RequestParam(value = "userId") int userId){
        if(locationService.setDefaultAddress(id,userId)){
            return "set default address success!";
        }else return "set default address fail!";
    }

    @RequestMapping(value = "/getdefaultaddress")
    public List<Address> getDefaultAddress(@RequestParam(value = "userId") int userId){
        return locationService.getDefaultAddress(userId);
    }

    @RequestMapping(value = "/getaddressbyid")
    public Address getAddressById(@RequestParam(value = "id") int id){
        return locationService.getAddressById(id);
    }



}
