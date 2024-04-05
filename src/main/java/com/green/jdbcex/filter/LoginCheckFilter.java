package com.green.jdbcex.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//필터 적용시 @WebFilter추가, 특정 경로를 지정해서 해당 경로의 요청에 대해서 doFilter()를 실행, todo라는 경로를 통해서는 중요한 자료가 있어서 로그인 여부를 체크하도록 할 수 있다.
@WebFilter(urlPatterns = {"/todo/*"})    // /todo/...로 시작하는 모든 경로에 대해서 필터링을 시도
@Log4j2
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        log.info("Login check filter....");

        //HttpServlet,Response를 사용하기에 ServletRequest의 하위인 HttpServletRequest,Response의 객체에서 세션을 얻어와야 함
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        HttpSession httpSession = httpServletRequest.getSession();
        //세션에서 얻어온 로그인 정보가 없을 경우에는 로그인 화면으로 가게 해야 함.
        if (httpSession.getAttribute("loginInfo")==null){
            httpServletResponse.sendRedirect("/jdbcex/login");
            return;
        }

        chain.doFilter(servletRequest,servletResponse);
    }
}
