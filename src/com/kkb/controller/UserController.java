package com.kkb.controller;

import com.kkb.bean.*;
import com.kkb.mvc.ResponseBody;
import com.kkb.service.CourierService;
import com.kkb.service.UserService;
import com.kkb.util.DateFormatUtil;
import com.kkb.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserController {
    @ResponseBody("/user/console.do")
    public String console(HttpServletRequest req, HttpServletResponse resp){
        List<Map<String, Integer>> data = UserService.console();
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
    @ResponseBody("/user/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp){
        //1.获取起始的偏移值
        int offset = Integer.parseInt(req.getParameter("offset"));
        //2.获取当前页要查询的数据量
        int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        //3.进行查询
        List<User> list = UserService.findAll(true, offset, pageNumber);
        List<BootStrapTableUser> list2 = new ArrayList<>();
        for (User u : list) {
            String regtime = DateFormatUtil.format(u.getRegtime());
            String lasttime = u.getLasttime() == null?DateFormatUtil.format(u.getRegtime()):DateFormatUtil.format(u.getLasttime());
            BootStrapTableUser u1 = new BootStrapTableUser(u.getId(),u.getUname(),u.getUphone(),u.getIdno(),u.getUpwd(),regtime,lasttime);
            list2.add(u1);
        }
        List<Map<String, Integer>> console = UserService.console();
        Integer total = console.get(0).get("data1_size");
        //4.将集合封装为bootstrap-table识别的格式
        ResultData<BootStrapTableUser> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }
    @ResponseBody("/user/insert.do")
    public String insert(HttpServletRequest req, HttpServletResponse resp){
        String uname = req.getParameter("uname");
        String uphone = req.getParameter("uphone");
        String idno = req.getParameter("idno");
        String upwd = req.getParameter("upwd");
        User u = new User(uname,uphone,idno,upwd);
        boolean insert = UserService.insert(u);
        Message msg = new Message();
        if (insert){
            msg.setStatus(0);
            msg.setResult("用户录入成功");
        }else {
            msg.setStatus(-1);
            msg.setResult("用户录入失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/user/find.do")
    public String find(HttpServletRequest req, HttpServletResponse resp){
        String uphone = req.getParameter("uphone");
        User u = UserService.findByUphone(uphone);
        Message msg = new Message();
        if (u == null){
            msg.setStatus(-1);
            msg.setResult("该电话不存在");
        }else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(u);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/user/update.do")
    public String update(HttpServletRequest req, HttpServletResponse resp){
        int id = Integer.parseInt(req.getParameter("id"));
        String uname = req.getParameter("uname");
        String uphone = req.getParameter("uphone");
        String idno = req.getParameter("idno");
        String upwd = req.getParameter("upwd");
        User newUser = new User();
        newUser.setUname(uname);
        newUser.setUphone(uphone);
        newUser.setIdno(idno);
        newUser.setUpwd(upwd);
        boolean update = UserService.update(id, newUser);
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
    @ResponseBody("/user/delete.do")
    public String delete(HttpServletRequest req,HttpServletResponse resp){
        int id = Integer.parseInt(req.getParameter("id"));
        boolean delete = UserService.delete(id);
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
