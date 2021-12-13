package com.lucas.xim.relationship;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/13  11:28 上午
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FriendSearchParam implements Serializable {
    public static final int FIELD_ID_USER_ID    = 0x01;
    public static final int FIELD_ID_NICK_NAME  = 0x01 << 1;
    public static final int FIELD_ID_REMARK     = 0x01 << 2;

    private List<String> keywordList;
    private List<Integer> searchFieldList = new ArrayList<>();

    public void setKeywordList(List<String> keywordList) {
        this.keywordList = keywordList;
    }

    public List<String> getKeywordList() {
        return keywordList;
    }

    public void addSearchField(int field) {
        searchFieldList.add(field);
    }

    public void removeSearchField(int field) {
        searchFieldList.remove((Integer)field);
    }

}
