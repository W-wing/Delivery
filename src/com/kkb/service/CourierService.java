package com.kkb.service;

import com.kkb.bean.Courier;
import com.kkb.dao.BaseCourierDao;
import com.kkb.dao.impl.CourierDaoMysql;

import java.util.List;
import java.util.Map;

public class CourierService {
    private static BaseCourierDao dao = new CourierDaoMysql();
    /**
     * 用于统计快递员的总数与当日注册量
     *
     * @return [{size:总数,day:新增}]
     */
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 用于查询所有快递员
     *
     * @param limit      是否分页的标记，true表示分页，false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递的集合
     */
    public static List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    /**
     * 根据快递员手机号查询快递员
     *
     * @param sysPhone
     * @return
     */
    public static Courier findBySysPhone(String sysPhone) {
        return dao.findBySysPhone(sysPhone);
    }

    /**
     * 新增快递人员
     *
     * @param c
     * @return 添加的结果，true表示成功，false表示失败
     */
    public static boolean insert(Courier c) {
        return dao.insert(c);
    }

    /**
     * 修改快递人员信息
     *
     * @param id
     * @param newCourier 新的快递员对象(dname,sysphone,idcard,dpassword)
     * @return 修改的结果，true表示成功，false表示失败
     */
    public static boolean update(int id, Courier newCourier) {
        return dao.update(id,newCourier);
    }

    /**
     * 根据id删除快递员信息
     *
     * @param id
     * @return
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }

    /**
     * 用于修改派件数量
     *
     * @param id
     * @param increment
     * @return
     */
    public static boolean updateEno(int id, int increment) {
        return dao.updateEno(id, increment);
    }

    /**
     * 修改上次登录时间
     *
     * @param id
     * @return
     */
    public static boolean updateLastTime(int id) {
        return dao.updateLastTime(id);
    }
}
