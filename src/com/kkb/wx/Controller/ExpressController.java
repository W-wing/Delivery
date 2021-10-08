package com.kkb.wx.Controller;

import com.kkb.mvc.ResponseBody;
import com.kkb.util.DateFormatUtil;
import com.kkb.util.JSONUtil;
import com.kkb.util.UserUtil;
import com.kkb.service.ExpressService;
import com.kkb.bean.*;

import javax.persistence.criteria.Expression;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ExpressController {
    @ResponseBody("/wx/findExpressByUserPhone.do")
    public String findByUserPhone(HttpServletRequest request, HttpServletResponse response){
        User wxUser = (User) UserUtil.getWxUser(request.getSession());
        String userPhone = wxUser.getUphone();
        List<Express> list = ExpressService.findByUserPhone(userPhone);
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for(Express e:list){
            String inTime = DateFormatUtil.format(e.getIntime());
            String outTime = e.getOutime()==null?"未出库":DateFormatUtil.format(e.getOutime());
            String status = e.getStatus()==0?"待取件":"已取件";
            String code = e.getCode()==null?"已取件":e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserphone(),e.getCompany(),code,inTime,outTime,status,e.getSysphone());
            list2.add(e2);
        }
        Message msg = new Message();
        if(list.size()==0){
            msg.setStatus(-1);
        }else{
            msg.setStatus(0);
            Stream<BootStrapTableExpress> status0Express = list2.stream().filter(express -> {
                if (express.getStatus().equals("待取件")) {
                    return true;
                } else {
                    return false;
                }
            }).sorted((o1,o2) -> {
                long o1Time = DateFormatUtil.toTime(o1.getIntime());
                long o2Time = DateFormatUtil.toTime(o2.getIntime());
                return (int)(o1Time-o2Time);
            });
            Stream<BootStrapTableExpress> status1Express = list2.stream().filter(express -> {
                if (express.getStatus().equals("已取件")) {
                    return true;
                } else {
                    return false;
                }
            }).sorted((o1,o2) -> {
                long o1Time = DateFormatUtil.toTime(o1.getIntime());
                long o2Time = DateFormatUtil.toTime(o2.getIntime());
                return (int)(o1Time-o2Time);
            });
            Object[] s0 = status0Express.toArray();
            Object[] s1 = status1Express.toArray();
            Map data = new HashMap<>();
            data.put("status0",s0);
            data.put("status1",s1);
            msg.setData(data);
        }
        String json = JSONUtil.toJSON(msg.getData());
        return json;
    }

    @ResponseBody("/wx/userExpressList.do")
    public String expressList(HttpServletRequest request,HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        List<Express> list = ExpressService.findByUserPhoneAndStatus(userPhone, 0);
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for(Express e:list){
            String inTime = DateFormatUtil.format(e.getIntime());
            String outTime = e.getOutime()==null?"未出库":DateFormatUtil.format(e.getOutime());
            String status = e.getStatus()==0?"待取件":"已取件";
            String code = e.getCode()==null?"已取件":e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserphone(),e.getCompany(),code,inTime,outTime,status,e.getSysphone());
            list2.add(e2);
        }
        Message msg = new Message();
        if(list.size() == 0){
            msg.setStatus(-1);
            msg.setResult("未查询到快递");
        }else{
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(list2);
        }
        return JSONUtil.toJSON(msg);
    }
    @ResponseBody("/wx/searchExpress.do")
    public String searchExpress(HttpServletRequest request, HttpServletResponse response){
        String expressNum = request.getParameter("expressNum");
        Object wxUser = UserUtil.getWxUser(request.getSession());
        String userPhone = "";
        Message msg = new Message();
        if (wxUser instanceof User){
            userPhone = ((User) wxUser).getUphone();
        }else if (wxUser instanceof Courier){
            userPhone = ((Courier) wxUser).getSysphone();
        }

        Express express = ExpressService.findByNumber(expressNum);
        System.out.println(express);
        if (express != null && express.getUserphone().equals(userPhone)){
            HttpSession session = request.getSession();
            session.setAttribute("searchExpress",express);
        }
        msg.setStatus(0);
        msg.setResult("查询成功");
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/wx/insert.do")
    public String insert(HttpServletRequest req,HttpServletResponse resp){
        String number = req.getParameter("number");
        String username = req.getParameter("username");
        String userphone = req.getParameter("userphone");
        String company = req.getParameter("company");
        Express e = new Express(number,username,userphone,company,UserUtil.getUserPhone(req.getSession()));
        boolean insert = ExpressService.insert(e);
        Message msg = new Message();
        if (insert){
            msg.setStatus(0);
            msg.setResult("快递录入成功");
        }else {
            msg.setStatus(-1);
            msg.setResult("快递录入失败");
        }
        return JSONUtil.toJSON(msg);
    }

}
