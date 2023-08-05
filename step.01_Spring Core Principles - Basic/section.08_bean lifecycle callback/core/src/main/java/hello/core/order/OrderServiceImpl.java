package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
// 기가막힌 롬복 시작
@RequiredArgsConstructor // final 키워드가 붙은 객체의 필드 요소를 파라미터로하는 생성자를 만들어 줌
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    
    // 새로운 할인 정책을 적용할 경우 아래의 코드를 사용함
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    
    // 의존성을 줄이기 위해 아래의 코드를 사용함
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    private  final DiscountPolicy discountPolicy;

    /* @RequiredArgsConstructor 사용으로 생성자를 작성할 필요가 없음 (`ctrl + F12`를 누르면 생성자가 자동으로 생성됨을 알 수 있음)
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    */

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // @Configuration 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
