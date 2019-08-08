package com.example.demo.dao;

import com.example.demo.core.universal.Mapper;
import com.example.demo.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserInfoMapper extends Mapper<UserInfo> {

    UserInfo selectById(@Param("id") Integer id);

    List<UserInfo> selectAll();
}
