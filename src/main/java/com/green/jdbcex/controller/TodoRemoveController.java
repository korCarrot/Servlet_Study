package com.green.jdbcex.controller;

import com.green.jdbcex.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "todoRemoveController", value = "/todo/remove")
@Log4j2
public class TodoRemoveController extends HttpServlet {
    private TodoService todoService = TodoService.INSTANCE;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("여기는 todo/remove");
        String tno1 = req.getParameter("tno");
        long tno=Long.parseLong(tno1);
        try {
            todoService.remove(tno);
        } catch (Exception e) {
            log.info("삭제 / todo/remove입니다.");
        }
        resp.sendRedirect("/jdbcex/todo/list");
    }
}
