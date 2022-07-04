package com.yzm.utils.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cat implements Serializable {
    private static final long serialVersionUID = -6096691686470547752L;
    @Excel(name = "猫名", orderNum = "1")
    private String name;
    @Excel(name = "猫龄", orderNum = "2")
    private Integer age;
}