package com.kkb.bean;

import lombok.Data;

@Data
public class BootStrapTableExpress {
    private int id;
    private String number;
    private String username;
    private String userphone;
    private String company;
    private String code;
    private String intime;
    private String outime;
    private String status;
    private String sysphone;

    public BootStrapTableExpress(int id, String number, String username, String userphone, String company, String code, String intime, String outime, String status, String sysphone) {
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

    public BootStrapTableExpress() {
    }
}
