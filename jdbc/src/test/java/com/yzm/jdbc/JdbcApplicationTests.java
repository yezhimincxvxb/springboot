package com.yzm.jdbc;

import com.yzm.jdbc.utils.JDBCUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;

@SpringBootTest
class JdbcApplicationTests {

    @Test
    void select() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.INSTANCE.getConnection();
            String sql = "select * from `user`";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " +
                        rs.getString(2) + " " +
                        rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.INSTANCE.close(conn, ps, rs);
        }
    }

    @Test
    void insert() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.INSTANCE.getConnection();
            String sql = "insert into `user`(username,password) values(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "ABC");
            ps.setString(2, "abc");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.INSTANCE.close(conn, ps, null);
        }
    }

    @Test
    void update() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.INSTANCE.getConnection();
            String sql = "update `user` set password = ? where username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "566");
            ps.setString(2, "ABC");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.INSTANCE.close(conn, ps, null);
        }
    }

    @Test
    void delete() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.INSTANCE.getConnection();
            String sql = "delete from `user` where username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "ABC");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.INSTANCE.close(conn, ps, null);
        }
    }

    @Test
    void call() {
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.INSTANCE.getConnection();
            String sql = "call select_all()";
            cs = conn.prepareCall(sql);
            rs = cs.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.INSTANCE.close(conn, cs, rs);
        }
    }

}
