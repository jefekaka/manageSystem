package com.hemeone.manageSys.controller;

import com.jfinal.core.Controller;

public class UserController extends Controller {
    public void index() {
        setAttr("title", "首页");
        render("/index.jsp");
    }

    public void user() {
        render("/index.jsp");
    }

}
