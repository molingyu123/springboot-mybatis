package com.example.demo;



import com.alibaba.druid.util.StringUtils;
import com.example.demo.core.constant.ProjectConstant;
import com.google.common.base.CaseFormat;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器，根据数据表名称生成对应得Model,Mapper 简化开发
 */
public class CodeGenerator {

    // JDBC 配置
    private static final String JDBC_URL ="jdbc:mysql://localhost:3306/demo";
    private static final String JDBC_USERNAME ="root";
    private static final String JDBC_PASSWORD ="123456";
    private static final String JDBC_DIVER_CLASS_NAME="com.mysql.jdbc.Driver";
    private static final String JAVA_PATH ="src/main/java";// java 文件路径
    private static final String RESOURCES_PATH ="src/main/resources";// 资源文件路径

    public static void main(String []args){
        genCode("system_log");
    }

    public static void genCode(String... tableNames){
        for(String tableName :tableNames){
           genCode(tableName,null);
        }
    }

    /**
     * 通过数据表的名称，和自定义的model名称生成代码
     * 如输入表名称 "t_user_detail" 和自定义的 Model 名称 "User"
     *     * 将生成 User、UserMapper、UserService ...
     *     *
     * @param tableName
     * @param modelName
     */
    public static void genCode(String tableName ,String modelName){
       genModelAndMapper(tableName,modelName);
    }

    /**
     * 封装生成的对象的方法
     * @param tableName
     * @param modelName
     */
    public static void genModelAndMapper(String tableName,String modelName){
        Context context =getContext();
        JDBCConnectionConfiguration jdbcConnectionConfiguration =getJDBCConnectionConfiguration();
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        PluginConfiguration pluginConfiguration = getPluginConfiguration();
        context.addPluginConfiguration(pluginConfiguration);

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = getJavaModelGeneratorConfiguration();
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = getSqlMapGeneratorConfiguration();
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = getJavaClientGeneratorConfiguration();
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        tableConfiguration.setDomainObjectName(modelName);
        context.addTableConfiguration(tableConfiguration);

        List<String> warings=null;
        MyBatisGenerator generator;
        try{
            Configuration configuration = new Configuration();
            configuration.addContext(context);
            configuration.validate();
            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warings = new ArrayList<>();
            generator = new MyBatisGenerator(configuration,callback,warings);
            generator.generate(null);
        }catch (Exception e){
            throw new RuntimeException("生成model和mapper失败",e);
        }
        if(StringUtils.isEmpty(modelName)){
            modelName = tableNameConvertUpperCamel(tableName);
        }
        System.out.println(modelName+".java生成成功");
        System.out.println(modelName+"Mapper.java生成成功");
        System.out.println(modelName+"Mapper.xml生成成功");
    }


    /**
     * 获取上下文
     * @return
     */
    public static Context getContext(){
        Context context = new Context(ModelType.FLAT);
        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER,"");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER,"");
       return context;
    }

    /**
     * 连接配置
     * @return
     */
    public static JDBCConnectionConfiguration getJDBCConnectionConfiguration(){
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        return jdbcConnectionConfiguration;
    }

    /**
     * mybatis 插件配置
     * @return
     */
    public static PluginConfiguration getPluginConfiguration(){
         PluginConfiguration pluginConfiguration = new PluginConfiguration();
         pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
         pluginConfiguration.addProperty("mappers",ProjectConstant.MAPPER_INTERFACE_REFERENCE);
         return pluginConfiguration;
    }

    /**
     *  java 生成实体对象配置地址
     * @return
     */
    public static JavaModelGeneratorConfiguration getJavaModelGeneratorConfiguration(){
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(ProjectConstant.MODEL_PACKAGE);
        javaModelGeneratorConfiguration.addProperty("enableSubPackages","true");
        javaModelGeneratorConfiguration.addProperty("trimStrings","true");
        return javaModelGeneratorConfiguration;
    }

    /**
     * xml 生成地址
     */
    public static SqlMapGeneratorConfiguration getSqlMapGeneratorConfiguration(){
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        return sqlMapGeneratorConfiguration;
    }

    /**
     * 生成mappper xml对应client，也就是接口dao
     * @return
     */
    public static JavaClientGeneratorConfiguration getJavaClientGeneratorConfiguration(){
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(ProjectConstant.MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        return javaClientGeneratorConfiguration;
    }

    public static String tableNameConvertUpperCamel(String tableName){
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,tableName.toLowerCase());
    }
}
