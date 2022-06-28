package com.yzm.utils.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentA implements Serializable {
    private static final long serialVersionUID = -7954900664172683361L;

    // needMerge：是否需要纵向合并单元格(用于含有list中,单个的单元格,合并list创建的多个row)
    @Excel(name = "学生ID", orderNum = "1", needMerge = true)
    private String userId;
    @Excel(name = "姓名", orderNum = "2", needMerge = true)
    private String name;

    @ExcelCollection(name = "宠物狗", orderNum = "3", type = Dog.class)
    private List<Dog> dogs;

}

