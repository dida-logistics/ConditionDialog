package com.tick.conditiondialogdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.tick.conditiondialog.VehicleCdSelector;
import com.tick.conditiondialog.VehicleCondition;
import com.tick.conditiondialog.VehicleConditionSelectListener;
import com.tick.conditiondialog.VehicleMeter;
import com.tick.conditiondialog.VehicleType;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements VehicleConditionSelectListener {

    private Button mButton;
    private TextView mTextView;
    private VehicleCdSelector mVehicleCdSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVehicleCdSelector = new VehicleCdSelector(getApplicationContext(), mock(), this);
        mVehicleCdSelector.setAnimationStyle(R.style.popwin_anim_style);
        mTextView = findViewById(R.id.tv_content);
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(v -> mVehicleCdSelector.show(v));
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
        ArrayList<VehicleMeter> meters = new ArrayList<>();
        ArrayList<VehicleType> types = new ArrayList<>();
        double d = 1.0;
        for (int i = 0; i < 25; i++) {
            d = d + i;
            VehicleMeter meter = new VehicleMeter();
            meter.setVehicleMeter(Double.toString(d));
            meters.add(meter);
        }
        for (int i = 0; i < 30; i++) {
            VehicleType type = new VehicleType();
            type.setVehicleType("平板车" + i);
            types.add(type);
        }
        vehicleCondition.setVehicleMeters(meters);
        vehicleCondition.setVehicleTypes(types);
        return vehicleCondition;
    }

    @Override
    public void onSure(VehicleCondition vehicleCondition) {
        mTextView.setText(getContent(vehicleCondition));
    }
}
