package com.kkb.util;

import com.kkb.bean.Courier;
import com.kkb.bean.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class UserUtil {

    public static String getUserName(HttpSession session){
        return (String) session.getAttribute("adminUserName");
    }

    public static String getUserPhone(HttpSession session){
        // TODO: 还没有编写柜子端，未存储任何录入人信息
        return "13000000000";
    }

    /**
     * 获取登录验证码
     * @param session
     * @param userPhone
     * @return
     */
    public static String getLoginSms(HttpSession session, String userPhone){
        return (String) session.getAttribute(userPhone);
    }

    /**
     * 设置登录验证码
     * @param session
     * @param userPhone
     * @param code
     */
    public static void setLoginSms(HttpSession session, String userPhone, String code){
        session.setAttribute(userPhone, code);
    }

    /**
     * 方法重载，当前登录的可能是用户也可能是快递员
     * @param session
     * @param obj
     */
    public static void setWxUser(HttpSession session, Object obj){
        session.setAttribute("wxUser", obj);
    }

    /**
     * 获取当前登录的用户or快递员
     * @param
     * @return
     */
    public static Object getWxUser(HttpSession session){
        return session.getAttribute("wxUser");
    }
    /**
     * 获取修改信息验证码
     * @param session
     * @param userPhone
     * @return
     */
    public static String getUpdateSms(HttpSession session, String userPhone){
        return (String) session.getAttribute(userPhone+"_update");
    }
    /**
     * 设置修改验证码
     * @param session
     * @param userPhone
     * @param code
     */
    public static void setUpdateSms(HttpSession session, String userPhone, String code){
        session.setAttribute(userPhone+"_update", code);
    }

}
