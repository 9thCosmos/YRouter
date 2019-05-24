package com.lzy.yrouter.test;

import java.io.Serializable;

/**
 * @author Lizhengyu
 * @description
 * @date 2019/4/12 15:43
 **/
public class UserInfo implements Serializable {
    private String name;
    private String couse;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCouse() {
        return couse;
    }

    public void setCouse(String couse) {
        this.couse = couse;
    }
}
