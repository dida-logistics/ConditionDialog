package com.tick.conditiondialog;

import java.util.ArrayList;

/**
 * 车辆条件实体类
 * Created by wangcheng on 2017/11/13.
 */

public class VehicleCondition {
    private ArrayList<VehicleMeter> vehicleMeters;
    private ArrayList<VehicleType> vehicleTypes;

    public ArrayList<VehicleMeter> getVehicleMeters() {
        if (vehicleMeters == null) {
            vehicleMeters = new ArrayList<>();
        }
        return vehicleMeters;
    }

    public void setVehicleMeters(ArrayList<VehicleMeter> vehicleMeters) {
        this.vehicleMeters = vehicleMeters;
    }

    public ArrayList<VehicleType> getVehicleTypes() {
        if (vehicleTypes == null) {
            vehicleTypes = new ArrayList<>();
        }
        return vehicleTypes;
    }

    public void setVehicleTypes(ArrayList<VehicleType> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    public VehicleCondition cloneCondition() {
        VehicleCondition condition = new VehicleCondition();
        ArrayList<VehicleMeter> vehicleMeters = new ArrayList<>();
        ArrayList<VehicleType> vehicleTypes = new ArrayList<>();
        try {
            for (VehicleMeter vehicleMeter : getVehicleMeters()) {
                vehicleMeters.add((VehicleMeter) vehicleMeter.clone());
            }
            for (VehicleType vehicleType : getVehicleTypes()) {
                vehicleTypes.add((VehicleType) vehicleType.clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        condition.setVehicleMeters(vehicleMeters);
        condition.setVehicleTypes(vehicleTypes);
        return condition;
    }
}
