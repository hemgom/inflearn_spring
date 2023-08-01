# 섹션 03. 스프링 핵심 원리 이해(2) - 객체 지향 원리 적용
## 01. 새로운 할인 정책 개발
- `참고(1)` : [애자일 소프트웨어 개발 선언](https://agilemanifesto.org/iso/ko/manifesto.html)  
<br/>

### __RateDiscountPolicy__ 추가
![img.png](img.png)  
<br/>

- `class RateDiscountPolicy` : 일정 %의 할인 금액을 반환한다.
- `class RateDiscountPolicyTest` : 회원의 등급이 `VIP`인지 아닌지에 따라 원하는 결과가 나오는지 확인한다.  
<br/><br/><br/>

## 02. 새로운 할인 정책 적용과 문제점
### 새로운 할인 정책 적용시 문제점 발견!
- 분명 역할과 구현을 충실히 분리 -> check!
- 다형성 활용은 물론이고 인터페이스와 구현 객체를 분리 -> check!
- OCP, DIP` 같은 객체지향 설계 원칙을 충실히 준수 -> 그렇게 느끼지만 실상은 아니다!
  - 어? `DIP의 경우` `OrderServiceImpl`는 `DiscountPolicy` 인터페이스에 의존하면서 지키지 않았나?
    - 클래스 의존 관계를 분석하면 추상뿐 아니라 구체 클래스에도 의존하고 있음을 알 수 있다.
    - 추상(인터페이스) 의존 : `DiscountPolicy`
    - 구체(구현) 의존 : `FixDiscountPolicy` `RateDiscountPolicy`
  - `OCP의 경우` : `OrderServiceImpl`는 이미 구체 클래스도 의존하고 있기 때문에 구체 클래스 변경 시 영향을 받는다!
    - 즉 영향을 받기에 OCP를 위반했다고 할 수 있다.  
<br/>

### 기대했던 혹은 바랬던 의존관계
![img_1.png](img_1.png)  
단순하게 `DiscountPolicy` 인터페이스에만 의존한다고 잘못 생각함  
<br/>

### 실제 의존관계
![img_2.png](img_2.png)  
보다싶이 `DiscountPolicy`뿐 아니라 구체 클래스 `FixDiscountPolicy`도 함께 의존하고 있다  
이는 `DIP 위반`이다!!  
<br/>

### 정책 변경 시 의존관계
![img_3.png](img_3.png)  
그래서 보는 것 처럼 구체 클래스를 타 클래스로 변경하는 순간 `OrderServiceImpl`도 함께 수정해야 하므로 `OCP 위반`이다.  
<br/>

### 과연 어떻게 위의 문제를 해결할 수 있을까?
- 현재 문제는 `OrderServiceImpl`가 인터페이스와 구현 클래스 모두 의존하는게 문제
- 즉 `인터페이스`만 의존하도록 의존관계를 수정하면 된다!!  
<br/>

### 인터페이에만 의존하도록 설계를 변경!
![img_4.png](img_4.png)  
<br/>

### 인터페이스에만 의존하도록 코드를 변경!
```agsl
public class OrderServiceImpl implements OrderService {
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    private DiscountPolicy discountPolicy;
}
```
- 하지만 구현체가 없기 때문에 코드 실행 시 `NPE(Null pointer exception)`이 발생한다.
- `해결방안` : 누군가 클라이언트에 인터페이스의 구현 객체를 대신 생성하고 주입해주어야 함!!