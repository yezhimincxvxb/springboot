package com.yzm.utils.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.Data;

import java.util.Date;

@Data
public class Account {

    @Excel(name = "id", orderNum = "1")
    private Integer id;

    @Excel(name = "姓名", orderNum = "2")
    private String name;

    @Excel(name = "是否实名", replace = {"已实名_true", "未实名_false", "---_null"}, orderNum = "3")
    private Boolean state;

    @Excel(name = "时间", format = "yyyy-MM-dd HH:mm:ss", orderNum = "4")
    private Date time;

//    @ExcelEntity
//    private User user;
//
//    @ExcelCollection(name = "用户集合", type = User.class)
//    private List<User> users;
}