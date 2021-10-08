package com.kkb.dao;

import com.kkb.bean.Courier;

import java.util.List;
import java.util.Map;

public interface BaseCourierDao {
    /**
     * 用于统计快递员的总数与当日注册量
     * @return [{size:总数,day:新增}]
     */
    List<Map<String, Integer>> console();

    /**
     * 用于查询所有快递员
     * @param limit 是否分页的标记，true表示分页，false表示查询所有快递
     * @param offset SQL语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递的集合
     */
    List<Courier> findAll(boolean limit, int offset, int pageNumber);

    /**
     * 根据快递员手机号查询快递员
     * @param sysPhone
     * @return
     */
    Courier findBySysPhone(String sysPhone);

    /**
     * 新增快递人员
     * @param c
     * @return 添加的结果，true表示成功，false表示失败
     */
    boolean insert(Courier c);

    /**
     * 修改快递人员信息
     * @param id
     * @param newCourier 新的快递员对象(dname,sysphone,idcard,dpassword)
     * @return 修改的结果，true表示成功，false表示失败
     */
    boolean update(int id,Courier newCourier);

    /**
     * 根据id删除快递员信息
     * @param id
     * @return
     */
    boolean delete(int id);

    /**
     * 用于修改派件数量
     * @param id
     * @param increment
     * @return
     */
    boolean updateEno(int id,int increment);

    /**
     * 修改上次登录时间
     * @param id
     * @return
     */
    boolean updateLastTime(int id);
}
