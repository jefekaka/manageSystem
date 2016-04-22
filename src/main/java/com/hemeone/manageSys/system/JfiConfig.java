package com.hemeone.manageSys.system;

import com.hemeone.manageSys.jfinal.FrontRoutes;
import com.hemeone.manageSys.kit.DataSourcesKit;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext2.plugin.druid.DruidEncryptPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * final的配置类  继承JFinalConfig     
 * 也可以继承JFinalConfigExt的扩张类   已实现数据源的读取和其他model的生成好像 但是有点问题
 * @author liwb
 *
 */
public class JfiConfig extends JFinalConfig { // JFinalConfigExt{
	
	public void afterJFinalStart(){
		//JFinal.start("WebRoot", 8080, "/", 5);
	};

	//设置常量类的
	public void configConstant(Constants me) {   
		me.setDevMode(true);
	}  
	
	public void configRoute(Routes me) { 
		me.add(new FrontRoutes());
		//me.add(new AdminRoutes());
	}  
	
	//插件配置
	public void configPlugin(Plugins me) {
	
		DruidEncryptPlugin drp = DataSourcesKit.getDataSource();		//获取Property的数据源   生成DruidEncryptPlugin
		me.add(drp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(drp);
		me.add(arp);
		
		//用Generate生成器  根据数据库中的表映射生成对应的model   里面应该会记录model 和 table 的对应关系  待测试
		//容器对model类的加载  是否已加载到容器中  
		DataSourcesKit.gerneratorModel(drp.getDataSource());
		
		/*arp.addMapping("role", Role.class);
		arp.addMapping("user", User.class);
		arp.addMapping("comment", Comment.class);
		arp.addMapping("weibo", Weibo.class);*/
		
		
		
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


	/*@Override
	public void configMoreConstants(Constants me) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void configMoreRoutes(Routes me) {
		// TODO Auto-generated method stub
		me.add(new FrontRoutes());
	}


	@Override
	public void configMorePlugins(Plugins me) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void configTablesMapping(String configName, ActiveRecordPlugin arp) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void configMoreInterceptors(Interceptors me) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void configMoreHandlers(Handlers me) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void afterJFinalStarted() {
		// TODO Auto-generated method stub
		
	} */
}
