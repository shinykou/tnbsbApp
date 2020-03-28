package com.gxn.diamond.provider.controller;

import com.gxn.diamond.service.impl.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    InventoryService inventoryService;

    @RequestMapping(value = "/paysuccess")
    public boolean paysuccess(@RequestParam(value = "orderId") long orderId){
        return inventoryService.changeInventoryWhenPay(orderId);
    }

}
