package com.gxn.diamond.service.impl;

import com.gxn.diamond.dao.ImageDAO;
import com.gxn.diamond.domain.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageSrevice {

    @Autowired
    ImageDAO imageDAO;
    public List<Image> getImages(String appKey,String type){
        return imageDAO.getImageList(appKey,type);
    }
}
