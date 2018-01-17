package com.tick.conditiondialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

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
    private Context mContext;
    private int mType;

    public ConditionDialogAdapter(Context context, ArrayList<T> conditions) {
        this(context, conditions, TYPE_SINGLE);
    }

    public ConditionDialogAdapter(Context context, ArrayList<T> conditions, int type) {
        mContext = context;
        mConditions = conditions == null ? new ArrayList<>() : conditions;
        mType = type;
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
        for (ConditionEntity entity : mConditions) {
            entity.setCheck(false);
        }
        notifyDataSetChanged();
    }

    private void clearCheck() {
        for (ConditionEntity entity : mConditions) {
            entity.setCheck(false);
        }
    }

    public final void onCheckItemClick(int position) {
        if (position < 0 || position >= mConditions.size()) {
            return;
        }
        T entity = mConditions.get(position);
        if (TYPE_SINGLE == mType) {
            //单选模式
            clearCheck();
        }
        entity.setCheck(!entity.isCheck());
        notifyDataSetChanged();
    }

    /**
     * 标记已经选中的条件
     *
     * @param args 选中条件数组
     */
    public final void setItemClicked(String[] args) {
        if (args == null || args.length == 0) {
            return;
        }
        if (TYPE_SINGLE == mType) {
            //单选模式
            clearCheck();
            String selected = args[0];
            for (T t : mConditions) {
                if (selected.equals(t.getValue())) {
                    t.setCheck(true);
                    break;
                }
            }
        } else {
            //多选模式
            clearCheck();
            for (String select : args) {
                for (T t : mConditions) {
                    if (select.equals(t.getValue())) {
                        t.setCheck(true);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<T> getSelectChilds() {
        ArrayList<T> list = new ArrayList<>();
        for (T t : mConditions) {
            if (t.isCheck()) {
                list.add(t);
            }
        }
        return list;
    }
}
