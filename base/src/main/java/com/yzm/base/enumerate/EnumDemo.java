package com.yzm.base.enumerate;

/**
 * Java 枚举是一个特殊的类，一般表示一组常量，比如一年的 4 个季节，一个年的 12 个月份，一个星期的 7 天，方向有东南西北等。
 * Java 枚举类使用 enum 关键字来定义，各个常量使用逗号 , 来分割。
 */
public class EnumDemo {
    public static void main(String[] args) {
//        test01();
//        System.out.println(test02(Week.SATURDAY));
//        test03();
//        test04();
        test05();
    }

    private static void test05() {
        Behaviour behaviour = Color3.BLUE;
        behaviour.printInfo();
    }

    private static void test04() {
        for (Color2 c : Color2.values()) {
            System.out.println(c.getColor());
        }
    }

    private static void test03() {
        System.out.println(Color.getName(2));
    }

    /**
     * switch 支持枚举
     */
    private static String test02(Week week) {
        switch (week) {
            case MONDAY:
                return "周一";
            case TUESDAY:
                return "周二";
            case WEDNESDAY:
                return "周三";
            case THURSDAY:
                return "周四";
            case FRIDAY:
                return "周五";
            case SATURDAY:
                return "周六";
            default:
                return "周日";
        }
    }

    private static void test01() {
        for (Week value : Week.values()) {
            System.out.println("枚举值：" + value);
            System.out.println("索引位置：" + value.ordinal());
            System.out.println("名称：" + value.name());
        }

        //使用 valueOf() 返回枚举常量，不存在的会报错 IllegalArgumentException
        System.out.println(Week.valueOf("THURSDAY"));
    }
}

/**
 * 星期枚举 作为常量使用并提供更多的方法
 * 以逗号隔开的，最后以分号结尾的，这部分叫做，这个枚举的实例
 * <p>
 * 默认方法
 * values()：返回枚举类中所有的枚举常量。
 * ordinal()：返回枚举常量的位置索引。
 * name()：返回枚举常量的名称字符串形式
 * valueOf()：返回指定字符串对应的枚举常量。
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
 */
enum Color {
    RED(1, "红色"), GREEN(2, "绿色"), BLUE(3, "蓝色");

    private final int index;
    private final String cname;

    // 构造函数
    private Color(int index, String cname) {
        this.index = index;
        this.cname = cname;
    }

    // 普通方法
    public static String getName(int index) {
        for (Color c : Color.values()) {
            if (c.getIndex() == index) {
                return c.getCname();
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public String getCname() {
        return cname;
    }
}

/**
 * 枚举既可以包含具体方法，也可以包含抽象方法。 如果枚举类具有抽象方法，则枚举类的每个实例都必须实现它。
 */
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

/**
 * 枚举类实现接口
 */
enum Color3 implements Behaviour {
    RED(1, "红色"), GREEN(2, "绿色"), BLUE(3, "蓝色");

    private final int index;
    private final String cname;

    private Color3(int index, String cname) {
        this.index = index;
        this.cname = cname;
    }

    public int getIndex() {
        return index;
    }

    public String getCname() {
        return cname;
    }

    @Override
    public void printInfo() {
        System.out.println("索引：" + getIndex() + "  颜色：" + getCname());
    }
}

interface Behaviour {
    void printInfo();
}

