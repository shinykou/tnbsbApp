package com.gxn.diamond.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.gxn.diamond.dao.IAddressDAO;
import com.gxn.diamond.dao.IAreaDAO;
import com.gxn.diamond.domain.form.LocationForm;
import com.gxn.diamond.domain.model.Address;
import com.gxn.diamond.domain.model.Area;
import com.gxn.diamond.domain.vo.LocationVO;
import com.gxn.diamond.service.ILocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author GXN
 * @version ${version}
 * @createdDate 2019/4/11
 */
@Service
@Slf4j
public class LocationServiceImpl implements ILocationService {

    @Autowired private IAddressDAO addressDAO;
    @Autowired private IAreaDAO areaDAO;

//    @Override
//    public List<LocationVO> getUserLocationList(int userId) {
//        try{
//            Set<Address> allAddress = addressDAO.getByUserId(userId);
//            return allAddress.stream().map(address -> new LocationVO(address,getAreaLinkByChildId(address.getAreaId()))).collect(Collectors.toList());
//        }catch (Exception e){
//            log.error("getUserLocationList",e);
//            return Lists.newArrayList();
//        }
//    }

    public List<Address> getAddressByUserId(int userId){
        List<Address> address= Lists.newArrayList();
        try{
            address = addressDAO.getByUserId(userId);
        }catch (Exception e){
            log.error("getAddressByUserId",e);
        }
        return address;

    }

    @Override
    public boolean addNewLocation(LocationForm locationForm) {
        try{
//            getAreaLinkByChildId(locationForm.getAreaId());
            addressDAO.addAddress(locationForm);
            return true;
        }catch (Exception e){
            log.error("addNewLocation",e);
            return false;
        }
    }

    @Override
    public boolean updateLocation(LocationForm locationForm) {
        //getAreaLinkByChildId(locationForm.getAreaId());
        return addressDAO.update(locationForm);
    }

    @Override
    public boolean deleteLocation(int id) {
        return addressDAO.deleteById(id);
    }

    /*
     获取三层地理信息
     */
    @Override
    public Optional<Area> getAreaLinkByChildId(int areaId) {
        Area firstChild = areaDAO.getById(areaId);
        try{
            Area son = firstChild;
            while (  son !=null && son.getParentId()>0 ){
                son = areaDAO.getById(son.getParentId());
                firstChild.setParentArea(son);
                firstChild.addLevel();
            }
            return Optional.ofNullable(firstChild);
        }finally {
            Assert.state(null != firstChild && firstChild.getLevel()!=3,"area not true");
        }
    }

    public boolean setDefaultAddress(int id,int userId){
        return(addressDAO.setDefaultAddress1(id,userId)&&addressDAO.setDefaultAddress2(id,userId));
    }

    public List<Address> getDefaultAddress(int userId){
        return addressDAO.getDefaultAddress(userId);
    }

    public Address getAddressById(int id){
        return addressDAO.getById(id);
    }



}
