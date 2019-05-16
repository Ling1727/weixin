package com.example.weixin.litepal;

import org.litepal.crud.LitePalSupport;

/**
 * Created by hasee on 2019/3/20.
 */

public class Member extends LitePalSupport {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
