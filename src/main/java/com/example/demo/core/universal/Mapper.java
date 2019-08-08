package com.example.demo.core.universal;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 定制版 MyBatis Mapper 插件 接口，文档有其它的接口可添加
 */
public interface Mapper<T> extends BaseMapper<T>,ConditionMapper<T>,IdsMapper<T>,InsertListMapper<T> {
}
