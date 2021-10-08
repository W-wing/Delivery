package com.kkb.wx.Controller;

import com.kkb.mvc.ResponseBody;
import com.kkb.util.JSONUtil;
import com.kkb.util.UserUtil;
import com.kkb.service.CourierService;
import com.kkb.service.UserService;
import com.kkb.bean.Courier;
import com.kkb.bean.Message;
import com.kkb.bean.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController {
    @ResponseBody("/wx/loginSms.do")
    public String sendSms(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        //String code = RandomUtil.getCode()+"";
        //boolean flag = SMSUtil.loginSMS(userPhone, code);
        String code = "123456";
        boolean flag = true;
        Message msg = new Message();
        if(flag){
            //短信发送成功
            msg.setStatus(0);
            msg.setResult("验证码已发送,请查收!");
        }else{
            //短信发送失败
            msg.setStatus(1);
            msg.setResult("验证码下发失败,请检查手机号或稍后再试");
        }
        UserUtil.setLoginSms(request.getSession(),userPhone,code);

        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/wx/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        String userCode = request.getParameter("code");
        String sysCode = UserUtil.getLoginSms(request.getSession(), userPhone);
        Message msg = new Message();
        if (sysCode == null){
            msg.setStatus(-1);
            msg.setResult("手机号码未获取短信");
        }else if (sysCode.equals(userCode)){
            // 验证码一致
            // TODO:判断是快递员还是用户，如果都不是则默认注册为用户，如果都是则以快递员身份登录
            User user1 = UserService.findByUphone(userPhone);//这一步查询出来的就是null userphone哪个字段
            Courier courier1 = CourierService.findBySysPhone(userPhone);
            if (user1 == null && courier1 == null){
                // 新注册为用户
                msg.setStatus(0);
                User u = new User();
                u.setUphone(userPhone);
                UserService.insert(u);
                UserUtil.setWxUser(request.getSession(), u);
            }else if (user1 == null && courier1 != null){
                // 以快递员身份登录
                msg.setStatus(1);
                Integer id = courier1.getId();
                CourierService.updateLastTime(id);
                UserUtil.setWxUser(request.getSession(), courier1);
            }else if (user1 != null && courier1 == null){
                // 用户登录
                msg.setStatus(0);
                Integer id = user1.getId();
                UserService.updateLastTime(id);
                UserUtil.setWxUser(request.getSession(), user1);
            }
        }else {
            // 验证码不一致
            msg.setStatus(-2);
            msg.setResult("验证码不一致，请检查");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 判断当前登陆的是用户还是快递员
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/userInfo.do")
    public String userInfo(HttpServletRequest request, HttpServletResponse response){
        Object loginUser = UserUtil.getWxUser(request.getSession());
        boolean isUser = loginUser instanceof User;
        Message msg = new Message();
        if (isUser){
            msg.setStatus(0);
            msg.setResult(((User)loginUser).getUphone());
        }else {
            msg.setStatus(1);
            msg.setResult(((Courier)loginUser).getSysphone());
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        // 销毁session
        request.getSession().invalidate();
        Message msg = new Message(0);
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/wx/userName.do")
    public String userName(HttpServletRequest request, HttpServletResponse response){
        Object loginUser = UserUtil.getWxUser(request.getSession());
        boolean isUser = loginUser instanceof User;
        Message msg = new Message();
        if (isUser){
            msg.setStatus(0);
            msg.setResult(((User)loginUser).getUname());
        }else {
            msg.setStatus(1);
            msg.setResult(((Courier)loginUser).getDname());
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/wx/updateSms.do")
    public String updateSms(HttpServletRequest request, HttpServletResponse response){
        String newPhone = request.getParameter("newPhone");
        // TODO:为了调试的时候不浪费钱，先把这两行注释了
        // String code = RandomUtil.getCode() + "";
        // boolean flag = SMSUtil.loginSMS(userPhone, code);

        String code = "222222";
        boolean flag = true;

        Message msg = new Message();
        if (flag){
            // 短信发送成功
            msg.setStatus(0);
            msg.setResult("验证码已发送，请查收！");
        }else {
            // 短信发送失败
            msg.setStatus(-1);
            msg.setResult("验证码发送失败，请检查手机号或稍后再试");
        }
        UserUtil.setUpdateSms(request.getSession(), newPhone, code);
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/wx/updateSmss.do")
    public String updateSmss(HttpServletRequest request, HttpServletResponse response){
        String newPhone = request.getParameter("newPhone");
        // TODO:为了调试的时候不浪费钱，先把这两行注释了
        // String code = RandomUtil.getCode() + "";
        // boolean flag = SMSUtil.loginSMS(userPhone, code);

        String code = "000000";
        boolean flag = true;

        Message msg = new Message();
        if (flag){
            // 短信发送成功
            msg.setStatus(0);
            msg.setResult("验证码已发送，请查收！");
        }else {
            // 短信发送失败
            msg.setStatus(-1);
            msg.setResult("验证码发送失败，请检查手机号或稍后再试");
        }
        UserUtil.setUpdateSms(request.getSession(), newPhone, code);
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    /**
     * 修改个人信息页面
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response){
        String newName = request.getParameter("newName");
        String newPhone = request.getParameter("newPhone");
        String verify = request.getParameter("verify");

        String sysCode = UserUtil.getUpdateSms(request.getSession(), newPhone);
        Message msg = new Message();
        if (sysCode == null){
            msg.setStatus(-1);
            msg.setResult("手机号码未获取短信");
        }else if (sysCode.equals(verify)){
            Object wxUser = UserUtil.getWxUser(request.getSession());
            if (wxUser instanceof User){
                User oldUser = UserService.findByUphone(((User) wxUser).getUphone());
                int id = oldUser.getId();
                oldUser.setUname(newName);
                oldUser.setUphone(newPhone);
                boolean flag = UserService.update(id, oldUser);
                if (flag){
                    msg.setStatus(0);
                    msg.setResult("修改成功");
                }else {
                    msg.setStatus(-3);
                    msg.setResult("修改失败");
                }
            }else if (wxUser instanceof Courier){
                Courier oldUser = CourierService.findBySysPhone(((Courier)wxUser).getSysphone());
                int id = oldUser.getId();
                oldUser.setDname(newName);
                oldUser.setSysphone(newPhone);
                boolean flag = CourierService.update(id, oldUser);
                if (flag){
                    msg.setStatus(0);
                    msg.setResult("修改成功");
                }else {
                    msg.setStatus(-3);
                    msg.setResult("修改失败");
                }
            }
        }else {
            // 验证码不一致
            msg.setStatus(-2);
            msg.setResult("验证码不一致，请检查");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/wx/updateUser.do")
    public String updateUser(HttpServletRequest req, HttpServletResponse resp){
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String upwd = req.getParameter("upwd");
        String msg1 = req.getParameter("msg");

        String sysCode = UserUtil.getUpdateSms(req.getSession(), phone);
        Message msg = new Message();
        if (sysCode == null){
            msg.setStatus(-1);
            msg.setResult("手机号码未获取短信");
        }else if (sysCode.equals(msg1)){
            Object wxUser = UserUtil.getWxUser(req.getSession());
            if (wxUser instanceof User){
                User oldUser = UserService.findByUphone(((User) wxUser).getUphone());
                int id = oldUser.getId();
                oldUser.setUname(name);
                oldUser.setUphone(phone);
                oldUser.setUpwd(upwd);
                boolean flag = UserService.update(id, oldUser);
                if (flag){
                    msg.setStatus(0);
                    msg.setResult("修改成功");
                }else {
                    msg.setStatus(-3);
                    msg.setResult("修改失败");
                }
            }else if (wxUser instanceof Courier){
                Courier oldUser = CourierService.findBySysPhone(((Courier)wxUser).getSysphone());
                int id = oldUser.getId();
                oldUser.setDname(name);
                oldUser.setSysphone(phone);
                boolean flag = CourierService.update(id, oldUser);
                if (flag){
                    msg.setStatus(0);
                    msg.setResult("修改成功");
                }else {
                    msg.setStatus(-3);
                    msg.setResult("修改失败");
                }
            }
        }else {
            // 验证码不一致
            msg.setStatus(-2);
            msg.setResult("验证码不一致，请检查");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

}

