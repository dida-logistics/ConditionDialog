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

public class VehicleTypeSelector extends PopupWindow {
    private VehicleTypeSelectListener mListener;
    private ConditionDialogAdapter<VehicleType> mTypeAdapter;
    private View mTop;

    public VehicleTypeSelector(Context context, ArrayList<VehicleType> vehicleTypes, VehicleTypeSelectListener
            listener, int type, @NonNull List<String> selectVehicleTypes) {
        mListener = listener;
        View container = LayoutInflater.from(context).inflate(R.layout.vehicle_type_condition_popwindow_layout, null,
                false);
        mTop = container.findViewById(R.id.v_top);
        TextView title = container.findViewById(R.id.tv_title);
        TextView tvCancel = container.findViewById(R.id.tv_cancel);
        TextView tvSure = container.findViewById(R.id.tv_sure);

        title.setText("车型");
        GridView typeGridView = container.findViewById(R.id.gv_condition);
        for (String str : selectVehicleTypes) {
            for (VehicleType vehicleType : vehicleTypes) {
                if (!TextUtils.isEmpty(str) && str.equals(vehicleType.getValue())) {
                    vehicleType.setCheck(true);
                    break;
                }
            }
        }
        mTypeAdapter = new ConditionDialogAdapter<>(context, vehicleTypes, type);
        typeGridView.setAdapter(mTypeAdapter);
        typeGridView.setOnItemClickListener(((parent, view, position, id) -> mTypeAdapter.onCheckItemClick(position)));

        tvCancel.setOnClickListener(v -> hide());
        tvSure.setOnClickListener(v -> {
            if (mListener != null) {
                VehicleCondition selectedCondition = new VehicleCondition();
                selectedCondition.setVehicleTypes(mTypeAdapter.getSelectChilds());
                mListener.onSure(selectedCondition.cloneVehicleTypes());
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

    public VehicleTypeSelector(Context context, ArrayList<VehicleType> vehicleTypes, VehicleTypeSelectListener
            listener) {
        this(context, vehicleTypes, listener, ConditionDialogAdapter.TYPE_MULTIPLY, new ArrayList<>());
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
