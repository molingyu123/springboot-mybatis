package com.example.demo.core.universal;

import java.util.List;
import java.util.TooManyListenersException;
import java.util.concurrent.locks.Condition;

/**
 *  Service 层基础接口 ,其它service 接口请继承改接口
 * @param <T>
 */
public interface Service<T> {

    /**
     *  持久化
     * @param model
     * @return integer
     */
    Integer insert(T model);

    /**
     * 根据id 主键进行删除
     * @param id
     * @return
     */
    Integer deleteById(String id);

    /**
     * 批量删除
     * @param ids --eg: ids-->1,2,3
     * @return
     */
    Integer deleteByIds(String ids);

    /**
     * 更新对象
     * @param model
     * @return
     */
    Integer update(T model);

    /**
     * 根据id 查询
     * @param id
     * @return
     */
    T selectById(String id);

    /**
     * 通过model 中某个变量名称查找，value 需要符合unique 约束
     * @param fieldName
     * @param value
     * @return
     * @throws TooManyListenersException
     */
    T selectBy(String fieldName ,Object value) throws TooManyListenersException;

    /**
     * 根据名称查询，返回多个结果集
     * @param fieldName
     * @param value
     * @return
     */
    List<T> selectListBy(String fieldName,Object value);

    /**
     * 根据多个id 查找，返回多个结果
     * @param ids
     * @return
     */
    List<T> selectListByIds(String ids);

    /**
     * 根据条件查询
     * @param condition
     * @return
     */
    List<T> selectByCondition(Condition condition);

    /**
     * 查询所有数据
     * @return
     */
    List<T> selectAll();

    /**
     * 根据实体中属性值进行查询，查询条件使用等号
     * @param record
     * @return
     */
    List<T> select(T record);

    /**
     * 根据实体中属性进行查询，查询条件使用等号
     * @param record
     * @return
     */
    T selectOne(T record);



}
