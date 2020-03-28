package com.gxn.diamond.provider.controller;


import com.gxn.diamond.domain.model.QResult;
import com.gxn.diamond.domain.model.Question;
import com.gxn.diamond.domain.vo.QuestionVO;
import com.gxn.diamond.service.impl.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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


}
