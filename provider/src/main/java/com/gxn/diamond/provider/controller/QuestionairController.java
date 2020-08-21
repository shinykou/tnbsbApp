package com.gxn.diamond.provider.controller;


import com.gxn.diamond.domain.model.QResult;
import com.gxn.diamond.domain.model.Question;
import com.gxn.diamond.domain.vo.QuestionVO;
import com.gxn.diamond.service.impl.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionairController {

    @Autowired
    QuestionService questionService;

    @RequestMapping(value = "/getquestionlist")
    public List<QuestionVO> getQuestionlist(@RequestParam(value = "questionIds") String questionIds){
        return questionService.getQuestionList(questionIds);
    }

    @RequestMapping(value = "/saveResult")
    public QResult saveResult(QResult qResult){
        return questionService.saveResult(qResult);
    }


    @RequestMapping(value = "/downExcel", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String downExcel(HttpServletResponse response) throws UnsupportedEncodingException {

        String fileName = "糖尿病肾病问卷结果数据.csv";

        // 如果文件名不为空，则进行下载
        if (fileName != null) {
            OutputStream os = null;
            try {
                response.reset();
                response.setContentType("application/octet-stream; charset=utf-8");
                response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(),"ISO8859-1"));
                StringBuffer sb=new StringBuffer("phone,openId,age,gender,smoke,gaoxueya,tangniaobing,eye,height,weight,bmi,hba1c,sbp,hdlc,tg,ldlc,uacr,scr,sua,score,modified,created\n");
                for(QResult qResult:questionService.getAllResults()){
                    sb.append(qResult.getPhone()+",");
                    sb.append(qResult.getOpenId()+",");
                    sb.append(qResult.getAge()+",");
                    sb.append(qResult.getGender()+",");
                    sb.append(qResult.getSmoke()+",");
                    sb.append(qResult.getGaoxueya()+",");
                    sb.append(qResult.getTangniaobing()+",");
                    sb.append(qResult.getEye()+",");
                    sb.append(qResult.getHeight()+",");
                    sb.append(qResult.getWeight()+",");
                    sb.append(qResult.getBmi()+",");
                    sb.append(qResult.getHba1c()+",");
                    sb.append(qResult.getSbp()+",");
                    sb.append(qResult.getHdlc()+",");
                    sb.append(qResult.getTg()+",");
                    sb.append(qResult.getLdlc()+",");
                    sb.append(qResult.getUacr()+",");
                    sb.append(qResult.getScr()+",");
                    sb.append(qResult.getSua()+",");
                    sb.append(qResult.getScore()+",");
                    sb.append(qResult.getCreated()+",");
                    sb.append(qResult.getModified()+",");
                    sb.append('\n');
                }

                byte[] bytes = sb.toString().getBytes("GBK");
                os = response.getOutputStream();
                // 将字节流传入到响应流里,响应到浏览器
                os.write(bytes);
                os.close();
            } catch (Exception ex) {

                throw new RuntimeException("导出失败");
            }finally {
                try {
                    if (null != os) {
                        os.close();
                    }
                } catch (IOException ioEx) {

                }
            }
        }
        return "";
    }


}
