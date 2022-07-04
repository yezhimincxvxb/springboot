package com.yzm.utils.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentB implements Serializable {

    private static final long serialVersionUID = 4266251627497187654L;
    @Excel(name = "学生ID", orderNum = "1")
    private String userId;
    @Excel(name = "姓名", orderNum = "2")
    private String name;

    @ExcelEntity(name = "宠物猫", show = true)
    private Cat cat;

}

