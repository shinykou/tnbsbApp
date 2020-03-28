package com.gxn.diamond.service.impl;

import com.gxn.diamond.dao.IQuestionDAO;
import com.gxn.diamond.domain.model.QResult;
import com.gxn.diamond.domain.model.Question;
import com.gxn.diamond.domain.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    IQuestionDAO questionDAO;

    public List<QuestionVO> getQuestionList(String questionIds){
        List<Question> questions=questionDAO.getQuestionsById(questionIds);
        System.out.println(questions);
        List<QuestionVO> questionVOS=new ArrayList<>();
        for(Question question:questions){
            QuestionVO questionVO=new QuestionVO();
            questionVO.setId(question.getId());
            questionVO.setQuestion(question.getQuestion());
            questionVO.setQname(question.getQname());
            questionVO.setOptionItems(questionDAO.getOptionItemsByQuestion(question.getId()));
            questionVO.setQuestionType(question.getQuestionType());
            questionVOS.add(questionVO);
        }

        return questionVOS;
    }

    public QResult saveResult(QResult qResult){
        qResult.setScore();
        questionDAO.saveResult(qResult);
        System.out.println(qResult);
        return qResult;
    }


}
