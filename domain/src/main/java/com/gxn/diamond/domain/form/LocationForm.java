package com.gxn.diamond.domain.form;

import lombok.Data;

/**
 * @author GXN
 * @version ${version}
 * @createdDate 2019/4/12
 */
@Data
public class LocationForm {

    private int userId;
    private String name ;
    private String phone;
    private String street;
    private int areaId;

}
