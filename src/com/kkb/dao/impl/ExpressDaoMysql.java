package com.kkb.dao.impl;

import com.kkb.bean.Express;
import com.kkb.dao.BaseExpressDao;
import com.kkb.exception.DuplicateCodeException;
import com.kkb.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressDaoMysql implements BaseExpressDao {
    //用于控制台显示
    private static final String SQL_CONSOLE = "select count(id) data1_size,COUNT(to_days(intime)=to_days(NOW()) OR NULL) data1_day,COUNT(status=0 OR NULL) data2_size,COUNT(to_days(intime)=to_days(NOW()) AND status=0 OR NULL) data2_day from express";
    //用于懒人排行榜
    private static final String SQL_LAZYCONSOLE = "SELECT username name,COUNT(status = 0) data_day FROM express where userphone = ?";
    //用于查询数据库中的所有数据信息
    private static final String SQL_FIND_ALL = "SELECT * from express";
    //用于分页查询数据库中的所有快递信息
    private static final String SQL_FIND_LIMIT = "SELECT * FROM express LIMIT ?,?";
    //根据取件码去查询快递的所有信息
    private static final String SQL_FIND_BY_CODE = "SELECT * FROM express WHERE code = ?";
    //根据快递单号查询快递的所有信息
    private static final String SQL_FIND_BY_NUMBER = "SELECT * FROM express WHERE number = ?";
    //根据录入人手机号查询快递的所有信息
    private static final String SQL_FIND_BY_SYSPHONE = "SELECT * FROM express WHERE sysphone = ?";
    //根据用户手机号查询快递的所有信息
    private static final String SQL_FIND_BY_USERPHONE = "SELECT * FROM express WHERE userphone = ?";
    //根据用户手机号与快递状态码查询快递信息
    private static final String SQL_FIND_BY_USERPHONEANDSTATUS = "SELECT * FROM EXPRESS WHERE userphone = ? and status = ?";
    //录入快递
    private static final String SQL_INSERT = "INSERT INTO express (number,username,userphone,company,code,intime,status,sysphone) VALUES(?,?,?,?,?,NOW(),0,?)";
    //快递的修改
    private static final String SQL_UPDATE = "UPDATE express SET number = ?,username = ?,company = ?,status = ? where id = ?";
    //快递的取件操作
    private static final String SQL_UPDATE_STATUS = "UPDATE express SET status = 1,outime = NOW(),code = null where code = ?";
    //快递的删除
    private static final String SQL_DELETE = "DELETE FROM express where id = ?";
    /**
     * 用于查询数据库中的全部快递(总数+新增)，待取件快递(总数+新增)
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     */
    @Override
    public List<Map<String, Integer>> console() {
        ArrayList<Map<String, Integer>> data = new ArrayList<>();
        //1.获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_CONSOLE);
            //3.填充参数（可选）
            //4.执行SQL语句
            result = state.executeQuery();
            //5.获取执行结果
            if (result.next()){
                int data1_size = result.getInt("data1_size");
                int data1_day = result.getInt("data1_day");
                int data2_size = result.getInt("data2_size");
                int data2_day = result.getInt("data2_day");
                Map data1 = new HashMap();
                data1.put("data1_size",data1_size);
                data1.put("data1_day",data1_day);
                Map data2 = new HashMap();
                data2.put("data2_size",data2_size);
                data2.put("data2_day",data2_day);
                data.add(data1);
                data.add(data2);
            }
            //6.资源的释放
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    /**
     * 用于查询用户未取件数
     *
     * @return
     */
    @Override
    public List<Map<String, Integer>> lazyConsole(String userphone) {
        ArrayList<Map<String, Integer>> data = new ArrayList<>();
        //1.获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_LAZYCONSOLE);
            //3.填充参数（可选）
            state.setString(1,userphone);
            //4.执行SQL语句
            result = state.executeQuery();
            //5.获取执行结果
            if (result.next()){
                String name = result.getString("name");
                int data_day = result.getInt("data_day");
                Map data1 = new HashMap();
                data1.put("name",name);
                data1.put("data_day",data_day);
                data.add(data1);
            }
            //6.资源的释放
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页的标记，true表示分页，false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递的集合
     */
    @Override
    public List<Express> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Express> data = new ArrayList<>();
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
            //5.获取执行结果
            while (result.next()){
                int id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String userphone = result.getString("userphone");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp intime = result.getTimestamp("intime");
                Timestamp outime = result.getTimestamp("outime");
                int status = result.getInt("status");
                String sysphone = result.getString("sysphone");
                Express e = new Express(id,number,username,userphone,company,code,intime,outime,status,sysphone);
                data.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    /**
     * 根据快递单号查询快递信息
     *
     * @param number 单号
     * @return 查询的快递信息，单号不存在时返回null
     */
    @Override
    public Express findByNumber(String number) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_NUMBER);
            //3.填充参数（可选）
            state.setString(1,number);
            //4.执行SQL语句
            result = state.executeQuery();
            //5.获取执行结果
            if (result.next()){
                int id = result.getInt("id");
                String username = result.getString("username");
                String userphone = result.getString("userphone");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp intime = result.getTimestamp("intime");
                Timestamp outime = result.getTimestamp("outime");
                int status = result.getInt("status");
                String sysphone = result.getString("sysphone");
                Express e = new Express(id,number,username,userphone,company,code,intime,outime,status,sysphone);
                return e;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * 根据取件码查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */
    @Override
    public Express findByCode(String code) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_CODE);
            //3.填充参数（可选）
            state.setString(1,code);
            //4.执行SQL语句
            result = state.executeQuery();
            //5.获取执行结果
            if (result.next()){
                int id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String userphone = result.getString("userphone");
                String company = result.getString("company");
                Timestamp intime = result.getTimestamp("intime");
                Timestamp outime = result.getTimestamp("outime");
                int status = result.getInt("status");
                String sysphone = result.getString("sysphone");
                Express e = new Express(id,number,username,userphone,company,code,intime,outime,status,sysphone);
                return e;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * 根据用户手机号码，查询他的所有快递信息
     *
     * @param userphone 手机号码
     * @return 查询的快递信息列表
     */
    @Override
    public List<Express> findByUserPhone(String userphone) {
        ArrayList<Express> data = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_USERPHONE);
            //3.填充参数（可选）
            state.setString(1,userphone);
            //4.执行SQL语句
            result = state.executeQuery();
            //5.获取执行结果
            while (result.next()){
                int id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp intime = result.getTimestamp("intime");
                Timestamp outime = result.getTimestamp("outime");
                int status = result.getInt("status");
                String sysphone = result.getString("sysphone");
                Express e = new Express(id,number,username,userphone,company,code,intime,outime,status,sysphone);
                data.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    /**
     * 根据用户手机号码跟快递的状态码进行查询
     *
     * @param userphone
     * @param status
     * @return
     */
    @Override
    public List<Express> findByUserPhoneAndStatus(String userphone, int status) {
        ArrayList<Express> data = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_USERPHONEANDSTATUS);
            //3.填充参数（可选）
            state.setString(1,userphone);
            state.setInt(2,status);
            //4.执行SQL语句
            result = state.executeQuery();
            //5.获取执行结果
            while (result.next()){
                int id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp intime = result.getTimestamp("intime");
                Timestamp outime = result.getTimestamp("outime");
                String sysphone = result.getString("sysphone");
                Express e = new Express(id,number,username,userphone,company,code,intime,outime,status,sysphone);
                data.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    /**
     * 根据录入人手机号码，查询录入的所有记录
     *
     * @param sysphone 手机号码
     * @return 查询的快递信息列表
     */
    @Override
    public List<Express> findBySysPhone(String sysphone) {
        ArrayList<Express> data = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_SYSPHONE);
            //3.填充参数（可选）
            state.setString(1,sysphone);
            //4.执行SQL语句
            result = state.executeQuery();
            //5.获取执行结果
            while (result.next()){
                int id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String userphone = result.getString("userphone");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp intime = result.getTimestamp("intime");
                Timestamp outime = result.getTimestamp("outime");
                int status = result.getInt("status");
                Express e = new Express(id,number,username,userphone,company,code,intime,outime,status,sysphone);
                data.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    /**
     * 快递的录入
     *INSERT INTO express (number,username,userphone,company,code,NOW(),status,sysphone) VALUES(?,?,?,?,?,NOW(),0,?)
     * @param e 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    @Override
    public boolean insert(Express e) throws DuplicateCodeException{
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            //3.填充参数
            state.setString(1,e.getNumber());
            state.setString(2,e.getUsername());
            state.setString(3,e.getUserphone());
            state.setString(4,e.getCompany());
            state.setString(5,e.getCode());
            state.setString(6,e.getSysphone());
            //4.执行SQL语句，并获取执行结果
            return state.executeUpdate()>0?true:false;
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
            if (e1.getMessage().endsWith("for key 'express.code'")){
                DuplicateCodeException e2 = new DuplicateCodeException(e1.getMessage());
                throw e2;
            }else {
                e1.printStackTrace();
            }
        } finally {
            //5.释放资源
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 快递的修改
     *
     * @param id         要修改的快递的id
     * @param newExpress 新的快递对象（number,company,username,userphone）
     * @return 修改的结果，true表示成功，false表示失败
     */
    @Override
    public boolean update(int id, Express newExpress) {
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            state.setString(1,newExpress.getNumber());
            state.setString(2,newExpress.getUsername());
            state.setString(3,newExpress.getCompany());
            state.setInt(4,newExpress.getStatus());
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
     * 更改快递的状态为1，表示取件完成
     *
     * @param code 要修改的快递单号
     * @return 修改的结果，true表示成功，false表示失败
     */
    @Override
    public boolean updateStatus(String code) {
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE_STATUS);
            state.setString(1,code);
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 根据id，删除单个快递信息
     *
     * @param id 要删除快递的id
     * @return 删除的结果，true表示成功，false表示失败
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
