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
```
public class OrderServiceImpl implements OrderService {
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    private DiscountPolicy discountPolicy;
}
```
- 하지만 구현체가 없기 때문에 코드 실행 시 `NPE(Null pointer exception)`이 발생한다.
- `해결방안` : 누군가 클라이언트에 인터페이스의 구현 객체를 대신 생성하고 주입해주어야 함!!  
<br/>

## 03. 관심사의 분리
### 애플리케이션이 하나의 공연이라고 생각할 때
- 배우는 본인의 배역을 수행하는 것만 집중해야 한다.
- 상대 배역의 배우가 바뀌더라도 배우의 배역 수행에 변경이 없어야 한다.
- 공연 구성, 담당배우 섭외 등의 책임을 담당하는 별도의 `공연 기획자`가 필요한 시점!!
- `공연 기획자`를 통해서 책임을 확실하게 분리하도록 한다.  
<br/>

### AppConfig 등장!
- 애플리케이션의 전체 동작 방식을 구성(config)하기 위해, 구현 객체를 생성하고 연결하는 책임을 가진 별도의 클래스
- `AppConfig`는 실제 동작에 필요한 `구현 객체를 생성`한다!
  - `MemberServiceImpl` `MemoryMemberRepository` `OrderServiceImpl` `FixDiscountPolicy`
- `AppConfig`는 생성한 객체 인스턴스의 참조(레퍼런스)를 `생성자를 통해서 주입(연결)`한다!  
<br/>

- `참고(2)` : 현재까지는 각 클래스에 생성자가 없기에 컴파일 오류가 발생하고 있다.  
<br/>

### MemberServiceImpl - 생성자 주입
- 설계 변경으로 해당 클래스는 더이상 `MemoryMemberRepository`를 의존하지 않음
- 단지 `추상(interface MemberRepository)`에만 의존한다.
- 클라이언트 입장에서는 어떤 구현 객체가 들어올지(주입될지) 알 수도 알 필요도 없다!
  - 이제부터 어떠한 구현 객체가 주입 될지는 오직 `외부(AppConfig)`에서 결정하기 때문이다.
- 위에서 예시로든 것 처럼 해당 클래스는 `의존관계`에 대한 부분은 외부에서 처리하고 `내부 실행에만 집중`하면 된다.  
<br/>

### 그림 - 클래스 다이어그램
![img_5.png](img_5.png)
- 객체 생성과 연결은 `AppConfig`가 담당
- `DIP 충족!` : `MemberServiceImpl`은 이제부터 추상(인터페이스)에만 의존하면 됨(a.k.a 구체 클레스 is 뭔들)
- `관심사의 분리` : `객체 생성 및 주입(연결) 역할` `실행 역할`이 명확하게 분리됨  
<br/>

### 그림 - 회원 객체 인스턴스 다이어그램
![img_6.png](img_6.png)
- `appConfig` 객체는 `memoryMemberRepository` 객체를 생성, 그 참조값을 `memberServiceImpl`로 생성하며 생성자로 전달함
- 클라이언트인 `memberServiceImpl` 입장에서는 의존관계를 마치 외부에서 주입해주는 것 같음
  - 이를 `DI(Dependency Injection)` 우리말로 `의존관계 주입` 또는 `의존성 주입`이라 한다.  
<br/>

### OrderServiceImpl - 생성자 주입
- 위에 먼저 말한 `MemberSerivceImpl - 생성자 주입`과 같다.
  - 기존에 의존하고 있던 구체 클래스의 개수의 차이만 있을 뿐 `AppConfig`를 통해 더 이상 구체 클래스에 의해 여향을 받지 않음!
- 해당 클래스는 `의존관계`에 대한 부분은 외부에서 처리하고 `내부 실행에만 집중`하면 된다.  
  <br/>

### AppConfig 실행 - 사용 클래스 내
```
public class MemberApp {
  public static void main(String[] args) {
    AppConfig appConfig = new AppConfig();
    MemberService memberService = appConfig.memberService();
    ...
  }
}
```
- `AppConfig`를 생성하고 `MemberService` 생성시 `AppConfig`가 선택한 구체 클래스를 주입함
- `OrderApp`도 마찬가지  
<br/>

### AppConfig 실행 - 테스트 코드 내
```
class MemberServiceTest {
  MemberService memberService;
  @BeforeEach
  public void beforeEach() {
    AppConfig appConfig = new AppConfig();
    memberService = appConfig.memberService();
  }
  ...
}
```
- `@BeforeEach`를 사용해 테스트 코스 실행마다 `beforeEach()`를 수행한다.
- `memberService`를 선언하고 `beforeEach()`를 통해 `AppConfig` 생성 및 구체 클레스를 주입한다.
- `OrderServiceTest`도 마찬가지  
<br/>

### 정리
- `AppConfig`를 통해서 역할을 명확하게 분리함
- 클라이언트는 `추상(인터페이스)`에만 의존하면 되고 `구체 클래스`는 `AppConfig`가 생성 및 주입을 담당하게 됨  
<br/>

## 04. AppConfig 리팩터링
- 현재 상황에서 `AppConfig`는 `중복`이 존재하고 `역할`에 따른 `구현`이 잘 보이지 않는다.  
<br/>

### 기대하는 그림
![img_7.png](img_7.png)  
<br/>

### 리팩터링 후
- 중복을 제거하고 역할에 따른 구현이 잘 보이도록 리팩터링을 진행하였다.
```
public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }
}
```
- 이로서 `AppConfig`의 전체 구성이 어떤지 빠르게 파악할 수 있게 되었다!
- 또한 `MemoryMemberRepository` `FixDiscountPolicy`를 변경 시 해당 메서드만 바꾸면 된다는 장점도 생겼다!