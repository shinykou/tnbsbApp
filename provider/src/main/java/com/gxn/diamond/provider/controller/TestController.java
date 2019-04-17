package com.gxn.diamond.provider.controller;



import com.gxn.diamond.domain.model.Image;
import com.gxn.diamond.service.KafKaService;
import com.gxn.diamond.service.impl.FileService;
import com.gxn.diamond.service.impl.ImageSrevice;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/test")
public class TestController {



    @Autowired
    KafKaService kafKaService;
    @RequestMapping(value = "/kafkasend")
    public String testkafka(@RequestParam(value = "message") String message, @RequestParam(value = "topic") String topic){
        try{
            kafKaService.produce(message,topic);
            return "kafka producer send success";
        }catch (Exception e){
            e.printStackTrace();
            return "kafka producer send fail";
        }
    }

    @Autowired
    FileService fileService;
    @RequestMapping(value="/getBiImg")
    public void getBiImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String path=request.getParameter("path");
        File file=fileService.getImgMark(path);//获取图片这个文件
        @Cleanup InputStream is=new FileInputStream(file);
        BufferedImage bi= ImageIO.read(is);
        ImageIO.write(bi, "JPEG", response.getOutputStream());
    }


    @Autowired
    ImageSrevice imageSrevice;
    @RequestMapping(value = "/getimages")
    public List<Image> getImages(@RequestParam(value = "appKey") String appkey, @RequestParam(value = "type") String type){
        List<Image> images= Arrays.asList();
        try{
            images=imageSrevice.getImages(appkey,type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return images;
    }






}
