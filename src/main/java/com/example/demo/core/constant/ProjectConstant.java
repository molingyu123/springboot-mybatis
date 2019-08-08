package com.example.demo.core.constant;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * 系统常用变量文件
 */
public class ProjectConstant {

    // 项目基础包名
    public static final String BASE_PACKAGE="com.example.demo";

    //model 所在包
    public static final String MODEL_PACKAGE =BASE_PACKAGE+".model";

    // mapper 所在包
    public static final String MAPPER_PACKAGE =BASE_PACKAGE+".dao";

    // Service所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE+".service";

    //service impl 所在包
    public static final String SERVICE_IMPL_PACKAGE =SERVICE_PACKAGE+".impl";

    //controller 所在包
    public static final String CONTROLLER_PACKAGE =BASE_PACKAGE+".controller";

    //MAPPER 插件基础接口得完全限定名
    public static final String MAPPER_INTERFACE_REFERENCE =BASE_PACKAGE+".core.universal.Mapper";
}
