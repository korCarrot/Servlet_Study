package com.green.jdbcex.controller;


import com.green.jdbcex.dto.MemberDTO;
import com.green.jdbcex.service.MemberService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/login")
@Log4j2
public class LoginController extends HttpServlet {


    //로그인 화면
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("login get.............");
        HttpSession session = req.getSession();
        log.info("새로운 세션여부 : " + session.isNew());
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req,resp);
    }

    //로그인 처리
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("login post.............");
        String mid = req.getParameter("mid");
        String mpw = req.getParameter("mpw");

        log.info("현재 로그인 한 id" + mid );
        String auto = req.getParameter("auto");
        log.info("로그인 상태 유지 여부 :" + auto);

        boolean rememberMe= auto != null && auto.equals("on");

        //로그인 정보(mid,mpw)를 한꺼번에 정보라는 키값으로 세션에 저장애햐함

        try {
            MemberDTO memberDTO = MemberService.INSTANCE.login(mid,mpw);
            //String str = mid+mpw;

            if(rememberMe){
                String uuid= UUID.randomUUID().toString();
                log.info("uuid :" + uuid);

                MemberService.INSTANCE.updateUuid(mid, uuid);
                memberDTO.setUuid(uuid);


                // 생성된 uuid를 쿠키로 만들어야 함,쿠키 이름은 remember-me 유효기간은 1주일
                Cookie rememberCookie=new Cookie("remember-me", uuid);
                rememberCookie.setMaxAge(60*60*24*7); // 쿠키 유효기간 1주일
                rememberCookie.setPath("/");

                resp.addCookie(rememberCookie);
            }


            HttpSession session = req.getSession();
            log.info("새로운 세션여부 : " + session.isNew());
            session.setAttribute("loginInfo", memberDTO);
            resp.sendRedirect("/jdbcex/todo/list");
        } catch (Exception e) {
            resp.sendRedirect("/jdbcex/login?result=error");
        }

    }
}