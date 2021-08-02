package com.mybatisplustemplate.demo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CodeGenerator {
    /**
     * 代码生成位置
     */
    public static final String PARENT_NAME = "com.wdxx";

    /**
     * modular 名字
     */
  //  public static final String MODULAR_NAME = "";

    /**
     * 基本路径
     */
   // public static final String SRC_MAIN_JAVA = "src/main/java/";

    /**
     * 作者
     */
    public static final String AUTHOR = "CodeGenerator";

    /**
     * 生成的文件的绝对路径
     */
    public static final String FILEPATH = "D:/mydemo/mybatisplus-redis-test/misaddress/src/main/java";

    /**
     * 是否是 rest 接口
     */
    private static final boolean REST_CONTROLLER_STYLE = true;

    /**
     * 数据库信息
     */
    public static final String JDBC_MYSQL_URL = "jdbc:mysql://121.40.106.103:3306/barmaster_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false";

    public static final String JDBC_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    public static final String JDBC_USERNAME = "rzyc";

    public static final String JDBC_PASSWORD = "admin@rzyc.com##";

    public static void main(String[] args) {
        String moduleName ="mybatisandredis" ;//模块名
        String tableName = "sys_resource";//表名
        String tablePrefix = "";//表前缀
        autoGenerator(moduleName, tableName, tablePrefix);
    }

    public static void autoGenerator(String moduleName, String tableName, String tablePrefix) {
        new AutoGenerator()
                .setGlobalConfig(getGlobalConfig())
                .setDataSource(getDataSourceConfig())
                .setPackageInfo(getPackageConfig(moduleName))
                .setStrategy(getStrategyConfig(tableName, tablePrefix))
                .setCfg(getInjectionConfig(moduleName))
                .setTemplate(getTemplateConfig())
                .execute();
    }

    private static String getDateTime() {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDate.format(formatter);
    }

    private static InjectionConfig getInjectionConfig(final String moduleName) {
        return new InjectionConfig() {
            @Override
            public void initMap() {
                Map map = new HashMap();
                map.put("dateTime", getDateTime());
                setMap(map);
                final String projectPath = FILEPATH;
                List<FileOutConfig> fileOutConfigList = new ArrayList<FileOutConfig>();
                // 自定义配置会被优先输出
                fileOutConfigList.add(new FileOutConfig("/templates/mapper.xml.vm") {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        // 自定义输出文件名，如果entity设置了前后缀，此次注意xml的名称也会跟着发生变化
                        return projectPath + "/src/main/resources/mapper/"
                                 + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                    }
                });
                setFileOutConfigList(fileOutConfigList);
            }
        };
    }


    private static StrategyConfig getStrategyConfig(String tableName, String tablePrefix) {
        return new StrategyConfig()
                .setCapitalMode(false)
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableName)
                .setRestControllerStyle(REST_CONTROLLER_STYLE)
                .setEntityBuilderModel(true)
                .setControllerMappingHyphenStyle(true)
                .entityTableFieldAnnotationEnable(true)
                .setTablePrefix(tablePrefix + "_");
    }

    private static PackageConfig getPackageConfig(String moduleName) {
        return new PackageConfig()
                .setModuleName(moduleName)
                .setParent(PARENT_NAME)
                .setService("service")
                .setServiceImpl("service.impl")
                .setController("controller")
                .setEntity("model");
    }

    private static DataSourceConfig getDataSourceConfig() {
        return new DataSourceConfig()
                .setUrl(JDBC_MYSQL_URL)
                .setDriverName(JDBC_DRIVER_NAME)
                .setUsername(JDBC_USERNAME)
                .setPassword(JDBC_PASSWORD);
    }

    private static GlobalConfig getGlobalConfig() {
        String filePath =FILEPATH;
        return new GlobalConfig()
                .setOutputDir(filePath)
                .setDateType(DateType.ONLY_DATE)
                .setIdType(IdType.AUTO)
                .setAuthor(AUTHOR)
                .setBaseColumnList(true)
                .setSwagger2(true)
                .setEnableCache(false)
                .setBaseResultMap(true)
                .setOpen(false);
    }

    private static TemplateConfig getTemplateConfig() {
        return new TemplateConfig()
                .setController("/controller.java.vm")
                .setService("/service.java.vm")
                .setServiceImpl("/serviceImpl.java.vm")
                .setEntity("/entity.java.vm")
                .setMapper("/mapper.java.vm")
                .setXml("/mapper.xml.vm");
    }

    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        sb.append("please input " + tip + " : ");
        System.out.println(sb.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("please input the correct " + tip + ". ");
    }
}