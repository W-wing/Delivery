package com.kkb.controller;

import com.kkb.bean.BootStrapTableExpress;
import com.kkb.bean.Express;
import com.kkb.bean.Message;
import com.kkb.bean.ResultData;
import com.kkb.mvc.ResponseBody;
import com.kkb.service.ExpressService;
import com.kkb.util.DateFormatUtil;
import com.kkb.util.JSONUtil;
import com.kkb.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpressController {
    @ResponseBody("/express/console.do")
    public String console(HttpServletRequest req, HttpServletResponse resp){
        List<Map<String, Integer>> data = ExpressService.console();
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
    @ResponseBody("/express/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp){
        //1.获取起始的偏移值
        int offset = Integer.parseInt(req.getParameter("offset"));
        //2.获取当前页要查询的数据量
        int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        //3.进行查询
        List<Express> list = ExpressService.findAll(true, offset, pageNumber);
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for (Express e : list) {
            String inTime = DateFormatUtil.format(e.getIntime());
            String outTime = e.getOutime()==null?"未出库":DateFormatUtil.format(e.getOutime());
            String status = e.getStatus()==0?"未取件":"已取件";
            String code = e.getCode()==null?"已取件":e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserphone(),e.getCompany(),code,inTime,outTime,status,e.getSysphone());
            list2.add(e2);
        }
        List<Map<String, Integer>> console = ExpressService.console();
        Integer total = console.get(0).get("data1_size");
        //4.将集合封装为bootstrap-table识别的格式
        ResultData<BootStrapTableExpress> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }
    @ResponseBody("/express/insert.do")
    public String insert(HttpServletRequest req, HttpServletResponse resp){
        String number = req.getParameter("number");
        String company = req.getParameter("company");
        String username = req.getParameter("username");
        String userphone = req.getParameter("userphone");
        Express e = new Express(number,username,userphone,company,UserUtil.getUserPhone(req.getSession()));
        boolean insert = ExpressService.insert(e);
        Message msg = new Message();
        if (insert){
            //录入成功
            msg.setStatus(0);
            msg.setResult("快递录入成功");
        }else {
            msg.setStatus(-1);
            msg.setResult("快递录入失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/express/find.do")
    public String find(HttpServletRequest req, HttpServletResponse resp){
        String number = req.getParameter("number");
        Express e = ExpressService.findByNumber(number);
        Message msg = new Message();
        if (e == null){
            msg.setStatus(-1);
            msg.setResult("单号不存在");
        }else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(e);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/express/update.do")
    public String update(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String number = request.getParameter("number");
        String company = request.getParameter("company");
        String username = request.getParameter("username");
        String userphone = request.getParameter("userphone");
        int status = Integer.parseInt(request.getParameter("status"));
        Express newExpress = new Express();
        newExpress.setNumber(number);
        newExpress.setCompany(company);
        newExpress.setUsername(username);
        newExpress.setUserphone(userphone);
        newExpress.setStatus(status);
        boolean flag = ExpressService.update(id, newExpress);
        Message msg = new Message();
        if(flag){
            msg.setStatus(0);
            msg.setResult("修改成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("修改失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/express/delete.do")
    public String delete(HttpServletRequest req, HttpServletResponse resp){
        int id = Integer.parseInt(req.getParameter("id"));
        boolean delete = ExpressService.delete(id);
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
