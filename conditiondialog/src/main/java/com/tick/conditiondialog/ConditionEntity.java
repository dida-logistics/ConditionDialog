package com.tick.conditiondialog;

/**
 * 条件实体抽象类
 * Created by wangcheng on 2017/11/13.
 */

public abstract class ConditionEntity {
    /**
     * 添加是否选中，默认未选中
     */
    private boolean isCheck = false;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    /**
     * 获取条件的文案
     *
     * @return 条件的文案
     */
    public abstract String getValue();
}
