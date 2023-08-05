# 섹션 07. 의존관계 자동 주입
## 01. 다양한 의존관계 주입 방법
크게 4가지 방법이 있음
1. 생성자 주입
2. 수정자 주입(setter)
3. 필드 주입
4. 일반 메서드 주입  
<br/>

### 생성자 주입 
- 생성자를 통해 의존관계를 주입 받는 방법
  - 지금까지 강의를 따라오면서 진행했던 방법
- 특징
  - 생성자 호출시점에 딱 1번 호출되는 것이 보장
  - `불변` `필수` 의존관계에 사용
- `중요!` : 생성자가 1개만 있다면 `@Autowired`를 생략해도 자동 주입이 된다.
  - 물론 스프링 빈에만 해당하며 최근에는 해당 방법을 많이 사용함  
<br/>

### 수정자(setter) 주입
- `setter`라 불리는 필드의 값을 변경하는 수정자 메소드를 통해 의존관계를 주입
- 특징
  - `선택` `변경` 가능성이 있는 의존관계에 사용
  - `자바빈 프로퍼티 규약`의 `수정자 메서드 방식`을 사용함
```
@Component
public class OrderServiceImpl implements OrderService {

  private MemberRepository memberRepository;
  private DiscountPolicy discountPolicy;

  @Autowired
  public void setMemberRepository(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Autowired
  public void setDiscountPolicy(DiscountPolicy discountPolicy) {
    this.discountPolicy = discountPolicy;
  }
}
```
- `@Autowired`의 기본 동작은 주입할 대상이 없을 경우 오류 발생
  - 해결 방법 : `@Auotowired(required = false)`로 지정하면 됨  
<br/>

- `참고!` : 자바빈 프로퍼티, 자바에서는 `setXxx` `getXxx`라는 메서드를 통해 값을 읽거나 수정하는 규칙이 있음
  - 이를 `자바빈 프로퍼티 규약`이라고 함
- 자바빈 프로퍼티 규약 예시
```
class Data {

  private int age;
  
  public void setAge(int age) {
    this.age = age;
  }
  
  public int getAge() {
    return age;
  }
}
```  
<br/>

### 필드 주입
- 필드에 바로 주입하는 방법
- 특징
  - 코드가 간결해서 좋아보이지만 외부에서 변경이 불가능해 테스트하기 힘들다는 단점이 있음
  - `DI`프레임워크가 없으면 아무것도 할 수 없음
  - 결론 : 사용하지 말자!
    - `테스트코드`, 스프링 설정을 위한 `@Configuration`에서 특별한 용도로만 가끔 사용됨
```
  @Component
  public class OrderServiceImpl implements OrderService {
  
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private DiscountPolicy discountPolicy;
  }
```
- `참고!` : 순수 자바 테스트 코드에서는 `@Autowired` 동작이 되지 않음
  - `@SpringBootTest`처럼 스프링 컨테이너를 테스트에 통합한 경우에만 가능
- `참고!` : `@Bean`에서 파라미터 의존관게는 자동 주입됨
  - 수동 등록시 자동 등록된 빈의 의존관계가 필요할 때 문제 해결 가능  
<br/>

### 일반 메서드 주입
- 일반 메서드를 통해서 주입 받을 수 있음
- 특징
  - 한번에 여러 필드를 주입 받을 수 있음
  - 일반적으로 잘 쓰이지 않음
```
  @Component
  public class OrderServiceImpl implements OrderService {
    
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    @Autowired
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
      this.memberRepository = memberRepository;
      this.discountPolicy = discountPolicy;
    }
  }
```
- `참고!` : 의존관계 자동 주입은 스프링 컨테이너가 관리하는 `스프링 빈`이어야 동작함
  - `Member`같은 클래스에서 `@AutoWired`를 적용해봤자 아무 기능도 하지 않음  
<br/>

## 02. 옵션 처리
주입할 스프링 빈이 없어도 동작해야 할 때가 있다.  
그런데 `@Autowired`만 사용하면 `required`옵션의 기본값이 `true`로 되어있으므로 자동 주입 대상이 없으면 오류가 발생  
<br/>

### 자동 주입 대상을 옵션으로 처리하는 방법
- `@Autowired(required = false)` : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 되지 않음
- `org.springframework.lang.@Nullalbe` : 자동 주입할 대상이 없으면 `null`이 입력됨
- `Optional<T>` : 자동 주입할 대상이 없으면 `Optional.empty`가 입력됨  
<br/>

### 예제 코드 - AutoWiredTest
- `Member`는 스프링 빈이 아님
- `setBean1()`은 `Autowired(required = false)`이므로 호출 자체가 되지 않음
  - 의존관계가 없기 때문에
- 출력 결과
```
  setNoBean2 = null
  setNoBean3 = Optional.empty
```  
<br/>

- `참고!` : `@Nullable` `Optional`은 스프링 전반에 걸쳐 지원됨
  - 생성자 자동 주입에서 특정 필드에만 사용해도 된다.