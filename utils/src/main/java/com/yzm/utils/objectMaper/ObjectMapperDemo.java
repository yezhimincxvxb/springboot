package com.yzm.utils.objectMaper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class ObjectMapperDemo {

    @Resource(name = "myObjectMapper")
    private ObjectMapper objectMapper;

    private static MapperUser user;
    private static MapperUser user2;

    static {
        user = new MapperUser()
                .setId(100L)
                .setName("abc")
                .setAge(20)
                .setMoney(new BigDecimal("998"))
                .setCreateTime(new Date())
                .setUpdateTime(LocalDateTime.now());
        user2 = new MapperUser()
                .setId(200L)
                .setName("ABC")
                .setAge(25)
                .setMoney(new BigDecimal("1998"))
                .setCreateTime(new Date())
                .setUpdateTime(LocalDateTime.now());
    }

    @PostConstruct
    public void test() {
//        demo01();
        demo02();
        demo03();
//        demo04();
    }

    /**
     * 对象 <==> JSON字符串
     */
    private void demo01() {
        try {
            String s = objectMapper.writeValueAsString(user);
            System.out.println("s = " + s);
            user.setName("abc_copy");

            MapperUser mapperUser = objectMapper.readValue(s, MapperUser.class);
            System.out.println("对象 = " + mapperUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 集合对象 <==> JSON字符串
     */
    private void demo02() {
        try {
            List<MapperUser> list = Arrays.asList(user, user2);

            String s = objectMapper.writeValueAsString(list);
            System.out.println("s = " + s);

            // constructArrayType、constructParametricType
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, MapperUser.class);
            List<MapperUser> mapperUsers = objectMapper.readValue(s, javaType);
            System.out.println("List = " + mapperUsers);

            List<MapperUser> mapperUsers2 = objectMapper.readValue(s, new TypeReference<List<MapperUser>>() {
            });
            System.out.println("List2 = " + mapperUsers2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Map对象 <==> JSON字符串
     */
    private void demo03() {
        try {
            Map<String, MapperUser> map = new HashMap<>();
            map.put("user", user);
            map.put("user2", user2);

            String s = objectMapper.writeValueAsString(map);
            System.out.println("s = " + s);

            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, String.class, MapperUser.class);
            Map<String, MapperUser> mapUser = objectMapper.readValue(s, javaType);
            System.out.println("Map = " + mapUser);

            Map<String, MapperUser> mapUser2 = objectMapper.readValue(s, new TypeReference<Map<String, MapperUser>>() {
            });
            System.out.println("Map2 = " + mapUser2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入读取 <> File文件
     * File不存在，自动创建
     */
    private void demo04() {
        try {
            File file = new File("C:\\user.txt");
            objectMapper.writeValue(file, user);
            MapperUser readValue = objectMapper.readValue(file, MapperUser.class);
            System.out.println(readValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
