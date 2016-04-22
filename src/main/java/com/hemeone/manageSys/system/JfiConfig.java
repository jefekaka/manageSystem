package com.hemeone.manageSys.system;

import javax.sql.DataSource;

import com.hemeone.manageSys.controller.UserController;
import com.hemeone.manageSys.kit.DataSourcesKit;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext2.plugin.druid.DruidEncryptPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;


public class JfiConfig extends JFinalConfig{
	
	public void afterJFinalStart(){
		//JFinal.start("WebRoot", 8080, "/", 5);
	};

	
	//设置常量类的
	public void configConstant(Constants me) {   
		me.setDevMode(true);
	}  
	
	public void configRoute(Routes me) { 
		me.add("/user", UserController.class);
	}  
	
	//插件配置
	public void configPlugin(Plugins me) {
		DruidEncryptPlugin drp = DataSourcesKit.getDataSource();		//获取Property的数据源   生成DruidEncryptPlugin
		me.add(drp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(drp);
		me.add(arp);
		
		//arp.addMapping("role", Role.class);
		//arp.addMapping("user", User.class);
		//arp.addMapping("comment", Comment.class);
		//arp.addMapping("weibo", Weibo.class);
		
		
		
		// 非第一次使用use加载的配置，需要通过每次使用use来指定配置文件名再来取值 
		//String redisHost = PropKit.use("redis_config.txt").get("host"); 
		//int redisPort = PropKit.use("redis_config.txt").getInt("port"); 
		//RedisPlugin rp = new RedisPlugin("myRedis", redisHost, redisPort); 
		//me.add(rp);
	}  
	
	//拦截器
	public void configInterceptor(Interceptors me) {
		
	}  
	
	//处理器
	public void configHandler(Handlers me) {} 
}
