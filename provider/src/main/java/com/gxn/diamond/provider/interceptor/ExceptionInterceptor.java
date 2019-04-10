package com.gxn.diamond.provider.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor {

    @ExceptionHandler(Exception.class)
    public String handleAll(Throwable t){
        //t.printStackTrace();
        return t.getMessage();
    }
}
