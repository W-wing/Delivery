package com.kkb.service;

import com.kkb.bean.User;
import com.kkb.dao.BaseUserDao;
import com.kkb.dao.impl.UserDaoMysql;

import java.util.List;
import java.util.Map;

public class UserService {
    private static BaseUserDao dao = new UserDaoMysql();
    /**
     * 用于统计用户的总数与当日注册量
     *
     * @return [{size:总数,day:新增}]
     */
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 用于查询所有用户
     *
     * @param limit      是否分页的标记，true表示分页，false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递的集合
     */
    public static List<User> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    /**
     * 根据用户手机号查找用户
     *
     * @param uphone
     * @return
     */
    public static User findByUphone(String uphone) {
        return dao.findByUphone(uphone);
    }

    /**
     * 添加用户
     *
     * @param u
     * @return
     */
    public static boolean insert(User u) {
        return dao.insert(u);
    }

    /**
     * 修改用户信息
     *
     * @param id
     * @param newUser
     * @return
     */
    public static boolean update(int id, User newUser) {
        return dao.update(id, newUser);
    }

    /**
     * 修改最近登录时间
     *
     * @param id
     * @return
     */
    public static boolean updateLastTime(int id) {
        return dao.updateLastTime(id);
    }

    /**
     * 根据id查找，删除用户
     *
     * @param id
     * @return
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
