package com.gxn.diamond.domain.model;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String openid;
    String mobile;
    String nickName;
    String imageUrl;
}
