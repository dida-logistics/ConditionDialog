package com.tick.conditiondialog.carrier;

import com.tick.conditiondialog.ConditionEntity;

/**
 * 承运商类型信息实体
 * Created by wangcheng on 2017/12/11.
 */

public class CarrierType extends ConditionEntity implements Cloneable{

    private String carrierType;

    public CarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    public CarrierType() {
    }

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    @Override
    public String getValue() {
        return getCarrierType();
    }

    @Override
    protected CarrierType clone() throws CloneNotSupportedException {
        CarrierType carrierType = null;
        try {
            carrierType = (CarrierType) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return carrierType;
    }
}
