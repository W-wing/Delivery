package com.kkb.dao;

import java.util.Date;

public interface BaseAdminDao {
    /**
     * 根据用户名，更新登陆时间和登录IP
     * @param username
     */
    void updateLoginTime(String username, Date date,String ip);

    /**
     * 管理员根据账号密码登录
     * @param username
     * @param password
     * @return
     */
    boolean login(String username,String password);
}
