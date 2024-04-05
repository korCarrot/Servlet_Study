package com.green.jdbcex.controller;

import com.green.jdbcex.dto.MemberDTO;
import com.green.jdbcex.service.MemberService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
@Log4j2
public class LoginController extends HttpServlet {

    //로그인 화면
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("LoginController login get.............");
        HttpSession session = req.getSession();
        log.info("새로운 세션여부 : " + session.isNew());
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req,resp);
    }

    //로그인 처리
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        log.info("LoginController login post.............");
        String mid = req.getParameter("mid");
        String mpw = req.getParameter("mpw");

        //로그인 정보(mid, mpw)를 한꺼번에 정보라는 키값으로 세션에 저장해야 함

        MemberDTO memberDTO = null;
        try {
            memberDTO = MemberService.INSTANCE.login(mid, mpw);

//        String str = mid+mpw;
            HttpSession session = req.getSession();

            log.info("새로운 세션여부 : " + session.isNew());

            session.setAttribute("loginInfo", memberDTO);
            resp.sendRedirect("/jdbcex/todo/list");
        } catch (Exception e) {
            resp.sendRedirect("/jdbcex/login?result=error");
        }



    }
}
