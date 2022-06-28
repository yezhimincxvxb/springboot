package com.yzm.utils.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget("teacher")
public class UserA implements Serializable {
    private static final long serialVersionUID = 4807946972432902046L;

    // @ExcelTarget指定ID为：teacher，在导出Excel表格时，name的表头为：教师姓名
    @Excel(name = "学生姓名_student, 教师姓名_teacher")
    private String name;
    @Excel(name = "年龄", orderNum = "1_student, 2_teacher")
    private Integer age;
}

