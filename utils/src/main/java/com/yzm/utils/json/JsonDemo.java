package com.yzm.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class JsonDemo {

    public static void main(String[] args) {
//        demo01();
//        demo02();
        StringBuilder sb = new StringBuilder();
        sb
                .append("[")
                .append("{")
                .append("\"id\":1,")
                .append("\"username\":\"大郎\",")
                .append("\"enable\":true,")
                .append("\"localDateTime\":\"2022-07-05T13:58:00.200\"")
                .append("},")
                .append("{")
                .append("\"id\":2,")
                .append("\"username\":\"小二郎\",")
                .append("\"enable\":true,")
                .append("\"localDateTime\":\"2022-07-05T13:58:00.201\"")
                .append("}")
                .append("]")
        ;

        List<Student> list = JSON.parseArray(sb.toString(), Student.class);
        list.forEach(System.out::println);
//        Student student = Student.builder()
//                .id(1)
//                .username("大郎")
//                .enable(true)
//                .localDateTime(LocalDateTime.now())
//                .build();
//        Student student2 = Student.builder()
//                .id(2)
//                .username("小二郎")
//                .enable(true)
//                .localDateTime(LocalDateTime.now())
//                .build();
//        List<Student> list = new ArrayList<>();
//        list.add(student);
//        list.add(student2);
//
//        String jsonString = JSON.toJSONString(list, true);
//        System.out.println("jsonString = " + jsonString);
    }

    private static void demo01() {
//        method01();
        method01_2();
    }

    private static void method01() {
        JSONObject object = (JSONObject) JSON.parse("{\"id\":110,\"username\":\"警察\"}");
        JSONObject object2 = JSON.parseObject("{\"id\":110,\"username\":\"警察\"}");
        Student object3 = JSON.parseObject("{\"id\":110,\"username\":\"警察\"}", Student.class);
        System.out.println("object = " + object);
        System.out.println("object2 = " + object2);
        System.out.println("object3 = " + object3);
        System.out.println("==================================");

        JSONArray array = (JSONArray) JSON.parse("['游泳','下棋','马拉松']");
        JSONArray array2 = JSON.parseArray("['游泳','下棋','马拉松']");
        List<String> array3 = JSON.parseArray("['游泳','下棋','马拉松']", String.class);
        System.out.println("array = " + array);
        System.out.println("array2 = " + array2);
        System.out.println("array3 = " + array3);
    }

    private static void method01_2() {
        Student student = new Student();
        student.setId(2);
        student.setUsername("小二郎");
        student.setEnable(true);
        student.setLocalDateTime(LocalDateTime.now());

        String jsonString = JSON.toJSONString(student);
        System.out.println("jsonString = " + jsonString);
        String jsonFormat = JSON.toJSONString(student, true);
        System.out.println("jsonFormat = " + jsonFormat);
        JSONObject object = (JSONObject) JSON.toJSON(student);
        System.out.println("object = " + object);
    }

    private static void demo02() {
        method02();
        method02_2();
    }

    private static void method02() {
        JSONObject object = new JSONObject();
        object.put("id", 2);
        object.put("username", "无所谓");
        object.put("enable", true);
        List<Integer> integers = Arrays.asList(1, 2, 3);
        object.put("hobby", integers);
        object.put("null", null);
        System.out.println(object);

        String s = JSON.toJSONString(object, true);
        System.out.println("s = " + s);
    }

    private static void method02_2() {
        JSONObject object = JSONObject.parseObject("{\"boolean\":true,\"string\":\"string\",\"list\":[1,2,3],\"int\":2}");
        System.out.println(object.getString("string"));
        System.out.println(object.getIntValue("int"));
        System.out.println(object.getBooleanValue("boolean"));
        List<Integer> integers = JSON.parseArray(object.getJSONArray("list").toJSONString(), Integer.class);
        integers.forEach(System.out::println);
        System.out.println(object.getString("null"));
    }

    public static void demo03() {
        String json = "{'id':'1','name':'admin','password':'123456','dog': {'name':'小狗','age':'2'},'time':'2020-06-01 12:30:20'}";
        JSONObject jsonObject = JSON.parseObject(json);
        JSONObject dog = jsonObject.getJSONObject("dog");
        System.out.println("name = " + dog.getString("name"));
        System.out.println("age = " + dog.getString("age"));
        System.out.println("time = " + jsonObject.getDate("time"));
    }

    public static void demo04() {
        String json = "{'person':[{'id':'1','name':'admin','password':'123456','time':'2020-06-01 12:30:20'},{'id':'2','name':'user','password':'111111','time':'2020-06-01 12:30:20'}]}";
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray persons = jsonObject.getJSONArray("person");
        persons.forEach(o -> {
            JSONObject person = (JSONObject) o;
            System.out.println("id = " + person.getString("id"));
            System.out.println("name = " + person.getString("name"));
            System.out.println("password = " + person.getString("password"));
            System.out.println("time = " + person.getString("time"));
        });
    }

    public static void demo05() {
        String json = "{'person':[{'id':'3','name':'admin','password':'123456','time':'2020-06-01 12:30:20'},{'id':'4','name':'user','password':'111111','time':'2020-06-01 12:30:20'}]}";
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray persons = jsonObject.getJSONArray("person");
        persons.forEach(o -> {
            JSONObject person = (JSONObject) o;
            Set<String> keys = person.keySet();
            for (String key : keys) {
                System.out.println(key + " = " + person.getString(key));
            }
        });

    }


}
