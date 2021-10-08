package com.kkb.dao.impl;

import com.kkb.bean.User;
import com.kkb.dao.BaseUserDao;
import com.kkb.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoMysql implements BaseUserDao {
    private static final String SQL_CONSOLE = "SELECT COUNT(id) data1_size,COUNT(TO_DAYS(regtime)=TO_DAYS(NOW()) or NULL) data1_day FROM user";
    private static final String SQL_FIND_ALL = "SELECT * FROM user";
    private static final String SQL_FIND_LIMIT = "SELECT * FROM user LIMIT ?,?";
    private static final String SQL_FIND_BY_UPHONE = "SELECT * FROM user WHERE uphone = ?";
    private static final String SQL_INSERT = "INSERT INTO user (uname,uphone,idno,upwd,regtime) VALUES (?,?,?,?,NOW())";
    private static final String SQL_UPDATE = "UPDATE user set uname = ?,uphone = ?,idno = ?,upwd = ? WHERE id = ?";
    private static final String SQL_UPDATE_LASTTIME = "UPDATE user SET lasttime = NOW() WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM user WHERE id = ?";
    /**
     * 用于统计用户的总数与当日注册量
     *
     * @return [{size:总数,day:新增}]
     */
    @Override
    public List<Map<String, Integer>> console() {
        ArrayList<Map<String, Integer>> data = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(SQL_CONSOLE);
            result = state.executeQuery();
            if (result.next()){
                int data1_size = result.getInt("data1_size");
                int data1_day = result.getInt("data1_day");
                Map data1 = new HashMap();
                data1.put("data1_size",data1_size);
                data1.put("data1_day",data1_day);
                data.add(data1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    /**
     * 用于查询所有用户
     *
     * @param limit      是否分页的标记，true表示分页，false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递的集合
     */
    @Override
    public List<User> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<User> data = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            if (limit){
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                state.setInt(1,offset);
                state.setInt(2,pageNumber);
            }else {
                state = conn.prepareStatement(SQL_FIND_ALL);
            }
            result = state.executeQuery();
            while (result.next()){
                int id = result.getInt("id");
                String uname = result.getString("uname");
                String uphone = result.getString("uphone");
                String idno = result.getString("idno");
                String upwd = result.getString("upwd");
                Timestamp regtime = result.getTimestamp("regtime");
                Timestamp lasttime = result.getTimestamp("lasttime");
                User u = new User(id,uname,uphone,idno,upwd,regtime,lasttime);
                data.add(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    /**
     * 根据用户手机号查找用户
     *
     * @param uphone
     * @return
     */
    @Override
    public User findByUphone(String uphone) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_UPHONE);
            state.setString(1,uphone);
            result = state.executeQuery();
            while (result.next()){
                int id = result.getInt("id");
                String uname = result.getString("uname");
                String idno = result.getString("idno");
                String upwd = result.getString("upwd");
                Timestamp regtime = result.getTimestamp("regtime");
                Timestamp lasttime = result.getTimestamp("lasttime");
                User u = new User(id,uname,uphone,idno,upwd,regtime,lasttime);
                return u;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * 添加用户
     *
     * @param u
     * @return
     */
    @Override
    public boolean insert(User u) {
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            state.setString(1,u.getUname());
            state.setString(2,u.getUphone());
            state.setString(3,u.getIdno());
            state.setString(4,u.getUpwd());
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 修改用户信息
     *
     * @param id
     * @param newUser
     * @return
     */
    @Override
    public boolean update(int id, User newUser) {
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            state.setString(1,newUser.getUname());
            state.setString(2,newUser.getUphone());
            state.setString(3,newUser.getIdno());
            state.setString(4,newUser.getUpwd());
            state.setInt(5,id);
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 修改最近登录时间
     *
     * @param id
     * @return
     */
    @Override
    public boolean updateLastTime(int id) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE_LASTTIME);
            state.setInt(1,id);
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    /**
     * 根据id查找，删除用户
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(int id) {
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_DELETE);
            state.setInt(1,id);
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }
}
