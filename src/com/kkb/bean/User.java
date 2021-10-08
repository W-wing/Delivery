package com.kkb.bean;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private Integer id;
    private String uname;
    private String uphone;
    private String idno;
    private String upwd;
    private Timestamp regtime;
    private Timestamp lasttime;


    public User() {
    }

    public User(String uname, String uphone, String idno, String upwd) {
        this.uname = uname;
        this.uphone = uphone;
        this.idno = idno;
        this.upwd = upwd;
    }

    public User(Integer id, String uname, String uphone, String idno, String upwd, Timestamp regtime, Timestamp lasttime) {
        this.id = id;
        this.uname = uname;
        this.uphone = uphone;
        this.idno = idno;
        this.upwd = upwd;
        this.regtime = regtime;
        this.lasttime = lasttime;
    }
}
