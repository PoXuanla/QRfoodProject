package com.example.qrfoodproject;

import java.io.Serializable;


public class UserInfo implements Serializable {
    private int uId;
    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }


}
