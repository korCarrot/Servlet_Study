package com.green.service;

import com.green.jdbcex.dto.TodoDTO;
import com.green.jdbcex.service.TodoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@Log4j2
public class TodoServiceTests {
    private TodoService todoService;

    @BeforeEach
    public void ready(){
        this.todoService =TodoService.INSTANCE;
    }
    @Test
   public  void testRegister() throws Exception {
        TodoDTO todoDTO=TodoDTO.builder().title("JDBC DTO 테스트2")
                .dueDate(LocalDate.now())
                .build();
        //System.out.println("todoDTO: " + todoDTO);
        log.info("todoDTO#: " + todoDTO);
       todoService.register(todoDTO);
    }


}
