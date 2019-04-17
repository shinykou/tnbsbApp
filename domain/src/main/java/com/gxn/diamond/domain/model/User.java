package com.gxn.diamond.domain.model;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{
    Long id;
    String openId;
    String mobile;
    String nickName;
    String avatarUrl;
    String city;
    String province;
    String country;
    int gender;


    public String key() {
        return
                "id=" + id + '&' +
                "openId=" + openId + '&' +
                "mobile=" + mobile ;
    }
}
