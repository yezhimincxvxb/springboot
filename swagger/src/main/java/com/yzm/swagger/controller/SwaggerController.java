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
//@RequestMapping("/swagger")
@Api(value = "用户信息", tags = {"用户信息API"})
public class SwaggerController {

    @ApiOperation(value = "查询用户列表接口", notes = "描述：根据用户名模糊搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = false, dataType = "Integer", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示多少条数据", required = false, dataType = "Integer", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "搜索条件", required = false, dataType = "String", paramType = "query", defaultValue = "叶")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数错误或不能满足要求"),
            @ApiResponse(code = 401, message = "无权访问该接口，需授权"),
            @ApiResponse(code = 404, message = "请求路径不存在"),
            @ApiResponse(code = 500, message = "服务器重启中")
    })
    @GetMapping("/list/user")
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
    @RequestMapping(value = "/getOne/{userId}", method = RequestMethod.GET)
    public Long getUserById(
            @ApiParam(name = "userId", value = "用户ID", required = true, type = "long", defaultValue = "10", example = "10")
            @PathVariable("userId") Long userId) {
        log.info("## 请求时间：{}", new Date());
        return userId;
    }

    // @ApiParam跟@ApiImplicitParam功能差不多，推荐使用@ApiImplicitParam
    @ApiOperation(value = "删除用户接口", notes = "根据用户ID删除")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "long", paramType = "path", defaultValue = "10", example = "10")
    @DeleteMapping("/delete/{userId}")
    public Long removeUserById(@PathVariable("userId") Long userId) {
        log.info("## 请求时间：{}", new Date());
        return userId;
    }

    @ApiOperation(value = "新增用户接口", notes = "新增用户信息")
//    @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataTypeClass = User.class, paramType = "body")
    @PostMapping(path = "/save/user")
    public User saveSwaggerUser(@RequestBody User user, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println("参数名：" + headerName + " ==> 参数值：" + request.getHeader(headerName));
        }
        return user;
    }

    @ApiOperation(value = "修改用户接口", notes = "修改用户信息")
    @PostMapping(path = "/update/user")
    public User updateSwaggerUser(
            @ApiParam(name = "user", value = "用户实体", required = true)
            @RequestBody User user,
            @RequestHeader(value = "token", required = false) String token) {
        System.out.println("token=" + token);
        return user;
    }

    @ApiOperation(value = "权限", notes = "权限")
    @GetMapping(path = "/auth/user")
    public void auth(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println("参数名：" + headerName + " ==> 参数值：" + request.getHeader(headerName));
        }
    }

    @ApiOperation(value = "权限", notes = "权限")
    @GetMapping(path ="/auth123")
    public void auth2(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println("参数名：" + headerName + " ==> 参数值：" + request.getHeader(headerName));
        }
    }


}
