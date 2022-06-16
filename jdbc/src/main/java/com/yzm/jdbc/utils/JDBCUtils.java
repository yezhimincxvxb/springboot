package com.yzm.jdbc.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Slf4j
public enum JDBCUtils {
    INSTANCE;

    private static final String url = "jdbc:mysql://127.0.0.1:3306/mydb?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai";
    private static final String username = "root";
    private static final String password = "root";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            log.info("连接数据库失败");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close(Connection conn, Statement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
