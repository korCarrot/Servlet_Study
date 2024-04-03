package com.green;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectTest {

    //필

    //생

    //메
    @Test  //메소드가 테스트 메소드임을 나타냅니다.
    void test1(){
        int v1=10;
        int v2=10;
        Assertions.assertEquals(v1,v2);
    }

    @Test
    void testConnections() throws Exception{
        Class.forName("org.mariadb.jdbc.Driver");
        Connection conn=DriverManager.getConnection("jdbc:mariadb://localhost:3307/webdb", "webuser", "webuser");

        Assertions.assertNotNull(conn);

        conn.close();
    }


    @Test
    void hikariCPTest()throws Exception {
        HikariConfig config  = new HikariConfig();
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://localhost:3307/webdb");
        config.setUsername("webuser");
        config.setPassword("webuser");

        HikariDataSource hikariDataSource=new HikariDataSource(config);
        Connection conn=hikariDataSource.getConnection();
        Assertions.assertNotNull(conn);
        System.out.println("커넥션풀 연결 객체 : "  + conn);
        conn.close();

    }
}