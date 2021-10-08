package com.kkb.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

import java.util.HashMap;

public class SMSUtil {
    public static boolean send(String phoneNumber,String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GEkHr49fpJMKyRJzcY2", "phG4vtM8Z81WrLS4ZiKJVp0wJ6dZ8b");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "E栈快递");
        request.putQueryParameter("TemplateCode", "SMS_211495702");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+ code +"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            String json = response.getData();
            Gson g = new Gson();
            HashMap result = g.fromJson(json, HashMap.class);
            if("OK".equals(result.get("Message"))) {
                return true;
            }else{
                System.out.println("短信发送失败，原因："+result.get("Message"));
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
    //短信登录验证码测试 修改模板里的数值
    public static boolean loginSMS(String phoneNumber,String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GKsiFzUmLZ8Qnx1Sfza", "jUhIqqk3wwvhm2T1HOUVbCmIOWNE0O");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "快递e栈");
        request.putQueryParameter("TemplateCode", "SMS_204295076");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+ code +"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            String json = response.getData();
            Gson g = new Gson();
            HashMap result = g.fromJson(json, HashMap.class);
            if("OK".equals(result.get("Message"))) {
                return true;
            }else{
                System.out.println("短信发送失败，原因："+result.get("Message"));
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

}

