package com.kkb.dao.impl;

import com.kkb.bean.Express;
import com.kkb.dao.BaseExpressDao;
import com.kkb.exception.DuplicateCodeException;
import com.kkb.service.ExpressService;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ExpressDaoMysqlTest {
    BaseExpressDao dao = new ExpressDaoMysql();

    @Test
    public void console() {
        List<Map<String, Integer>> console = dao.console();
        System.out.println(console);
    }

    @Test
    public void findAll() {
        /*List<Express> all = dao.findAll(true,2,2);
        System.out.println(all);*/
        List<Express> all1 = dao.findAll(false, 0, 0);
        System.out.println(all1);
    }

    @Test
    public void findByNumber() {
        Express byNumber = dao.findByNumber("123456");
        System.out.println(byNumber);
    }

    @Test
    public void findByCode() {
        Express byCode = dao.findByCode("777");
        System.out.println(byCode);
    }

    @Test
    public void findByUserPhone() {
        List<Express> byUserPhone = dao.findByUserPhone("13654789632");
        System.out.println(byUserPhone);
    }

    @Test
    public void findBySysPhone() {
        List<Express> bySysPhone = dao.findBySysPhone("18888888888");
        System.out.println(bySysPhone);
    }

    @Test
    public void insert() {
        Express e = new Express("567","李四","13856978754","天天快递","18898745632","777");
        boolean insert = false;
        try {
            insert = dao.insert(e);
        } catch (DuplicateCodeException duplicateCodeException) {
            System.out.println("取件码重复的异常被捕获到了");
        }
        System.out.println(insert);
    }

    @Test
    public void update() {
        Express e = new Express();
        e.setNumber("321");
        e.setCompany("圆通快递");
        e.setUsername("小李子");
        e.setStatus(1);
        boolean update = dao.update(1, e);
        System.out.println(update);
    }

    @Test
    public void updateStatus() {
        boolean b = dao.updateStatus("777");
        System.out.println(b);
    }

    @Test
    public void delete() {
        boolean delete = dao.delete(1);
        System.out.println(delete);
    }
}