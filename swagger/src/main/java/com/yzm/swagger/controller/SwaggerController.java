package com.yzm.swagger.controller;

import com.yzm.swagger.entity.User;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;

@Slf4j
@RestController
@RequestMapping("/swagger")
@Api(value = "用户信息", tags = {"用户信息API"})
public class SwaggerController {

    @ApiOperation(value = "查询用户列表接口", notes = "描述：根据用户名模糊搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = false, dataType = "Integer", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示多少条数据", required = false, dataType = "Integer", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "count", required = false, dataType = "String", paramType = "query", defaultValue = "叶")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数错误或不能满足要求"),
            @ApiResponse(code = 401, message = "无权访问该接口，需授权"),
            @ApiResponse(code = 404, message = "请求路径不存在"),
            @ApiResponse(code = 500, message = "服务器重启中")
    })
    @GetMapping
    public ModelMap listUser(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "name", required = false) String name) {
        log.info("## 请求时间：{}", new Date());

        ModelMap map = new ModelMap();
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("name", name);
        return map;
    }

    @ApiOperation(value = "用户详情接口", notes = "根据用户ID查询详情")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Integer getUserById(
            @ApiParam(name = "userId", value = "用户ID", required = true, type = "long", defaultValue = "10", example = "10")
            @PathVariable("userId") Integer userId) {
        log.info("## 请求时间：{}", new Date());
        return userId;
    }

    // @ApiParam跟@ApiImplicitParam功能差不多，推荐使用@ApiImplicitParam
    @ApiOperation(value = "删除用户接口", notes = "根据用户ID删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "long", paramType = "path", defaultValue = "10", example = "10")
    })
    @DeleteMapping("/{userId}")
    public Integer removeUserById(@PathVariable("userId") Integer userId) {
        log.info("## 请求时间：{}", new Date());
        return userId;
    }

    @ApiOperation(value = "新增用户接口", notes = "新增用户信息")
    @PostMapping(path = "/save")
    public User saveSwaggerUser(@RequestBody User user, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            System.out.println(headerNames.nextElement());
        }
        return user;
    }



}
