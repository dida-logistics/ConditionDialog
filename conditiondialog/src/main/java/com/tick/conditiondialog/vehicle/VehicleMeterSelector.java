package com.tick.conditiondialog.vehicle;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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

/**
 * 车长选择器
 * Created by wangcheng on 2017/11/13.
 */

public class VehicleMeterSelector extends PopupWindow {
    private VehicleMetersSelectListener mListener;
    private ConditionDialogAdapter<VehicleMeter> mMeterAdapter;
    private View mTop;

    public VehicleMeterSelector(Context context, ArrayList<VehicleMeter> vehicleMeters, VehicleMetersSelectListener
            listener, int type) {
        mListener = listener;
        View container = LayoutInflater.from(context).inflate(R.layout.vehicle_meter_condition_popwindow_layout,
                null, false);
        mTop = container.findViewById(R.id.v_top);
        TextView title = container.findViewById(R.id.tv_title);
        TextView tvCancel = container.findViewById(R.id.tv_cancel);
        TextView tvSure = container.findViewById(R.id.tv_sure);

        title.setText("车长");
        GridView meterGridView = container.findViewById(R.id.gv_condition);
        mMeterAdapter = new ConditionDialogAdapter<>(context, vehicleMeters, type);
        meterGridView.setAdapter(mMeterAdapter);
        meterGridView.setOnItemClickListener((parent, view, position, id) -> mMeterAdapter.onCheckItemClick(position));

        tvCancel.setOnClickListener(v -> hide());
        tvSure.setOnClickListener(v -> {
            if (mListener != null) {
                VehicleCondition selectedCondition = new VehicleCondition();
                selectedCondition.setVehicleMeters(mMeterAdapter.getSelectChilds());
                mListener.onSure(selectedCondition.cloneVehicleMeters());
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

    public VehicleMeterSelector(Context context, ArrayList<VehicleMeter> vehicleMeters, VehicleMetersSelectListener
            listener) {
        this(context, vehicleMeters, listener, ConditionDialogAdapter.TYPE_MULTIPLY);
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
