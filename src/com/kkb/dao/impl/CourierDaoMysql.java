package com.kkb.dao.impl;

import com.kkb.bean.Courier;
import com.kkb.dao.BaseCourierDao;
import com.kkb.util.DruidUtil;

import java.sql.*;
import java.util.*;

public class CourierDaoMysql implements BaseCourierDao {
    /**
     * 用于在控制台上显示总数
     */
    private static final String SQL_CONSOLE = "SELECT COUNT(id) data1_size,COUNT(TO_DAYS(regtime)=TO_DAYS(NOW()) or NULL) data1_day FROM courier";
    /**
     * 用于查询所有快递员
     */
    private static final String SQL_FIND_ALL = "SELECT * FROM courier";
    /**
     * 用于分页显示
     */
    private static final String SQL_FIND_LIMIT = "SELECT * FROM courier LIMIT ?,?";
    /**
     * 根据快递员手机号进行查询
     */
    private static final String SQL_FIND_BY_SYSPHONE = "SELECT * FROM courier WHERE sysphone = ?";
    /**
     * 添加快递员
     */
    private static final String SQL_INSERT = "INSERT INTO courier (dname,sysphone,idcard,dpassword,eno,regtime) VALUES (?,?,?,?,0,NOW())";
    /**
     * 修改快递员信息
     */
    private static final String SQL_UPDATE = "UPDATE courier SET dname = ?,sysphone = ?,idcard = ?,dpassword = ? WHERE id = ?";
    /**
     * 删除快递员
     */
    private static final String SQL_DELETE = "DELETE FROM COURIER WHERE id = ?";
    /**
     * 修改快递员派件数
     */
    public static final String SQL_UPDATE_ENO = "UPDATE COURIER SET ENO=ENO+? WHERE id=?";
    /**
     * 修改快递员登录时间
     */
    public static final String SQL_UPDATE_LAST_TIME = "UPDATE COURIER SET lasttime=NOW() WHERE ID=?";
    /**
     * 用于统计快递员的总数与当日注册量
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
     * 用于查询所有快递员
     *
     * @param limit      是否分页的标记，true表示分页，false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递的集合
     */
    @Override
    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Courier> data = new ArrayList<>();
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
                String dname = result.getString("dname");
                String sysphone = result.getString("sysphone");
                String idcard = result.getString("idcard");
                String dpassword = result.getString("dpassword");
                int eno = result.getInt("eno");
                Timestamp regtime = result.getTimestamp("regtime");
                Timestamp lasttime = result.getTimestamp("lasttime");
                Courier c = new Courier(id,dname,sysphone,idcard,dpassword,eno,regtime,lasttime);
                data.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    /**
     * 根据快递员手机号查询快递员
     *
     * @param sysPhone
     * @return
     */
    @Override
    public Courier findBySysPhone(String sysPhone) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_SYSPHONE);
            state.setString(1,sysPhone);
            result = state.executeQuery();
            while (result.next()){
                int id = result.getInt("id");
                String dname = result.getString("dname");
                String idcard = result.getString("idcard");
                String dpassword = result.getString("dpassword");
                int eno = result.getInt("eno");
                Timestamp regtime = result.getTimestamp("regtime");
                Timestamp lasttime = result.getTimestamp("lasttime");
                Courier c = new Courier(id,dname,sysPhone,idcard,dpassword,eno,regtime,lasttime);
                return c;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * 新增快递人员
     *
     * @param c (dname,sysphone,idcard,dpassword,eno,regtime)
     * @return 添加的结果，true表示成功，false表示失败
     */
    @Override
    public boolean insert(Courier c) {
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            state.setString(1,c.getDname());
            state.setString(2,c.getSysphone());
            state.setString(3,c.getIdcard());
            state.setString(4,c.getDpassword());
            return (state.executeUpdate() > 0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 修改快递人员信息
     *
     * @param id
     * @param newCourier 新的快递员对象(dname,sysphone,idcard,dpassword)
     * @return 修改的结果，true表示成功，false表示失败
     */
    @Override
    public boolean update(int id, Courier newCourier) {
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            state.setString(1,newCourier.getDname());
            state.setString(2,newCourier.getSysphone());
            state.setString(3,newCourier.getIdcard());
            state.setString(4,newCourier.getDpassword());
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
     * 根据id删除快递员信息
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

    /**
     * 用于修改派件数量
     *
     * @param id
     * @param increment
     * @return
     */
    @Override
    public boolean updateEno(int id, int increment) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE_ENO);
            state.setInt(1,increment);
            state.setInt(2,id);
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    /**
     * 修改上次登录时间
     *
     * @param id
     * @return
     */
    @Override
    public boolean updateLastTime(int id) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE_LAST_TIME);
            state.setInt(1,id);
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }
}
