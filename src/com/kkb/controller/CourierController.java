package com.kkb.controller;

import com.kkb.bean.*;
import com.kkb.mvc.ResponseBody;
import com.kkb.service.CourierService;
import com.kkb.util.DateFormatUtil;
import com.kkb.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourierController {
    @ResponseBody("/courier/console.do")
    public String console(HttpServletRequest req, HttpServletResponse resp){
        List<Map<String, Integer>> data = CourierService.console();
        Message msg = new Message();
        if (data.size() == 0){
            msg.setStatus(-1);
        }else {
            msg.setStatus(0);
        }
        msg.setData(data);
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/courier/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp){
        //1.获取起始的偏移值
        int offset = Integer.parseInt(req.getParameter("offset"));
        //2.获取当前页要查询的数据量
        int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        //3.进行查询
        List<Courier> list = CourierService.findAll(true, offset, pageNumber);
        List<BootStrapTableCourier> list2 = new ArrayList<>();
        for (Courier c : list) {
            String regtime = DateFormatUtil.format(c.getRegtime());
            String lasttime = c.getLasttime() == null?DateFormatUtil.format(c.getRegtime()):DateFormatUtil.format(c.getLasttime());
            BootStrapTableCourier c1 = new BootStrapTableCourier(c.getId(),c.getDname(),c.getSysphone(),c.getIdcard(),c.getDpassword(),c.getEno(),regtime,lasttime);
            list2.add(c1);
        }
        List<Map<String, Integer>> console = CourierService.console();
        Integer total = console.get(0).get("data1_size");
        //4.将集合封装为bootstrap-table识别的格式
        ResultData<BootStrapTableCourier> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }
    @ResponseBody("/courier/insert.do")
    public String insert(HttpServletRequest req, HttpServletResponse resp){
        String dname = req.getParameter("dname");
        String sysphone = req.getParameter("sysphone");
        String idcard = req.getParameter("idcard");
        String dpassword = req.getParameter("dpassword");
        Courier c = new Courier(dname,sysphone,idcard,dpassword);
        boolean insert = CourierService.insert(c);
        Message msg = new Message();
        if (insert){
            msg.setStatus(0);
            msg.setResult("快递员录入成功");
        }else {
            msg.setStatus(-1);
            msg.setResult("快递员录入失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/courier/find.do")
    public String find(HttpServletRequest req, HttpServletResponse resp){
        String sysphone = req.getParameter("sysphone");
        Courier c = CourierService.findBySysPhone(sysphone);
        Message msg = new Message();
        if (c == null){
            msg.setStatus(-1);
            msg.setResult("该电话不存在");
        }else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(c);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/courier/update.do")
    public String update(HttpServletRequest req, HttpServletResponse resp){
        int id = Integer.parseInt(req.getParameter("id"));
        String dname = req.getParameter("dname");
        String sysphone = req.getParameter("sysphone");
        String idcard = req.getParameter("idcard");
        String dpassword = req.getParameter("dpassword");
        Courier newCourier = new Courier();
        newCourier.setDname(dname);
        newCourier.setSysphone(sysphone);
        newCourier.setIdcard(idcard);
        newCourier.setDpassword(dpassword);
        boolean update = CourierService.update(id, newCourier);
        Message msg = new Message();
        if (update){
            msg.setStatus(0);
            msg.setResult("修改成功");
        }else {
            msg.setStatus(-1);
            msg.setResult("修改失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/courier/delete.do")
    public String delete(HttpServletRequest req,HttpServletResponse resp){
        int id = Integer.parseInt(req.getParameter("id"));
        boolean delete = CourierService.delete(id);
        Message msg = new Message();
        if (delete){
            msg.setStatus(0);
            msg.setResult("删除成功");
        }else {
            msg.setStatus(-1);
            msg.setResult("删除失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
