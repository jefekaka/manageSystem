package com.hemeone.manageSys.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class UserController extends Controller {
    public void index() {
    	Record r = Db.findById("user", 1);
        renderJson(r);
    }

    public void user() {
    	Record r = Db.findById("user", 1);
        renderJson(r);
        //("/index.jsp");
    }

}
