package com.example.demo.service.impl;

import com.example.demo.core.ret.ServiceException;
import com.example.demo.core.universal.AbstractService;
import com.example.demo.dao.UserInfoMapper;
import com.example.demo.model.UserInfo;
import com.example.demo.service.UserInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


@Service
public class UserInfoServiceImpl extends AbstractService<UserInfo> implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo selectById(Integer id) {
        UserInfo userInfo = userInfoMapper.selectById(id);
        if(userInfo ==null){
            throw new ServiceException("暂无当前的用户");
        }
        return userInfo;
    }

    @Override
    public PageInfo<UserInfo> selectAll(Integer page, Integer size) {
        // 开启分页的语句写在分页查询语句的上方，只有紧跟在PageHelper.startPage 方法后的第一个Mybatis的查询方法会被分页
        PageHelper.startPage(page,size);
        List<UserInfo> userInfoList = userInfoMapper.selectAll();
        PageInfo<UserInfo> pageInfo = new PageInfo<>(userInfoList);
        return pageInfo;
    }
}
