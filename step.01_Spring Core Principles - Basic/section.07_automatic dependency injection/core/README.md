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
<br/><br/><br/>

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
<br/><br/><br/>

## 03. 생성자 주입을 선택해라!
과거에는 `수정자(setter) 주입`과 `필드 주입`을 많이 사용했었다.  
하지만 최근에는 스프링을 포함한 DI 프레임워크 대부분이 `생성자 주입`을 권장하는데 그 이유는 아래와 같다.    
### 1. 불변
   - 대부분의 의존관계 주입은 한 번 수행되면 애플리케이션 종료시점까지 의존관계를 변경할 일이 없음
     - 오히려 애플리케이션 종료 전에 변경되면 문제가 생김
   - `수정자 주입`을 사용하려면 `setXxx()`를 `public`으로 열어두어야 함
     - 분명 누구간 변경하는 실수가 생기므로 메서드를 열어두는 건 좋지 않은 설계 방법이다.
     - `생성자 주입`의 경우 객체 생성시 딱 1번 호출 되므로 불변하게 설계를 원할 때 사용하기 용이하다.
### 2. 누락
   - 의존관계에 의해 필요한 `객체 생성 또는 주입의 누락`이 발생함
   - 아래의 테스트 코드 결과로 그 차이점 더 명확하게 알 수 있다.
```
    @Test
    void createOrder() {
      OrderServiceImpl orderService = new OrderServiceImpl();
      orderService.createOrder(1L, "itemA", 10000);
    }
```
- `수정자(setter) 주입` : 실행은 되나 실행 결과를 보면 `NPE(Null Point Exception)`이 발생
  - 이유는 `memberRepository` `discountPolicy`의 의존관계 주입이 생략되었기 때문
- `생성자 주입` : 애초에 실행이 되지 않는 `컴파일 오류`가 발생함
  - 오류 발생을 직관적으로 볼 수 있어 개발자가 객체 생성시 의존관계 주입이 필요함을 쉽게 알 수가 있음
  - `IDE`에서 어떤 값을 필수로 주입해야하는지도 알 수 있음
### 3. final 키워드
   - `생성자 주입`사용 시 필드에 `final`키워드를 사용할 수 있음
     - 그렇기에 혹시라도 생성자에 코드가 누락되면 컴파일 시점에 누락을 확인 할 수 있다.
     - 컴파일 오류 `java: variable discountPolicy might not have been initialized`
   - 컴파일 오류는 오류들 중 `가장 빠르게 확인이 가능하며 좋은 오류이다!`  
<br/>

- `참고!` : `생성자 주입`을 제외한 나머지 주입 방식들은 모두 생성자 이후에 호출 된다.
  - 그렇기에 필드에 `final`키워드를 사용할 수 없다.
  - 필드에 `final`키워드를 사용하고 싶다면 오직 `생성자 주입`방식을 사용해야 한다.  
<br/>

### 정리 
- 프레임워크에 의존하지 않고 순수한 자바 언어의 특징을 잘 살리기 위해 `생성자 주입`방식을 사용하자!
- 기본적으로 `생성자 주입`을 사용하고, 필수 값이 아닐 경우에는 `수정자 주입`으로 옵션을 부여하면 된다.
  - 당연하지만 두 가지 주입 방식을 동시에 사용할 수는 없다.
- 되도록 `생성자 주입`을 사용하도록 하자!!
  - 가끔 옵션이 필요한 경우 `수정자 주입`을 사용하고 `필드 주입`은 생각도 하지 말자!  
<br/><br/><br/>

## 04. 롬복과 최신 트랜드
개발을 하다보면 대부분이 `불변`이어야하고 그래서 필드에 `final`키워드를 사용하게 된다.  
하지만 할게 많은 개발자의 입장에서 `필드 주입`같은 편리함을 찾게 되는데 이러한 방법에 대해 알아보자!  
<br/>

