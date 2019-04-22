package com.gxn.diamond.domain.model;

import lombok.Data;

/**
 * @author gaoxiaoning
 * @version ${version}
 * @createdDate 2019/4/11
 */
@Data
public class Area {
    private int id;
    private String name;
    private int parentId;
    private Area parentArea;
    private int level=1;

    public void addLevel() {
        this.level++;
    }
}
