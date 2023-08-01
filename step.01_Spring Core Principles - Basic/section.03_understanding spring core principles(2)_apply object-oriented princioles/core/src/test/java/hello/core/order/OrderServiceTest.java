package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    // MemberService memberService = new MemberServiceImpl();
    // OrderService orderService = new OrderServiceImpl();
    // AppConfig를 통한 의존성 주입
    // '@BeforeEach'는 '@Test' 실행 전 무조건 수행 됨, 테스트 코드가 n개라면 n번 수행함
    MemberService memberService;
    OrderService orderService;
    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }


    @Test
    void createOrder() {
        Long memeberId = 1L;
        Member member = new Member(memeberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memeberId, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
