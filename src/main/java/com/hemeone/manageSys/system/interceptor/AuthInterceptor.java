package com.hemeone.manageSys.system.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.hemeone.manageSys.system.Constant;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Const;
import com.jfinal.core.Controller;

/**
 * 登陆拦截器
 * 
 * @author yanglinghui
 */
public class AuthInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation ai) {
        // 获取controller
        Controller controller = ai.getController();
        // 获取controller 的参数
        String userName = controller.getCookie("USER_LOGIN_NAME");
        String token = controller.getCookie("USER_LOGIN_TOKEN");
        try {
        	
        } catch (Exception e) {
            try {
                controller.redirect("/login?redirect="
                        + URLEncoder.encode(controller.getRequest().getServletPath(), Const.DEFAULT_ENCODING));
            } catch (UnsupportedEncodingException e1) {
                controller.redirect("/login");
            }
        }
    }
}

class AuthException extends Exception {}
