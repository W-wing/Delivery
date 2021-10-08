package com.kkb.bean;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BootStrapTableUser {
    private Integer id;
    private String uname;
    private String uphone;
    private String idno;
    private String upwd;
    private String regtime;
    private String lasttime;

    public BootStrapTableUser() {
    }

    public BootStrapTableUser(Integer id, String uname, String uphone, String idno, String upwd, String regtime, String lasttime) {
        this.id = id;
        this.uname = uname;
        this.uphone = uphone;
        this.idno = idno;
        this.upwd = upwd;
        this.regtime = regtime;
        this.lasttime = lasttime;
    }
}
