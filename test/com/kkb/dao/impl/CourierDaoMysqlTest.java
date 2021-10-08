package com.kkb.dao.impl;

import com.kkb.bean.Courier;
import com.kkb.dao.BaseCourierDao;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CourierDaoMysqlTest {
    BaseCourierDao dao = new CourierDaoMysql();

    @Test
    public void console() {
        List<Map<String, Integer>> console = dao.console();
        System.out.println(console);
    }

    @Test
    public void findAll() {
        /*List<Courier> all = dao.findAll(false, 0, 0);
        System.out.println(all);*/
        List<Courier> all1 = dao.findAll(true, 0, 3);
        System.out.println(all1);
    }

    @Test
    public void findBySysPhone() {
        Courier bySysPhone = dao.findBySysPhone("13000000000");
        System.out.println(bySysPhone);
    }

    @Test
    public void insert() {
        Courier c = new Courier("美女呀","18656478965","456789200103210056","789");
        boolean insert = dao.insert(c);
        System.out.println(insert);
    }

    @Test
    public void update() {
        Courier newCourier = new Courier("大美女","18656478966","456789200103210057","666");
        boolean update = dao.update(1008, newCourier);
        System.out.println(update);
    }

    @Test
    public void delete() {
        boolean delete = dao.delete(1008);
        System.out.println(delete);
    }

    @Test
    public void updateEno() {
        boolean b = dao.updateEno(1008, 6);
        System.out.println(b);
    }

    @Test
    public void updateLastTime() {
        boolean b = dao.updateLastTime(1008);
        System.out.println(b);
    }
}