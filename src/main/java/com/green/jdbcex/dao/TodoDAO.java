package com.green.jdbcex.dao;

import com.green.jdbcex.domain.TodoVO;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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


    //전체 글 목록 조회

    public List<TodoVO> selectAll() throws Exception{

        List<TodoVO> todoVOList=new ArrayList<>();

        String sql = "select * from tbl_todo";
        @Cleanup  //  try catch 리소스문을 대체(가독성 향상), @Cleanup이 추가된 변수는 close()가 호출되는 것 보장
        Connection conn = com.green.jdbcex.dao.ConnectionUtil.INSTANCE.getConnection();
        @Cleanup
        PreparedStatement pstmt = conn.prepareStatement(sql);
        @Cleanup
        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
          TodoVO todoVO= TodoVO.builder().tno(rs.getLong("tno"))
                            .title(rs.getString("title"))
                    .dueDate(rs.getDate("dueDate").toLocalDate())
                    .finished(rs.getBoolean("finished")).build();
            todoVOList.add(todoVO);
        }
        return todoVOList;
    }

    public TodoVO selectOne(Long tno) throws Exception{
        String sql = "select * from tbl_todo where tno=?";
        @Cleanup  //  try catch 리소스문을 대체(가독성 향상), @Cleanup이 추가된 변수는 close()가 호출되는 것 보장
        Connection conn = com.green.jdbcex.dao.ConnectionUtil.INSTANCE.getConnection();
        @Cleanup
        PreparedStatement pstmt = conn.prepareStatement(sql);


        //1번글 설정
        pstmt.setLong(1, tno);

        @Cleanup
        ResultSet rs = pstmt.executeQuery();

        rs.next();
        TodoVO todoVO= TodoVO.builder().tno(rs.getLong("tno"))
                .title(rs.getString("title"))
                .dueDate(rs.getDate("dueDate").toLocalDate())
                .finished(rs.getBoolean("finished")).build();
//$$
        return todoVO;
    }

    //글 수정
    public void modArticle(TodoVO todoVO) throws Exception{
        String sql = "update tbl_todo set title=?, dueDATE=? where tno=?";
        @Cleanup Connection connection=ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt=connection.prepareStatement(sql);
        pstmt.setString(1,todoVO.getTitle());
        pstmt.setDate(2, Date.valueOf(todoVO.getDueDate()));
        pstmt.setLong(3,todoVO.getTno());
        pstmt.executeUpdate();
    }

    // 글 목록 추가
    public void insert(TodoVO todoVO) throws Exception {
        String sql = "insert into tbl_todo (title, dueDate, finished) values (?, ?, ?)";

        @Cleanup Connection connection = com.green.jdbcex.dao.ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, todoVO.getTitle());
        preparedStatement.setDate(2, Date.valueOf(todoVO.getDueDate()));
        preparedStatement.setBoolean(3, todoVO.isFinished());

        preparedStatement.executeUpdate();

    }

    //글 삭제

    public void delete(Long tno) throws Exception{
        String sql = "delete from tbl_todo where tno=?";
        @Cleanup  //  try catch 리소스문을 대체(가독성 향상), @Cleanup이 추가된 변수는 close()가 호출되는 것 보장
        Connection conn = com.green.jdbcex.dao.ConnectionUtil.INSTANCE.getConnection();
        @Cleanup
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //삭제할 글 설정
        pstmt.setLong(1, tno);

        pstmt.executeUpdate();
    }

}