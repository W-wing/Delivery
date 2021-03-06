package com.kkb.mvc;

import java.lang.annotation.*;

/**
 * 注解的作用
 * 被此注解添加的方法，会被用于处理请求
 * 方法返回的内容，会以文字形式返回到客户端
 * @author H
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseBody {
    String value();
}
