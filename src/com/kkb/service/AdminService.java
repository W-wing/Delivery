package com.kkb.service;

import com.kkb.dao.BaseAdminDao;
import com.kkb.dao.impl.AdminDaoMysql;

import java.util.Date;

public class AdminService {
    private static BaseAdminDao dao = new AdminDaoMysql();

    /**
     * 更新登录时间与IP
     * @param username
     * @param date
     * @param ip
     */
    public static void updateLoginTimeAndIP(String username, Date date, String ip){
        dao.updateLoginTime(username, date, ip);
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public static boolean login(String username,String password){
        return dao.login(username, password);
    }
}
