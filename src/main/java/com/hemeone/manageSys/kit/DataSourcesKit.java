package com.hemeone.manageSys.kit;

import javax.sql.DataSource;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.ext2.plugin.druid.DruidEncryptPlugin;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.plugin.activerecord.generator.Generator;

public class DataSourcesKit {
    private static final String cfg               = "cfg.properties";
    private static final String ACTIVE_TEMPLATE   = "db.%s.active";
    private static final String URL_TEMPLATE      = "jdbc:%s://%s";
    private static final String USER_TEMPLATE     = "db.%s.user";
    private static final String PASSWORD_TEMPLATE = "db.%s.password";
    private static final String INITSIZE_TEMPLATE = "db.%s.initsize";
    private static final String MAXSIZE_TEMPLATE  = "db.%s.maxactive";
    private static Prop         prop              = null;

    private static void loadPropertyFile() {
        if (prop == null) {
            prop = new Prop(cfg, "UTF-8");
        }
    }

    /**
     * 获取数据源
     * 
     * @return
     */
    private static String getDataSources() {
        loadPropertyFile();
        String ds = getProperty("db.ds");
        return ds;
    }

    private static String getProperty(String key) {
        return prop.get(key);
    }

    public static Integer getPropertyToInt(String key) {
        return prop.getInt(key);
    }

    /**
     * 根据数据源配置 生成DruidEncryptPlugin
     * @param ds
     * @return
     */
    private static DruidEncryptPlugin getDruidPlugin(String ds) {
        loadPropertyFile();
        String url = getProperty(String.format("db.%s.url", ds));
        url = String.format(URL_TEMPLATE, ds, url);
        if (!url.endsWith("?characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull")) {
            url += "?characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull";
        }
        DruidEncryptPlugin dp = new DruidEncryptPlugin(url, getProperty(String.format(USER_TEMPLATE, ds)),
                getProperty(String.format(PASSWORD_TEMPLATE, ds)));
        dp.setInitialSize(getPropertyToInt(String.format(INITSIZE_TEMPLATE, ds)));		//数据库连接池初始大小
        dp.setMaxActive(getPropertyToInt(String.format(MAXSIZE_TEMPLATE, ds)));			//数据库连接池最大激活数
        dp.addFilter(new StatFilter());			//添加StatFilter  拦截器
        WallFilter wall = new WallFilter();
        wall.setDbType(ds);
        dp.addFilter(wall);			//添加WallFilter  拦截器
        return dp;
    }

    public static Boolean getPropertyToBoolean(String key, Boolean defaultValue) {
        return prop.getBoolean(key, defaultValue);
    }

    /**
     * 获取是否打开数据库状态
     * 
     * @return
     */
    private static boolean getDbActiveState(String ds) {
        loadPropertyFile();
        return getPropertyToBoolean(String.format(ACTIVE_TEMPLATE, ds), false);
    }

    /**
     * 获取数据源
     */
    public static DruidEncryptPlugin getDataSource() {
        String ds = getDataSources();
        if (!getDbActiveState(ds)) ;
        DruidEncryptPlugin drp = getDruidPlugin(ds);
        return drp;
    }
    
    /**
     * 本地调用方法  用于生成model
     * @param args
     */
    public static void main(String[] args) {
    	//System.out.println(com.alibaba.druid.filter.config.ConfigTools.encrypt(""));
        loadPropertyFile();
        // base model 所使用的包名
        String baseModelPackageName = getProperty("ge.base.model.package");
        boolean isMaven = getPropertyToBoolean("ge.maven", false);
        String outDir = PathKit.getWebRootPath() + "/src/";
        if (isMaven) outDir += "main/java/";
        outDir += baseModelPackageName.replaceAll("\\.", "/");
        // base model 文件保存路径
        String baseModelOutputDir = outDir;
        
        
        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = getProperty("ge.model.package");
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";
        DataSource ds = (DataSource) getDataSource();
        // 创建生成器
        Generator gernerator = new Generator(ds, baseModelPackageName, baseModelOutputDir, modelPackageName,
                modelOutputDir);
        // 添加不需要生成的表名
        gernerator.addExcludedTable(getProperty("ge.excludetable").split(","));
        // 设置是否在 Model 中生成 dao 对象
        gernerator.setGenerateDaoInModel(true);
        // 设置是否生成字典文件
        gernerator.setGenerateDataDictionary(getPropertyToBoolean("ge.dict", false));
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为
        // "User"而非 OscUser
        if (getPropertyToBoolean("ge.prefixes.remove", false))
            gernerator.setRemovedTableNamePrefixes(getProperty("ge.prefixes").split(","));
        // 生成
        gernerator.generate();
    
	}
}
