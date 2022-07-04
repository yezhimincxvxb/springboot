package com.yzm.utils.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dog implements Serializable {
    private static final long serialVersionUID = 1521569235809741877L;
    @Excel(name = "狗名")
    private String name;
    @Excel(name = "狗龄")
    private Integer age;
}

