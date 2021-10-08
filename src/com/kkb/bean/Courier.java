package com.kkb.bean;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class Courier {
    private Integer id;
    private String dname;
    private String sysphone;
    private String idcard;
    private String dpassword;
    private Integer eno;
    private Timestamp regtime;
    private Timestamp lasttime;

    public Courier() {
    }

    public Courier(String dname, String sysphone, String idcard, String dpassword) {
        this.dname = dname;
        this.sysphone = sysphone;
        this.idcard = idcard;
        this.dpassword = dpassword;
    }

    public Courier(Integer id, String dname, String sysphone, String idcard, String dpassword, Integer eno, Timestamp regtime, Timestamp lasttime) {
        this.id = id;
        this.dname = dname;
        this.sysphone = sysphone;
        this.idcard = idcard;
        this.dpassword = dpassword;
        this.eno = eno;
        this.regtime = regtime;
        this.lasttime = lasttime;
    }
}
