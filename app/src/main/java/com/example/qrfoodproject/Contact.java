package com.example.qrfoodproject;

import java.io.Serializable;

public class Contact implements Serializable {
    private int code;
    private String message;
    private UserInfo Info;

    public int getcode() {
        return code;
    }
    public void setcode(int code) {
        this.code = code;
    }
    public String getmessage() {
        return message;
    }
    public void sermessage(String message) {
        this.message = message;
    }
    public UserInfo getuserInfo() {
        return Info;
    }

    public void setuserInfo(UserInfo userInfo) {
        this.Info = userInfo;
    }
}
