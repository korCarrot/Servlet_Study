package com.green.service;

import com.green.jdbcex.dto.TodoDTO;
import org.junit.jupiter.api.BeforeEach;

public class TodoService {

    private  TodoService todoService;

    @BeforeEach
    public void ready(){
        todoService = TodoService.INSTANCE;
    }

    void testRegister(){
        TodoDTO
    }

}
