package com.gxn.diamond.service.impl;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileService {

    public File getImgMark(String path){
        File file=new File(path);
        return file;
    }
}
