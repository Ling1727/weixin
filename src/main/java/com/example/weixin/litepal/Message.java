package com.example.weixin.litepal;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

/**
 * Created by hasee on 2019/3/13.
 */

public class Message extends LitePalSupport{
    private final int SEND=1;
    private final int RECEIVE=0;
    private String name;
    private String con;
    private int type;
    private String time;
    private Boolean timeIsShow;
    private Date date;

    public Message(){

    }

    public Message(String name,String con,int type,String time,Boolean timeIsShow,Date date){
        this.name=name;
        this.con=con;
        this.type=type;
        this.time=time;
        this.timeIsShow=timeIsShow;
        this.date=date;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getTimeIsShow() {
        return timeIsShow;
    }

    public void setTimeIsShow(Boolean timeIsShow) {
        this.timeIsShow = timeIsShow;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
