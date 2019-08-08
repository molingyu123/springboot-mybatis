package com.example.demo.controller;

import com.example.demo.core.ret.RetResponse;
import com.example.demo.core.ret.RetResult;
import com.example.demo.model.UserInfo;
import com.example.demo.service.UserInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/userInfo")
@Api(tags = {"用户操作接口"},description = "userInfoController")// 接口文档生成的测试
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/hello")
    public String hello(){
        return "HELLO java!";
    }

    @ApiOperation(value = "查询用户",notes = "根据用户ID查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id",required = true, dataType = "String",paramType = "query")
    })
    @PostMapping("/selectById")
    public RetResult<UserInfo> selectById(@RequestParam Integer id){
        UserInfo userInfo = userInfoService.selectById(id);
        return RetResponse.makeOKRsp(userInfo);
    }

    @PostMapping("/testException")
    public RetResult<UserInfo> testException(Integer id){
        List a =null;
        a.size();
        UserInfo userInfo = userInfoService.selectById(id);
        return RetResponse.makeOKRsp(userInfo);
    }

    @ApiOperation(value = "查询用户",notes = "分页查询用户所有")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "当前页码",dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="size",value = "每页显示的条数",dataType = "Integer",paramType = "query")
    })
    @PostMapping("/selectAll")
    public RetResult<PageInfo<UserInfo>> retResult(@RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "0") Integer size){
        PageInfo<UserInfo> pageInfo = userInfoService.selectAll(page,size);
        return  RetResponse.makeOKRsp(pageInfo);
    }


}
