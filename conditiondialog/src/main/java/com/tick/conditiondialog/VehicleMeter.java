package com.tick.conditiondialog;

/**
 * 车长实体类
 * Created by wangcheng on 2017/11/13.
 */

public class VehicleMeter extends ConditionEntity implements Cloneable {

    private String vehicleMeter;

    public String getVehicleMeter() {
        return vehicleMeter;
    }

    public void setVehicleMeter(String vehicleMeter) {
        this.vehicleMeter = vehicleMeter;
    }

    @Override
    public String getValue() {
        return getVehicleMeter();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        VehicleMeter vehicleMeter = null;
        try {
            vehicleMeter = (VehicleMeter) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return vehicleMeter;
    }
}
