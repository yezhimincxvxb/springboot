package com.yzm.base.enumerate;

/**
 * Java 枚举是一个特殊的类，一般表示一组常量，比如一年的 4 个季节，一个年的 12 个月份，一个星期的 7 天，方向有东南西北等。
 * Java 枚举类使用 enum 关键字来定义，各个常量使用逗号 , 来分割。
 */
public class EnumDemo {
    public static void main(String[] args) {
//        test01();
//        test02();
        test03();
    }

    private static void test03() {
        for (Color2 c : Color2.values()) {
            System.out.println(c.getColor());
        }
    }

    private static void test02() {
        Color c1 = Color.RED;
        System.out.println(c1);
        c1.colorInfo();
    }

    private static void test01() {
        for (Week value : Week.values()) {
            System.out.println("枚举值：" + value);
            System.out.println("索引位置：" + value.ordinal());
        }

        //使用 valueOf() 返回枚举常量，不存在的会报错 IllegalArgumentException
        System.out.println(Week.valueOf("THURSDAY"));
    }
}

/**
 * 星期枚举
 * <p>
 * 默认方法
 * values()：返回枚举类中所有的值。
 * ordinal()：方法可以找到每个枚举常量的索引，就像数组索引一样。
 * valueOf()：方法返回指定字符串值的枚举常量。
 */
enum Week {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

/**
 * 枚举跟普通类一样可以用自己的变量、方法和构造函数，构造函数只能使用 private 访问修饰符，所以外部无法调用。
 * 枚举既可以包含具体方法，也可以包含抽象方法。 如果枚举类具有抽象方法，则枚举类的每个实例都必须实现它。
 */
enum Color {
    RED, GREEN, BLUE;

    // 构造函数
    private Color() {
        System.out.println("Constructor called for : " + this);
    }

    public void colorInfo() {
        System.out.println("Universal Color");
    }
}

enum Color2 {
    RED {
        public String getColor() {//枚举对象实现抽象方法
            return "红色";
        }
    },
    GREEN {
        public String getColor() {//枚举对象实现抽象方法
            return "绿色";
        }
    },
    BLUE {
        public String getColor() {//枚举对象实现抽象方法
            return "蓝色";
        }
    };

    public abstract String getColor();//定义抽象方法
}