package com.example.demo.service;

import com.example.demo.core.universal.Service;
import com.example.demo.model.UserInfo;
import com.github.pagehelper.PageInfo;

public interface UserInfoService extends Service<UserInfo> {

    UserInfo selectById(Integer id);

    PageInfo<UserInfo> selectAll(Integer page,Integer size);
}
