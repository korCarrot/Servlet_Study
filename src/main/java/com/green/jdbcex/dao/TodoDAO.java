package com.green.jdbcex.dao;

import com.green.jdbcex.domain.TodoVO;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TodoDAO {



    public String getTime() throws Exception {

        String now = null;

//1) 이전 방식
//        try {
//            ConnectionUtil.INSTANCE.getConnection();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

// 2) 자원을 가지고 하는 예외처리(닫아주지 않아도 됨), try with Resource 처리

//        try (Connection conn = ConnectionUtil.INSTANCE.getConnection(); PreparedStatement pstmt = conn.prepareStatement("select now()");) {
//            ResultSet rs = pstmt.executeQuery();
//            rs.next();
//            now = rs.getString(1);
//        } catch (Exception e) {
//            System.out.println(" DB 커넥션 관련 예외 발생");
//        }

        @Cleanup  //  try catch 리소스문을 대체(가독성 향상), @Cleanup이 추가된 변수는 close()가 호출되는 것 보장
        Connection conn = com.green.jdbcex.dao.ConnectionUtil.INSTANCE.getConnection();
        @Cleanup
        PreparedStatement pstmt = conn.prepareStatement("select now()");
        @Cleanup
        ResultSet rs = pstmt.executeQuery();

        rs.next();
        now = rs.getString(1);



        return now;
    }

//   public void insert(TodoVO todoVO) throws Exception {
//       String sql="insert into tbl_todo(title, dueDate, finished) VALUES (?, ?, ?)";
//       @Cleanup Connection conn=ConnectionUtil.INSTANCE.getConnection();
//       @Cleanup PreparedStatement stmt=conn.prepareStatement(sql);
//
//        stmt.setString(1, todoVO.getTitle() );
//        stmt.setDate(2, Date.valueOf(todoVO.getDueDate()));
//        stmt.setBoolean(3, todoVO.isFinished() );
//
//        stmt.executeUpdate();
//
//    }

    //교재 코드
    public void insert(TodoVO todoVO) throws Exception {
        String sql = "insert into tbl_todo (title, dueDate, finished) values (?, ?, ?)";

        @Cleanup Connection connection = com.green.jdbcex.dao.ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, todoVO.getTitle());
        preparedStatement.setDate(2, Date.valueOf(todoVO.getDueDate()));
        preparedStatement.setBoolean(3, todoVO.isFinished());

        preparedStatement.executeUpdate();

    }

}