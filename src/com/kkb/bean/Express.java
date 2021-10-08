package com.kkb.bean;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class Express {
    private int id;
    private String number;
    private String username;
    private String userphone;
    private String company;
    private String code;
    private Timestamp intime;
    private Timestamp outime;
    private int status;
    private String sysphone;

    public Express() {
    }

    public Express(String number, String username, String userphone, String company, String sysphone, String code) {
        this.number = number;
        this.username = username;
        this.userphone = userphone;
        this.company = company;
        this.sysphone = sysphone;
        this.code = code;
    }

    public Express(int id, String number, String username, String userphone, String company, String code, Timestamp intime, Timestamp outime, int status, String sysphone) {
        this.id = id;
        this.number = number;
        this.username = username;
        this.userphone = userphone;
        this.company = company;
        this.code = code;
        this.intime = intime;
        this.outime = outime;
        this.status = status;
        this.sysphone = sysphone;
    }

    public Express(String number, String username, String userphone, String company, String sysphone) {
        this.number = number;
        this.username = username;
        this.userphone = userphone;
        this.company = company;
        this.sysphone = sysphone;
    }
}
