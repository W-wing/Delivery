package com.kkb.dao.impl;

import com.kkb.bean.User;
import com.kkb.dao.BaseUserDao;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class UserDaoMysqlTest {
    BaseUserDao dao = new UserDaoMysql();
    @Test
    public void console() {
        List<Map<String, Integer>> console = dao.console();
        System.out.println(console);
    }

    @Test
    public void findAll() {
        /*List<User> all = dao.findAll(true, 0, 3);
        System.out.println(all);*/
        List<User> all1 = dao.findAll(false, 0, 0);
        System.out.println(all1);
    }

    @Test
    public void findByUphone() {
        User byUphone = dao.findByUphone("13056489632");
        System.out.println(byUphone);
    }

    @Test
    public void insert() {
        User u = new User("测试1", "13333333333", "666666666666666666", "123");
        boolean s = dao.insert(u);
        System.out.println(s);
    }

    @Test
    public void update() {
        User newUser = new User("测试2","19632548963","520369456978962031","111");
        boolean update = dao.update(1007, newUser);
        System.out.println(update);
    }

    @Test
    public void updateLastTime() {
        boolean b = dao.updateLastTime(1007);
        System.out.println(b);
    }

    @Test
    public void delete() {
        boolean delete = dao.delete(1007);
        System.out.println(delete);
    }
}