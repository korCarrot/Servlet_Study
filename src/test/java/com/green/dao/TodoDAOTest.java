package com.green.dao;

import com.green.jdbcex.dao.TodoDAO;
import com.green.jdbcex.domain.TodoVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class TodoDAOTest {

    private TodoDAO todoDAO;

    @BeforeEach
    public void ready(){
        todoDAO = new TodoDAO();
    }

    @Test
    void testTime() throws Exception {
        System.out.println("현재 시각 : " + todoDAO.getTime());
    }


    // 글 추가 테스트
    @Test
    public void testInsert() throws Exception {
        TodoVO todoVO = TodoVO.builder()
                .title("Sample Title...3")
                .dueDate(LocalDate.of(2021,12,31))
                .build();

        todoDAO.insert(todoVO);
    }

    // 전체 글 조회 테스트
    @Test
    void testSelectAll()throws Exception{
        List<TodoVO>  todoVOList=  todoDAO.selectAll();
        //System.out.println(todoVOList);
        //스트림 처리 후 찍기

        todoVOList.stream().forEach((x)->{System.out.println(x);});

    }

    //1번글 조회 테스트
    @Test
    void testSelectOne() throws Exception{
        TodoVO todoVO=todoDAO.selectOne(1L);
        System.out.println(todoVO);
    }

    //수정 구현 후 테스트
    @Test
    void testUpdate()throws Exception{
        TodoVO todoVO = TodoVO.builder().tno(4L)
                .title("글 수정 Not yet(아직 안함)..")
                .dueDate(LocalDate.of(2024,4,10))
                .finished(false)
                .build();
      todoDAO.update(todoVO);

    }


    // 삭제 구현 후 테스트
    @Test
    void testDelete()throws Exception{

        todoDAO.delete(9L);

    }
}
