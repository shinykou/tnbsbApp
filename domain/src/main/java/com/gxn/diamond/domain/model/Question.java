package com.gxn.diamond.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    int id;
    String question;
    String qname;
    String itemIds;
    String modified;
    String created;
    int questionType;



}