### 롬복 라이브러리 적용 방법
#### build.gradle에 라이브러리 및 환경 추가
```
  ...
  // lombok 설정 추가 시작
  configurations {
    compileOnly {
	  extendsFrom(annotationProcessor)
	}
  }
  // lombok 설정 추가 끝
  
  ...
  
    dependencies {
	  implementation 'org.springframework.boot:spring-boot-starter'
	
	  // lombok 라이브러리 추가 시작
	  compileOnly 'org.projectlombok:lombok'
	  annotationProcessor 'org.projectlombok:lombok'
	
	  testCompileOnly 'org.projectlombok:lombok'
	  testAnnotationProcessor 'org.projectlombok:lombok'
	  // lombok 라이브러리 추가 끝
	
	  testImplementation 'org.springframework.boot:spring-boot-starter-test'
    }
  ...
```
- 해당 코드를 입력해도 되고 스프링 부트 생성시 `dependency -> Lombok을 추가`해도 된다.
- `lombok`을 추가 했다면 아래의 설정도 해주도록 하자.
  1. Preferences(윈도우 File Settings) plugin `lombok` 검색 - 설치 - 실행(재시작)
  2. `Preferences Annotation Processors` 검색 `Enable annotation processing 체크`(재시작)
  3. 임의의 테스트 클래스를 만들고 `@Getter` `@Setter` 확인  
<br/>

### 기존 코드와 롬복 사용 코드
```
@Component
// 기가막힌 롬복 시작
@RequiredArgsConstructor // final 키워드가 붙은 객체의 필드 요소를 파라미터로하는 생성자를 만들어 줌
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private  final DiscountPolicy discountPolicy;

    /* `@RequiredArgsConstructor` 사용으로 생성자를 작성할 필요가 없음(생성자 = 기존코드)
    (`ctrl + F12`를 누르면 생성자가 자동으로 생성됨을 알 수 있음)
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    */
    ...
}
```
- 기존 코드의 경우 생성자가 1개라 `@Autowired`를 생략할 수 있다는 편리함이 있었음
- 롬복 적용을 하였더니 `@RequiredArgsConstructor` 하나만으로 생성자 작성을 생략 할 수 있었음
  - `@RequiredArgsConstructor` : `fianl`키워드가 붙은 필드를 모아 생성자를 자동으로 만들어줌
    - 실제로 코드에는 보이지 않지만 `ctrl + F12`를 통해 생성자가 생성됨을 알 수 있음
- 코드의 내용이 너무나 간결해져 정말 편해 보임
  - 또한 이후에 `final`키워드가 붙은 필드가 추가되도 생성자를 수정할 필요 없이 필드만 추가하면 됨  
<br/>

### 정리
최근에는 생성자를 1개 두어 `@Autowired`를 생략하는 방식을 많이 사용한다.  
여기에 `Lombok`의 `@RequiredArgsConstructor`을 더하면 기능은 다 제공 받으면서 깔끔한 코드를 사용할 수 있다.  
한 마디로 __금상첨화!!__  
<br/><br/><br/>

## 05. 조회 빈이 2개 이상 - 문제
### @Autowired는 타입(Type)으로 조회를 한다
```
@Autowired
private DiscountPolicy discountPolicy
```
- 타입으로 조회하기 때문에 `ac.getBean(DiscountPolicy.class)`와 비슷한 동작을 함
  - 물론 더 많은 기능을 제공함
- 스프링 빈 조회시 타입으로 조회 할 때 만약 같은 타입의 빈이 2개 이상 있을 때 문제가 발생함  
<br/>

### 예시 상황
- 같은 인터페이스를 상속받는 2개의 구현체(구현 클래스)들을 스프링 빈으로 선언 (@Component 사용)
- 이 상태에서 필드의 인터페이스를 타입에 `의존관계 자동 주입(@Autowired)`를 붙여 실행
- `NoUniqueBeanDefinitionException` 오류가 발생한다.
```
NoUniqueBeanDefinitionException: No qualifying bean of type
'hello.core.discount.DiscountPolicy' available: expected single matching bean
but found 2: fixDiscountPolicy,rateDiscountPolicy
```
- 친절하게도 하나의 빈을 기대했지만 `fixDiscountPolicy` `rateDiscountPolicy` 2개가 발견됬다고 알려줌
- 하위 타입으로 지정해 해결할 수 있긴 하지만 이는 `DIP`를 위배하고 `유연성`이 떨어지게 된다.
  - 또 이름만 다른 같은 타입의 스프링 빈이 2개 있다면 해결이 되지 않음
