package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller // 스프링이 자동으로 스프링 빈으로 등록(@Component 내장), 스프링 MVC에서 애노테이션 기반 컨트롤러로 인식함
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form") //요청 정보 매핑, url 호출시 메서드가 호출 됨
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
