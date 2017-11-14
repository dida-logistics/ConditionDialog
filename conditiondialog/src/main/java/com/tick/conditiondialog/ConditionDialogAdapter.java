package com.tick.conditiondialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;

/**
 * 条件选择框适配器
 * Created by wangcheng on 2017/11/13.
 */

public class ConditionDialogAdapter<T extends ConditionEntity> extends BaseAdapter {

    /**
     * 单选模式
     */
    public static final int TYPE_SINGLE = 0;
    /**
     * 多选模式
     */
    public static final int TYPE_MULTIPLY = 1;

    private ArrayList<T> mConditions;
    private ArrayList<T> mSelectChilds = new ArrayList<>();
    private Context mContext;
    private int mType;

    public ConditionDialogAdapter(Context context, ArrayList<T> conditions) {
        this(context, conditions, TYPE_SINGLE);
    }

    public ConditionDialogAdapter(Context context, ArrayList<T> conditions, int type) {
        mContext = context;
        mConditions = conditions == null ? new ArrayList<>() : conditions;
        mType = type;
        mSelectChilds.clear();
        for (T entity : mConditions) {
            if (entity.isCheck()) {
                mSelectChilds.add(entity);
            }
        }
    }

    @Override
    public int getCount() {
        return mConditions.size();
    }

    @Override
    public Object getItem(int position) {
        return mConditions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.condition_dialog_item_layout, parent, false);
            holder = new ViewHolder();
            holder.cb = convertView.findViewById(R.id.cb_condition);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ConditionEntity entity = mConditions.get(position);
        holder.cb.setText(entity.getValue());
        holder.cb.setEnabled(false);
        holder.cb.setClickable(false);//防止CheckBox消费点击事件
        holder.cb.setChecked(entity.isCheck());
        return convertView;
    }

    class ViewHolder {
        CheckBox cb;
    }

    public void reset() {
        mSelectChilds.clear();
        for (ConditionEntity entity : mConditions) {
            entity.setCheck(false);
        }
        notifyDataSetChanged();
    }

    private void clearCheck() {
        for (ConditionEntity entity : mSelectChilds) {
            entity.setCheck(false);
        }
        mSelectChilds.clear();
    }

    public final void onCheckItemClick(int position) {
        if (position < 0 || position >= mConditions.size()) {
            return;
        }
        T entity = mConditions.get(position);
        if (TYPE_SINGLE == mType) {
            //单选模式
            clearCheck();
            if (!entity.isCheck()) {
                mSelectChilds.add(entity);
            }
        } else {
            //多选模式
            if (!entity.isCheck()) {
                mSelectChilds.add(entity);
            } else {
                mSelectChilds.remove(entity);
            }
        }
        entity.setCheck(!entity.isCheck());
        notifyDataSetChanged();
    }

    public ArrayList<T> getSelectChilds() {
        return mSelectChilds;
    }
}
