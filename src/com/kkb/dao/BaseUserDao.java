package com.kkb.dao;

import com.kkb.bean.User;

import java.util.List;
import java.util.Map;

public interface BaseUserDao {
    /**
     * 用于统计用户的总数与当日注册量
     * @return [{size:总数,day:新增}]
     */
    List<Map<String, Integer>> console();

    /**
     * 用于查询所有用户
     * @param limit 是否分页的标记，true表示分页，false表示查询所有快递
     * @param offset SQL语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递的集合
     */
    List<User> findAll(boolean limit, int offset, int pageNumber);

    /**
     * 根据用户手机号查找用户
     * @param uphone
     * @return
     */
    User findByUphone(String uphone);

    /**
     * 添加用户
     * @param u
     * @return
     */
    boolean insert(User u);

    /**
     * 修改用户信息
     * @param id
     * @param newUser
     * @return
     */
    boolean update(int id,User newUser);

    /**
     * 修改最近登录时间
     * @param id
     * @return
     */
    boolean updateLastTime(int id);

    /**
     * 根据id查找，删除用户
     * @param id
     * @return
     */
    boolean delete(int id);
}
