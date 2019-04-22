package com.gxn.diamond.domain.vo;

import com.gxn.diamond.domain.model.Address;
import com.gxn.diamond.domain.model.Area;

import java.util.Optional;

/**
 * @author GXN
 * @version ${version}
 * @createdDate 2019/4/12
 */
public class LocationVO {

    private int id;
    private String name ;
    private String phone;
    private String street;
    private String created;
    private String modified;
    private String province;
    private String city;
    private String region;

    public LocationVO(Address address, Optional<Area> areaLinkByChild) {
        this.id = address.getId();
        this.name = address.getName();
        this.phone = address.getPhone();
        this.street = address.getStreet();
        this.created = address.getCreated();
        this.modified = address.getModified();
        if ( areaLinkByChild.isPresent() ){
            province = areaLinkByChild.get().getParentArea().getParentArea().getName();
            city = areaLinkByChild.get().getParentArea().getName();
            region = areaLinkByChild.get().getName();
        }
    }
}
