package com.kkb.controller;

import com.kkb.bean.Message;
import com.kkb.mvc.ResponseBody;
import com.kkb.service.AdminService;
import com.kkb.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class AdminController {
    @ResponseBody("/admin/login.do")
    public String login(HttpServletRequest req, HttpServletResponse resp){
        //1.接参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        AdminService.login(username,password);
        //2.调用service传参数，并获取结果
        boolean result = AdminService.login(username,password);
        //3.根据结果，返回不同的数据给ajax
        Message msg = null;
        if (result){
            msg = new Message(0,"登录成功");
            //登录时间和IP的更新
            Date date = new Date();
            String ip = req.getRemoteAddr();
            AdminService.updateLoginTimeAndIP(username,date,ip);
            req.getSession().setAttribute("adminUserName","username");
        }else {
            msg = new Message(-1,"登录失败");
        }
        //4.将数据转换为JSON
        String json = JSONUtil.toJSON(msg);
        //5.将json回复给Ajax
        return json;
    }
}
