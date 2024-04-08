package com.green.jdbcex.filter;


import com.green.jdbcex.dto.MemberDTO;
import com.green.jdbcex.service.MemberService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;


//필터 적용시 @WebFilter추가, 특정 경로를 지정해서 해당 경로의 요청에 대해서 doFilter()를 실행, todo라는 경로를 통해서는 중요한 자료가 있어서 로그인 여부를 체크하도록 할 수 있다.
@WebFilter(urlPatterns = {"/todo/*"})  // /todo/...   로 시작하는 모든 경로에 대해서 필터링을 시도
@Log4j2
public class LoginCheckFilter implements Filter {

    //필터링이 필요한 로직을 구현
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Login check filter....");

        //ServletRequest의 하위인 HttpServletRequest의 객체에서 세션을 얻어와야함
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        HttpSession httpSession= httpServletRequest.getSession();

        log.info("세션에서 loginInfo에 대한 값 :"+ httpSession.getAttribute("loginInfo"));

        if(httpSession.getAttribute("loginInfo")==null){
            //이제는 로그인시에 기존에 remember-me 쿠키를 발행했는지 여부(remember-me를 체크함)를 체크해야 함
            //remember-me 쿠키를 찾아야 함
            Cookie cookie= findCookie(httpServletRequest.getCookies(), "remember-me");

            if(cookie!=null){
                // 쿠키가 존재하면
                log.info("cookie가 존재");
                String uuid=cookie.getValue();

                //데이터베이스에서 확인
                try {
                    MemberDTO memberDTO = MemberService.INSTANCE.getByUUID(uuid);
                    log.info("쿠키값으로 조회한 사용자 정보 :" + memberDTO);

//                    if(memberDTO==null){
//                        throw new Exception("쿠키 값이 유효하지 않음");
//                    }
                    //회원 정보를 세션에 추가
                    httpSession.setAttribute("loginInfo", memberDTO);
                } catch (Exception e) {
                    //throw new RuntimeException(e);
                    httpServletResponse.sendRedirect("/jdbcex/login");
                }


            }




        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);


    }

    //쿠키 찾는 메서드
    public Cookie findCookie(Cookie cookies[], String name){
        if(cookies ==null || cookies.length==0){
            return null;
        }

        Optional<Cookie> result  =Arrays.stream(cookies).filter((cookie)->{return cookie.getName().equals(name);}).findFirst();

        return result.isPresent() ?  result.get() : null;




    }

}