package com.tender.hellojack.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by boyu on 2017/11/15.
 */

public class RegionBean implements IPickerViewData {

    private long id;

    private int iconId;

    private String regionName;

    public RegionBean(long id, int iconId, String regionName) {
        this.id = id;
        this.iconId = iconId;
        this.regionName = regionName;
    }

    public long getId() {
        return id;
    }

    public int getIconId() {
        return iconId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public String getPickerViewText() {
        return "id: " + String.valueOf(id)+ ",name: " + regionName;
    }
}
