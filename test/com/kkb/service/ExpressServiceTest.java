package com.kkb.service;

import com.kkb.bean.Express;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressServiceTest {

    @Test
    public void insert() {
        Express e = new Express("568","李四","13856978754","天天快递","18898745632","777");
        boolean insert = ExpressService.insert(e);
        System.out.println(insert);
    }
}