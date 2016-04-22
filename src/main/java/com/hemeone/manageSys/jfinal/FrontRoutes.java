package com.hemeone.manageSys.jfinal;

import com.hemeone.manageSys.controller.UserController;
import com.jfinal.config.Routes;

public class FrontRoutes extends Routes{

	@Override
	public void config() {
		// TODO Auto-generated method stub
		add("/", UserController.class); 
	}

}
