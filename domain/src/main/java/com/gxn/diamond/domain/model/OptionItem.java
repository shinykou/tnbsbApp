package com.gxn.diamond.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionItem {
    int id;
    int questionId;
    String itemContent;
    int itemScore;
    String modified;
    String created;
    int optionType;

}
