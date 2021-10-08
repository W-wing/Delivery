package com.kkb.dao.impl;

import com.kkb.dao.BaseAdminDao;
import com.kkb.util.DruidUtil;
import com.kkb.util.UserUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AdminDaoMysql implements BaseAdminDao {
    private static final String SQL_UPDATE_LOGIN_TIME = "update admin set logintime = ?,loginip = ? where username = ?";
    private static final String SQL_LOGIN = "select id from admin where username = ? and password = ?";

    /**
     * 根据用户名，更新登陆时间和登录IP
     *
     * @param username
     * @param date
     * @param ip
     */
    @Override
    public void updateLoginTime(String username, Date date, String ip) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE_LOGIN_TIME);
            state.setDate(1,new java.sql.Date(date.getTime()));
            state.setString(2,ip);
            state.setString(3,username);
            state.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,null);
        }

    }

    /**
     * 管理员根据账号密码登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean login(String username, String password) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(SQL_LOGIN);
            state.setString(1,username);
            state.setString(2,password);
            rs = state.executeQuery();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,rs);
        }
        return false;
    }
}
