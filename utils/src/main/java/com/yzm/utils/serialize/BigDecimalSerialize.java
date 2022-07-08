package com.yzm.utils.serialize;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 自定义序列化
 * BigDecimal类型的参数保留两位小数
 */
public class BigDecimalSerialize implements ObjectSerializer {

    @Override
    public void write(JSONSerializer jsonSerializer, Object value, Object filedName, Type type, int i) {
        String typeName = type.getTypeName();
        if (typeName.equals(BigDecimal.class.getTypeName())) {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String price = decimalFormat.format(value);
            jsonSerializer.write(price);
        } else {
            jsonSerializer.write(value);
        }
    }

    public static void main(String[] args) {
        double pi = 3.1415927;
        //取整数部分
        System.out.println(new DecimalFormat("0").format(pi));//3
        System.out.println(new DecimalFormat("#").format(pi));//3
        //保留两位小数
        System.out.println(new DecimalFormat("0.00").format(pi));//3.14
        System.out.println(new DecimalFormat("#.00").format(pi));//3.14
        //取两位整数和三位小数
        System.out.println(new DecimalFormat("00.000").format(pi));// 03.142
        System.out.println(new DecimalFormat("##.000").format(pi));// 3.142
        //以百分比方式计数，并取两位小数
        System.out.println(new DecimalFormat("0.00%").format(pi));//314.16%
        System.out.println(new DecimalFormat("#.##%").format(pi));//314.16%
        long c = 299792458;//光速
        //显示为科学计数法，并取五位小数
        System.out.println(new DecimalFormat("#.#####E0").format(c));//2.99792E8
        //显示为两位整数的科学计数法，并取四位小数
        System.out.println(new DecimalFormat("00.####E0").format(c));//29.9792E7
        //每三位以逗号进行分隔。
        System.out.println(new DecimalFormat(",###").format(c));//299,792,458
        //将格式嵌入文本
        System.out.println(new DecimalFormat("光速大小为每秒,###米。").format(c));

        // 总结:DecimalFormat 类主要靠 # 和 0 两种占位符号来指定数字长度。区别：0 表示如果位数不足则以 0 填充，#不会
    }
}
