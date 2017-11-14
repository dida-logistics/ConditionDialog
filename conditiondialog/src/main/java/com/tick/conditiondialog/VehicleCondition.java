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

    /**
     * 获取车型
     *
     * @return 车型
     */
    public String getVehicleType() {
        if (vehicleTypes != null && vehicleTypes.size() > 0) {
            StringBuilder type = new StringBuilder();
            for (int i = 0; i < vehicleTypes.size(); i++) {
                type.append(vehicleTypes.get(i).getValue());
                if (i != vehicleTypes.size() - 1) {
                    type.append(",");
                }
            }
            return type.toString();
        }
        return "";
    }

    /**
     * 获取车长
     *
     * @return 车长
     */
    public String getVehicleMeter() {
        if (vehicleMeters != null && vehicleMeters.size() > 0) {
            StringBuilder type = new StringBuilder();
            for (int i = 0; i < vehicleMeters.size(); i++) {
                type.append(vehicleMeters.get(i).getValue());
                if (i != vehicleMeters.size() - 1) {
                    type.append(",");
                }
            }
            return type.toString();
        }
        return "";
    }
}
