package com.gxn.diamond.domain.vo;

import com.gxn.diamond.domain.model.OptionItem;
import lombok.Data;

import java.util.List;

@Data
public class QuestionVO {
    int id;
    String question;
    String qname;
    int questionType;
    List<OptionItem> optionItems;
    String modified;
    String created;
}
