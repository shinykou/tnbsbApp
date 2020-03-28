package com.gxn.diamond.service;

import com.gxn.diamond.domain.form.LocationForm;
import com.gxn.diamond.domain.model.Area;
import com.gxn.diamond.domain.vo.LocationVO;

import java.util.List;
import java.util.Optional;

/**
 * @author GXN
 * @version ${version}
 * @createdDate 2019/4/12
 */
public interface ILocationService {



    //List<LocationVO> getUserLocationList(int userId);

    boolean addNewLocation(LocationForm locationForm);

    boolean updateLocation(LocationForm locationForm);

    boolean deleteLocation(int id);

    Optional<Area> getAreaLinkByChildId(int areaId);

}
