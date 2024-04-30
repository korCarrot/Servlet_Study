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

//       Arrays.stream(cookies): 주어진 배열을 스트림으로 변환합니다. 이를 통해 배열 요소에 대해 함수형 프로그래밍 방식으로 연산을 수행할 수 있습니다.
//      .filter((cookie) -> {return cookie.getName().equals(name);}): 스트림 요소를 필터링하여 주어진 조건을 만족하는 요소만을 남깁니다. 여기서는 각 쿠키의 이름이 주어진 이름과 일치하는지 확인합니다.
//        .findFirst(): 필터링된 요소 중에서 첫 번째 요소를 반환합니다. 이때, 해당 조건을 만족하는 요소가 없다면 Optional.empty()를 반환합니다.
        Optional<Cookie> result  =Arrays.stream(cookies).filter((cookie)->{return cookie.getName().equals(name);}).findFirst();

        return result.isPresent() ?  result.get() : null;

//Optional<Cookie>을 사용하는 이유는 다음과 같습니다:
//Null 안전성: 쿠키를 찾을 때 쿠키 배열이 비어 있거나 찾는 쿠키가 존재하지 않을 수 있습니다. 이런 경우에 직접적으로 null을 반환하면 NullPointerException과 같은 예외가 발생할 수 있습니다. 그러나 Optional을 사용하면 이러한 예외를 방지하고 코드를 더 안전하게 만들 수 있습니다.
//명시적인 존재 여부 표현: Optional은 값이 존재하는지 여부를 명시적으로 표현합니다. 반환된 Optional 객체가 비어있으면 쿠키를 찾지 못한 것이며, 값이 존재하면 해당 쿠키를 찾은 것입니다. 이를 통해 클라이언트 코드에서 쿠키가 존재하는지 또는 존재하지 않는지를 명확하게 파악할 수 있습니다.
//메서드 체이닝: Optional은 메서드 체이닝을 통해 연속된 작업을 수행할 수 있습니다. 예를 들어, 쿠키를 찾은 후에 추가적인 작업을 수행할 수 있습니다. 이를 통해 코드를 더 간결하고 가독성 있게 작성할 수 있습니다.
    }

}