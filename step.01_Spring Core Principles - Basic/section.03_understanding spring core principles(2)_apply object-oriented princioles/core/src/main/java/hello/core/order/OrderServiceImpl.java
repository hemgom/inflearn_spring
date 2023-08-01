package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    
    // 새로운 할인 정책을 적용할 경우 아래의 코드를 사용함
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    
    // 의존성을 줄이기 위해 아래의 코드를 사용함
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    private  final DiscountPolicy discountPolicy;

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