- 수동으로 등록해서 해결해도 되지만 사실 해당 문제에 대한 해결 방법은 여러가지가 있음  
<br/>

## 06. @Autowired 필드 명, @Qualifire, @Primary
이전 강의에서 다룬 문제의 해결 방법에 대해서 알아보도록 하자!  
<br/>

### 조회 대상 빈이 2개 이상일 때
- `@Autowired` 필드명 매칭
- `@Qualifire` -> `@Qualifire`끼리 매칭 -> 빈 이름 매칭
- `@Primary` 사용  
<br/>

### @Autowired - 필드명 매칭
__기존 코드__
```
@Autowired
private DiscountPolicy discountPolicy
```
__필드 명(변수 명)을 사용할 빈의 이름과 같게 변경__
```
@Autowired
private DiscountPolicy rateDiscountPolicy
```
- `@Autowired`의 조회(매칭) 과정을 확인 해 보면
  1. 필드(변수)의 타입이 일치하는 빈을 찾음
  2. 만약 같은 타입의 빈이 2개 이상 이라면, 필드 명(변수 명)과 같은 이름의 빈을 찾음
- 즉, `필드 명 매칭`은 타입으로 우선 매칭 시도를 한 후에 다수의 빈이 있다면 추가로 동작하는 기능이다.  
<br/>

### @Qualifire - 빈 등록시 붙여 줌
`@Qualifire`는 빈 이름을 변경하는 기능이 아니라 `등록한 이름이 같은 @Qualifire`를 찾는 기능을 제공한다.
__빈 등록시__
```
@Component
@Qualifier("mainDiscountPolicy")
public class RateDiscountPolicy implements DiscountPolicy {}
```
__생성자 자동 주입시__
```
@Autowired
public OrderServiceImpl(MemberRepository memberRepository,
              @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
  this.memberRepository = memberRepository;
  this.discountPolicy = discountPolicy;
}
```
- `@Qualifire("mainDiscountPolicy)`를 붙여서 의존관계를 주입하면 같은 `@Qualifire("mainDiscountPolicy)`를 찾는다.
  - 만약 같은 `@Qualifire("mainDiscountPolicy)`가 없다면 `"mainDiscountPolicy"`와 같은 빈 이름을 찾는다.
  - 그래도 없다면 `NoSuchBeanDefinitionException`예외가 발생한다.
- 하지만 명확하게 `@Qualifire`끼리 찾는 용도로 사용하는게 애매하지 않아 좋다.
- `@Qualifire`는 예시 처럼 `생성자 주입`뿐만 아니라 `수정자 주입` `필드 주입`에도 사용 가능하다.
  - 심지어 이전에 직접 빈을 등록할 때 사용했던 `@Bean` 사용시에도 `@Qualifire`를 사용할 수 있다.  
<br/>

### @Primary 사용
`@Primary`는 `우선순위`를 정하는 방법이다. `@Autowired` 수행시 다수의 빈이 조회되면 `@Primary`가 우선 매칭 된다.
__빈 등록시__
```
@Component
@Primary
public class RateDiscountPolicy implements DiscountPolicy {}
```
- 빈 등록시 `@Primary`를 붙여주기만 하면 된다.  
<br/>

### 그럼 @Qualifire vs @Primary ??
1. 코드 작성 편함의 기준으로 보면
   - `@Primary` 승! : `@Qualifire`의 경우 사용할 빈 등록과 의존관계 주입시 전부 `Qualifire`를 붙여줘야 함
2. 둘을 모두 사용하는 경우의 우선순위는
   - `@Qualifire` 승! : `@Qualifire`는 상세하게 동작하고 `@Primary`는 기본값 처럼 동작하기 때문
     - 스프링은 자동보다는 `수동`을 그리고 넒은 범위의 선택권 보다는 `좁은 범위의 선택권`을 우선시 한다.