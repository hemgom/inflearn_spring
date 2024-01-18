package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Slf4j
@RestController // 반환 값으로 뷰를 찾는게 아니라 HTTP 메시지 바디에 바로 입력
public class LogTestController {
    
    private final Logger log = LoggerFactory.getLogger(getClass()); // @Slf4j 사용 시 lombok이 해당 코드를 자동으로 입력해줌

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);   // System.out.println 의 경우 모든 로그가 전부 남기 때문에 사용에 주의 해야함

        // 아래의 방식처럼 사용하면 로그가 출력되지 않아도 연산을 실행된다 -> 불필요함, 자원 낭비
        //log.trace("trace my log =" + name);

        // Level: trace > debug > info > warn > error
        // /resources/static/application.properties 에서 로그 레벨 설정을 할 수 있음
        // 로그 레벨 설정 시 하위 레벨 로그들이 출력됨
        log.trace("trace log={}", name);    // local pc
        log.debug("debug log={}", name);    // 개발 서버
        log.info(" info log={}", name);     // 운영 서버
        log.warn(" warn log={}", name);
        log.error(" error log={}", name);

        return "ok";
    }
}
