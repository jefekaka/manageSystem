package com.hemeone.manageSys.system.interceptor;

import com.hemeone.manageSys.system.Constant;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class BaseInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        controller.setAttr("WEB_SITE_TITLE", "JLook内容管理平台");
        if (controller.getSessionAttr(Constant._CURRENT_USER_) != null) {
            System.out.println("-- 加载菜单 --");
        }
        inv.invoke();
    }

}
