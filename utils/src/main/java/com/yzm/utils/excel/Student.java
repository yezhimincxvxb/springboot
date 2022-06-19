package com.yzm.utils.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    private static final long serialVersionUID = -4089175524171189962L;

    @Excel(name = "学生ID", orderNum = "1")
    private String userId;
    // orderNum：排序，值越小越靠前(左) 学生姓名值：2，联系电话值：3，所以学生姓名靠前
    @Excel(name = "联系电话", orderNum = "3")
    private String phone;
    @Excel(name = "学生姓名", orderNum = "2")
    private String userName;
    // replace：替换，导出时，0替换成女，1男
    // suffix：后缀，男生、女生
    // mergeVertical：纵向合并内容相同的单元格
    @Excel(name = "性别", orderNum = "4", replace = {"女_0", "男_1"}, suffix = "生", mergeVertical = true)
    private Integer sex;
    // numFormat：使用DecimalFormat对象进行数字格式化
    @Excel(name = "余额", orderNum = "5", numFormat = "0.00")
    private Double money;
    // format：时间格式化
    @Excel(name = "创建时间", orderNum = "6", format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    // groupName：分组
    @Excel(name = "公司名称", orderNum = "7", groupName = "公司信息")
    private String companyName;
    @Excel(name = "公司地址", orderNum = "8", groupName = "公司信息")
    private String companyAdd;
    @Excel(name = "公司人数", orderNum = "9", groupName = "公司信息")
    private Integer companyNum;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class StudentA implements Serializable {
    private static final long serialVersionUID = -7954900664172683361L;

    // needMerge：是否需要纵向合并单元格(用于含有list中,单个的单元格,合并list创建的多个row)
    @Excel(name = "学生ID", orderNum = "1", needMerge = true)
    private String userId;
    @Excel(name = "姓名", orderNum = "2", needMerge = true)
    private String name;

    @ExcelCollection(name = "宠物狗", orderNum = "3", type = Dog.class)
    private List<Dog> dogs;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class StudentB implements Serializable {

    private static final long serialVersionUID = 4266251627497187654L;
    @Excel(name = "学生ID", orderNum = "1")
    private String userId;
    @Excel(name = "姓名", orderNum = "2")
    private String name;

    @ExcelEntity(name = "宠物猫", show = true)
    private Cat cat;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget("teacher")
class UserA implements Serializable {
    private static final long serialVersionUID = 4807946972432902046L;

    // @ExcelTarget指定ID为：teacher，在导出Excel表格时，name的表头为：教师姓名
    @Excel(name = "学生姓名_student, 教师姓名_teacher")
    private String name;
    @Excel(name = "年龄", orderNum = "1_student, 2_teacher")
    private Integer age;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Dog implements Serializable {
    private static final long serialVersionUID = 1521569235809741877L;
    @Excel(name = "狗名")
    private String name;
    @Excel(name = "狗龄")
    private Integer age;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Cat implements Serializable {
    private static final long serialVersionUID = -6096691686470547752L;
    @Excel(name = "猫名", orderNum = "1")
    private String name;
    @Excel(name = "猫龄", orderNum = "2")
    private Integer age;
}