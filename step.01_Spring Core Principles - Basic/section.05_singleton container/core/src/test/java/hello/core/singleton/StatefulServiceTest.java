package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        // 1. 사용자 A가 주문 요청 - 10000원 주문
        statefulService1.order("userA", 10000);
        // 2. 사용자 B가 주문 요청 - 20000원 주문
        statefulService2.order("userB", 20000);
        // 위에 2줄의 코드를 수정한다.
        // `order()`에 반환 값이 생겼으므로 `int userAPrice, userBPrice`를 만들어 값을 저장한다.

        // 3. 사용자 A의 주문금액 조회
        int price = statefulService1.getPrice();
        // 해당 구문도 문제해결을 위해 지워준다.

        // 4. 사용자 A의 주문금액은 10000원이지만 출력은 20000원이 출력됨
        System.out.println("userA price = " + price);
        // `price`를 출력할게 아니라 `userAPrice`의 값을 출력한다.

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    @Configuration
    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}