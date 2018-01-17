package com.tick.conditiondialog.vehicle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tick.conditiondialog.ConditionDialogAdapter;
import com.tick.conditiondialog.R;
import com.tick.conditiondialog.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 车辆条件选择器
 * Created by wangcheng on 2017/11/13.
 */

public class VehicleCdSelector extends PopupWindow {
    private VehicleConditionSelectListener mListener;
    private ConditionDialogAdapter<VehicleMeter> mMeterAdapter;
    private ConditionDialogAdapter<VehicleType> mTypeAdapter;
    private View mTop;

    public VehicleCdSelector(Context context, VehicleCondition vehicleCondition, VehicleConditionSelectListener
            listener, int vehicleTypeType, @NonNull List<String> selectVehicleTypes, int vehicleMeterType, @NonNull
                                     List<String> selectVehicleMeters) {
        mListener = listener;
        VehicleCondition condition = vehicleCondition.cloneCondition();
        View container = LayoutInflater.from(context).inflate(R.layout.vehicel_condition_popwindow_layout, null, false);
        mTop = container.findViewById(R.id.v_top);
        TextView title = container.findViewById(R.id.tv_title);
        TextView tvCancel = container.findViewById(R.id.tv_cancel);
        TextView tvSure = container.findViewById(R.id.tv_sure);

        GridView meterGridView = container.findViewById(R.id.gv_vehicle_meter);
        for (String str : selectVehicleMeters) {
            for (VehicleMeter vehicleMeter : condition.getVehicleMeters()) {
                if (!TextUtils.isEmpty(str) && str.equals(vehicleMeter.getValue())) {
                    vehicleMeter.setCheck(true);
                    break;
                }
            }
        }
        mMeterAdapter = new ConditionDialogAdapter<>(context, condition.getVehicleMeters(), vehicleMeterType);
        meterGridView.setAdapter(mMeterAdapter);
        meterGridView.setOnItemClickListener((parent, view, position, id) -> mMeterAdapter.onCheckItemClick(position));

        GridView typeGridView = container.findViewById(R.id.gv_vehicle_type);
        for (String str : selectVehicleTypes) {
            for (VehicleType vehicleType : condition.getVehicleTypes()) {
                if (!TextUtils.isEmpty(str) && str.equals(vehicleType.getValue())) {
                    vehicleType.setCheck(true);
                    break;
                }
            }
        }
        mTypeAdapter = new ConditionDialogAdapter<>(context, condition.getVehicleTypes(), vehicleTypeType);
        typeGridView.setAdapter(mTypeAdapter);
        typeGridView.setOnItemClickListener(((parent, view, position, id) -> mTypeAdapter.onCheckItemClick(position)));

        tvCancel.setOnClickListener(v -> hide());
        tvSure.setOnClickListener(v -> {
            if (mListener != null) {
                VehicleCondition selectedCondition = new VehicleCondition();
                selectedCondition.setVehicleMeters(mMeterAdapter.getSelectChilds());
                selectedCondition.setVehicleTypes(mTypeAdapter.getSelectChilds());
                mListener.onSure(selectedCondition.cloneCondition());
                hide();
            }
        });
        //去除点击时的背景色。
        title.setHighlightColor(ContextCompat.getColor(context, android.R.color.transparent));
        DisplayMetrics metrics = ViewUtil.getDisplayMetrics(context);
        setWidth(metrics.widthPixels);
        setHeight(metrics.heightPixels - ViewUtil.getStateBarHeight(context));
        setContentView(container);
        setFocusable(true);
    }

    public VehicleCdSelector(Context context, VehicleCondition vehicleCondition, VehicleConditionSelectListener
            listener) {
        this(context, vehicleCondition, listener, ConditionDialogAdapter.TYPE_MULTIPLY, new ArrayList<>(),
                ConditionDialogAdapter.TYPE_SINGLE, new ArrayList<>());
    }

    public void show(View v) {
        showAtLocation(v, Gravity.TOP, 0, 0);
        mTop.animate().alpha(1f).setDuration(100).setStartDelay(600);
    }

    public void hide() {
        mTop.setAlpha(0f);
        mTop.postDelayed(this::dismiss, 10);
    }
}
