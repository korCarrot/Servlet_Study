package com.green.jdbcex.controller;

import com.green.jdbcex.dto.TodoDTO;
import com.green.jdbcex.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "todoModifyController", value = "/todo/modify")
@Log4j2
public class TodoModifyController extends HttpServlet {
    private TodoService todoService = TodoService.INSTANCE;

    private final DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("utf-8");
        Long tno = Long.parseLong(req.getParameter("tno"));
        log.info("여기는 todo read 그리고 tno값 : " + tno);

        try {
            TodoDTO todoDTO = todoService.get(tno);
            req.setAttribute("dto",todoDTO);
            req.getRequestDispatcher("/WEB-INF/todo/modify.jsp").forward(req,resp);
        } catch (Exception e) {
            log.info("글 수정 중 에러 발생. todo/modify");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("여기는 modify doPost");
//        req.setCharacterEncoding("utf-8");
        String finishedStr = req.getParameter("finished");  //개발자도구에서는 true로 나오지만 자바에서는 on으로 나옴.

        TodoDTO todoDTO = TodoDTO.builder()
                .tno(Long.parseLong(req.getParameter("tno")))
                .title(req.getParameter("title"))
                .dueDate(LocalDate.parse(req.getParameter("dueDate"),DATEFORMATTER ))
                .finished(finishedStr !=null && finishedStr.equals("on")).build();

        try {
            todoService.update(todoDTO);
        } catch (Exception e) {
            log.info("update에러 / modify");
        }

        resp.sendRedirect("/jdbcex/todo/list");
    }
}
