package com.tick.conditiondialog.carrier;

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
 * 承运商类型条件选择器
 * Created by wangcheng on 2017/11/13.
 */

public class CarrierTypeCdSelector extends PopupWindow {
    private CarrierTypeSelectListener mListener;
    private ConditionDialogAdapter<CarrierType> mTypeAdapter;
    private View mTop;

    public CarrierTypeCdSelector(Context context, List<CarrierType> carrierTypes, CarrierTypeSelectListener listener,
                                 int type, @NonNull List<String> selectCarrierTypes) {
        mListener = listener;
        try {
            if (carrierTypes == null) {
                return;
            }
            ArrayList<CarrierType> list = new ArrayList<>();
            for (CarrierType carrierType : carrierTypes) {
                CarrierType entity = carrierType.clone();
                list.add(entity);
                for (String str : selectCarrierTypes) {
                    if (!TextUtils.isEmpty(str) && str.equals(entity.getValue())) {
                        entity.setCheck(true);
                        break;
                    }
                }
            }
            View container = LayoutInflater.from(context).inflate(R.layout.carrier_type_condition_popwindow_layout,
                    null, false);
            mTop = container.findViewById(R.id.v_top);
            TextView title = container.findViewById(R.id.tv_title);
            TextView tvCancel = container.findViewById(R.id.tv_cancel);
            TextView tvSure = container.findViewById(R.id.tv_sure);
            GridView meterGridView = container.findViewById(R.id.gv_condition);
            title.setText("承运商类型");
            mTypeAdapter = new ConditionDialogAdapter<>(context, list, type);
            meterGridView.setAdapter(mTypeAdapter);
            meterGridView.setOnItemClickListener((parent, view, position, id) -> mTypeAdapter.onCheckItemClick
                    (position));
            tvCancel.setOnClickListener(v -> hide());
            tvSure.setOnClickListener(v -> {
                if (mListener != null) {
                    List<CarrierType> results = new ArrayList<>();
                    try {
                        List<CarrierType> carrierTypeList = mTypeAdapter.getSelectChilds();
                        if (carrierTypeList != null) {
                            for (CarrierType carrierType : carrierTypeList) {
                                results.add(carrierType.clone());
                            }
                        }
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    mListener.onSure(results);
                }
                hide();
            });
            //去除点击时的背景色。
            title.setHighlightColor(ContextCompat.getColor(context, android.R.color.transparent));
            DisplayMetrics metrics = ViewUtil.getDisplayMetrics(context);
            setWidth(metrics.widthPixels);
            setHeight(metrics.heightPixels);
            setContentView(container);
            setFocusable(true);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public CarrierTypeCdSelector(Context context, List<CarrierType> carrierTypes, CarrierTypeSelectListener listener) {
        this(context, carrierTypes, listener, ConditionDialogAdapter.TYPE_MULTIPLY, new ArrayList<>());
    }

    public void show(View v) {
        showAtLocation(v, Gravity.TOP, 0, 0);
        mTop.animate().alpha(1f).setDuration(100).setStartDelay(600);
    }

    public void show(View v, String types) {
        if (!TextUtils.isEmpty(types)) {
            String[] conditions = types.split(",");
            mTypeAdapter.setItemClicked(conditions);
        }
        show(v);
    }

    public void hide() {
        mTop.setAlpha(0f);
        mTop.postDelayed(this::dismiss, 10);
    }
}
