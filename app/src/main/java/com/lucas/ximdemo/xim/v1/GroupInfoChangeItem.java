package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  12:08 下午
 */
import java.io.Serializable;

public class GroupInfoChangeItem implements Serializable {
    private int groupInfoChangeType;
    private String valueChanged;
    private String customInfoKey;
    private boolean boolValueChanged;

    public int getGroupInfoChangeType() {
        return groupInfoChangeType;
    }

    public void setGroupInfoChangeType(int groupInfoChangeType) {
        this.groupInfoChangeType = groupInfoChangeType;
    }

    public String getValueChanged() {
        return valueChanged;
    }

    public void setValueChanged(String valueChanged) {
        this.valueChanged = valueChanged;
    }

    public String getCustomInfoKey() {
        return customInfoKey;
    }

    public void setCustomInfoKey(String customInfoKey) {
        this.customInfoKey = customInfoKey;
    }

    public boolean getBoolValueChanged() {
        return boolValueChanged;
    }
}

