package com.tick.conditiondialogdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.tick.conditiondialog.carrier.CarrierType;
import com.tick.conditiondialog.carrier.CarrierTypeCdSelector;
import com.tick.conditiondialog.carrier.CarrierTypeSelectListener;
import com.tick.conditiondialog.vehicle.VehicleCdSelector;
import com.tick.conditiondialog.vehicle.VehicleCondition;
import com.tick.conditiondialog.vehicle.VehicleConditionSelectListener;
import com.tick.conditiondialog.vehicle.VehicleMeter;
import com.tick.conditiondialog.vehicle.VehicleMeterSelector;
import com.tick.conditiondialog.vehicle.VehicleMetersSelectListener;
import com.tick.conditiondialog.vehicle.VehicleType;
import com.tick.conditiondialog.vehicle.VehicleTypeSelectListener;
import com.tick.conditiondialog.vehicle.VehicleTypeSelector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements VehicleConditionSelectListener,
        CarrierTypeSelectListener {

    private Button mVehicleButton;
    private Button mCarrierTypeButton;
    private Button mVehicleTypeButton;
    private Button mVehicleMeterButton;
    private TextView mTextView;
    private VehicleCdSelector mVehicleCdSelector;
    private VehicleMeterSelector mVehicleMeterSelector;
    private VehicleTypeSelector mVehicleTypeSelector;
    private CarrierTypeCdSelector mTypeCdSelector;
    private String mSelectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVehicleCdSelector = new VehicleCdSelector(getApplicationContext(), mock(), this);
        mVehicleCdSelector.setAnimationStyle(R.style.popwin_anim_style);

        mVehicleTypeSelector = new VehicleTypeSelector(getApplicationContext(), mockTypes(), vehicleTypes -> {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < vehicleTypes.size(); i++) {
                result.append(vehicleTypes.get(i).getValue());
                if (i != vehicleTypes.size() - 1) {
                    result.append(",");
                }
            }
            mSelectedType = result.toString();
            mTextView.setText(mSelectedType);
        });
        mVehicleTypeSelector.setAnimationStyle(R.style.popwin_anim_style);

        mVehicleMeterSelector = new VehicleMeterSelector(getApplicationContext(), mockMeters(), vehicleMeters -> {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < vehicleMeters.size(); i++) {
                result.append(vehicleMeters.get(i).getValue());
                if (i != vehicleMeters.size() - 1) {
                    result.append(",");
                }
            }
            mSelectedType = result.toString();
            mTextView.setText(mSelectedType);
        });
        mVehicleMeterSelector.setAnimationStyle(R.style.popwin_anim_style);

        mTypeCdSelector = new CarrierTypeCdSelector(getApplicationContext(), mockCarrierType(), this);
        mTypeCdSelector.setAnimationStyle(R.style.popwin_anim_style);

        mTextView = findViewById(R.id.tv_content);
        mVehicleButton = findViewById(R.id.btVehicel);
        mVehicleButton.setOnClickListener(v -> mVehicleCdSelector.show(v));
        mVehicleTypeButton = findViewById(R.id.btVehicleType);
        mVehicleTypeButton.setOnClickListener(v -> mVehicleTypeSelector.show(v));
        mVehicleMeterButton = findViewById(R.id.btVehicleMeter);
        mVehicleMeterButton.setOnClickListener(v -> mVehicleMeterSelector.show(v));

        mCarrierTypeButton = findViewById(R.id.btCarrierType);
        mCarrierTypeButton.setOnClickListener(v -> mTypeCdSelector.show(v, mSelectedType));
    }

    public String getContent(VehicleCondition condition) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("车长：");
        for (int i = 0; i < condition.getVehicleMeters().size(); i++) {
            resultBuilder.append(condition.getVehicleMeters().get(i).getValue());
            if (i != condition.getVehicleMeters().size() - 1) {
                resultBuilder.append(",");
            }
        }
        resultBuilder.append("米\n");
        for (int i = 0; i < condition.getVehicleTypes().size(); i++) {
            resultBuilder.append(condition.getVehicleTypes().get(i).getValue());
            if (i != condition.getVehicleTypes().size() - 1) {
                resultBuilder.append(",");
            }
        }
        return resultBuilder.toString();
    }

    public VehicleCondition mock() {
        VehicleCondition vehicleCondition = new VehicleCondition();
        vehicleCondition.setVehicleMeters(mockMeters());
        vehicleCondition.setVehicleTypes(mockTypes());
        return vehicleCondition;
    }

    public ArrayList<VehicleType> mockTypes() {
        ArrayList<VehicleType> types = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            VehicleType type = new VehicleType();
            type.setVehicleType("平板车" + i);
            types.add(type);
        }
        return types;
    }

    public ArrayList<VehicleMeter> mockMeters() {
        ArrayList<VehicleMeter> meters = new ArrayList<>();
        double d = 1.0;
        for (int i = 0; i < 25; i++) {
            d = d + i;
            VehicleMeter meter = new VehicleMeter();
            meter.setVehicleMeter(Double.toString(d));
            meters.add(meter);
        }
        return meters;
    }

    public List<CarrierType> mockCarrierType() {
        ArrayList<CarrierType> types = new ArrayList<>();
        types.add(new CarrierType("运输公司"));
        types.add(new CarrierType("车队"));
        types.add(new CarrierType("车老板"));
        types.add(new CarrierType("信息部"));
        types.add(new CarrierType("专线"));
        return types;
    }

    @Override
    public void onSure(VehicleCondition vehicleCondition) {
        mTextView.setText(getContent(vehicleCondition));
    }

    @Override
    public void onSure(List<CarrierType> carrierTypes) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < carrierTypes.size(); i++) {
            result.append(carrierTypes.get(i).getValue());
            if (i != carrierTypes.size() - 1) {
                result.append(",");
            }
        }
        mSelectedType = result.toString();
        mTextView.setText(mSelectedType);
    }
}
