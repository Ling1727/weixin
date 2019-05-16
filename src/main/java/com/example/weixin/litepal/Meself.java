package com.example.weixin.litepal;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2019/3/20.
 */

public class Meself extends LitePalSupport {
    private String name;
    private String touxiangPath;
    private String password;
    private List<User> users=new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTouxiangPath() {
        return touxiangPath;
    }

    public void setTouxiangPath(String touxiangPath) {
        this.touxiangPath = touxiangPath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getUsers() {
        return LitePal.where("me=?",name).find(User.class);
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
