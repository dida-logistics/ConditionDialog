package com.tick.conditiondialog;

/**
 * 车型实体类
 * Created by wangcheng on 2017/11/13.
 */

public class VehicleType extends ConditionEntity implements Cloneable {

    private String vehicleType;

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String getValue() {
        return getVehicleType();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        VehicleType vehicleType = null;
        try {
            vehicleType = (VehicleType) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return vehicleType;
    }
}
