package com.kkb.bean;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class BootStrapTableCourier {
    private Integer id;
    private String dname;
    private String sysphone;
    private String idcard;
    private String dpassword;
    private Integer eno;
    private String regtime;
    private String lasttime;

    public BootStrapTableCourier() {
    }

    public BootStrapTableCourier(Integer id, String dname, String sysphone, String idcard, String dpassword, Integer eno, String regtime, String lasttime) {
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
