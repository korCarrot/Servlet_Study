package com.green.jdbcex.controller;

import com.green.jdbcex.dto.TodoDTO;
import com.green.jdbcex.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "todoListController", value = "/todo/list")
@Log4j2
public class TodoListController extends HttpServlet {

    private TodoService todoService  = TodoService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("todo list..................");

        ServletContext servletContext=req.getServletContext();

        String appName= (String) servletContext.getAttribute("appName");

        try {
            List<TodoDTO> todoDTOList=  todoService.listAll();

            String view="/WEB-INF/todo/list.jsp";
            req.setAttribute("todoDTOList", todoDTOList);
            RequestDispatcher requestDispatcher=req.getRequestDispatcher(view);

            requestDispatcher.forward(req, resp);

        } catch (Exception e) {
            log.info("글 전체 조회시 예외 발생");
            // throw new RuntimeException(e);
        }
    }
}