package com.yzm.utils.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yzm.utils.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student extends BaseEntity {

    private static final long serialVersionUID = -5195836352072234956L;
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

