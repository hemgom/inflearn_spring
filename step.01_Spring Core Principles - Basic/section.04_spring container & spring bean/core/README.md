# 섹션 04. 스프링 컨테이너와 스프링 빈
## 01. 스프링 컨테이너 생성
```
//스프링 컨테이너 생성
ApplicationContext applicationContext =
    new AnnotationConfigApplicationContext(AppConfig.class);
```
- `interface ApplicationContext`를 스프링 컨테이너라고 함
- 스프링 컨테이너는 XML을 기반으로 만들 수 있으며 애노테이션 기반의 자바 설정 클래스로도 만들 수 있음
  - 이전 강의에서 `AppConfig`를 활용한 것이 애노테이션 기반으로 스프링 컨테이너를 만든 것임
  - `new AnnotationConfigApplicationContext(AppConfig.class);`를 통해 만들 수 있음
    - 해당 클래스는 `ApplicationContext의 구현체`이므로 가능!  
<br/>

- `참고(1)` : `BeanFactory`를 직접 구현하는 경우는 거의 없어 `ApplicationContext`를 스프링 컨테이너라고 한다.  
<br/>

### 스프링 컨테이너의 생성 과정
__1. 스프링 컨테이너 생성__
![img.jpg](img.jpg)
- 컨테이너 생성 : `new AnnotationConfigApplicationContext(AppConfig.class)`
- 생성 시 `구성 정보`를 지정해주어야 함
  - 예시 그림에서는 `AppConfig.class`를 구성 정보로 지정함  
<br/>

__2. 스프링 빈 등록__
![img_1.jpg](img_1.jpg)
- 스프링 컨테이너는 파라미터로 넘어온 설정 클래스 정보를 사용해 스프링 빈을 등록함
- 빈 이름의 경우 `메서드 명`을 사용하며 직접 부여도 가능함
  - 직접 부여할 경우 : `@Bean(name = "memberService2")`  
<br/>

- `주의!` : 빈 이름은 항상 서로 다른 이름을 부여해야 함, 같은 이름 부여 시에는 빈이 무시되거나 덮어버리는 오류가 발생  
<br/>

__3. 스프링 빈 의존관계 설정 준비__
![img_2.jpg](img_2.jpg)  
<br/>

__4. 스프링 빈 의존관계 설정 - 완료__
![img_3.jpg](img_3.jpg)
- 스프링 컨테이너는 설정 정보를 참고해 `의존관계 주입(DI)`을 함
- 어찌보면 자바 코드를 호출하는 것 처럼 보이나 분명한 차이가 존재하며 후에 `싱글톤 컨테이너`에서 다룰려고 한다.  
<br/>

- `참고(2)`
  - `스프링`은 빈을 생성하고, 의존관계 주입하는 단계가 나누어져 있음
  - 위에 설명처럼 `자바 코드`로 스프링 빈을 등록하면 `생성자 호출`과 `의존관계 주입`이 한 번에 처리됨  
<br/>

### 정리
스프링 컨테이너를 생성하고, 스프링 빈도 등록하고, 의존관계도 설정했으니 다음엔 스프링 컨테이너에서 `데이터 조회`를 해보자!  
<br/><br/><br/>

## 02. 컨테이너에 등록된 모든 빈 조회
- `Tip!` : 코드 작성 시 리스트나 배열이 있는 경우 `iter + 엔터`를 하면 `for문`이 자동으로 생성된다!
- `Tip!` : 작성 코드를 드래그해 선택한 후 `ctrl + d'를 하면 선택 영역이 복사 된다!  
<br/>

### class ApplicationContextInfoTest
- 모든 빈 출력 - `findAllBean()`
  - 실행 시 스프링에 등록된 모든 빈 정보를 출력
  - `getBeanDefinitionNames()` : 스프링에 등록된 모든 빈 이름을 조회
  - `getBean()` : 빈 이름으로 빈 객체(인스턴스)를 조회
- 애플리케이션 빈 출력 - `findApplicationBean()`
  - 스프링 내부에서 사용하는 빈 제외 본인이 직접 등록한 빈만 출력
  - `getRole()` : 스프링 내부에서 사용하는 빈을 구분
    - `ROLE_APPLICATION` : 일반적으로 사용자가 정의한 빈
    - `ROLE_INFRASTRUCTURE` : 스프링이 내부에서 사용하는 빈  
<br/><br/><br/>

## 03. 스프링 빈 조회 - 기본
### 스프링 컨테이너에서 스프링 빈을 찾는 가장 기본적이 방법
- `getBean(빈 이름, 타입)` `getBean(타입)`
- 조회 대상 스프링 빈이 없을 경우 예외 발생!
  - ex) `NoSuchBeanDefinitionException: No bean named 'xxxxx' available`  
<br/>

- `Tip!` : 코드 작성 시 `ctrl + e`를 사용하면 이전에 작성하던 코드로 이동이 가능하다!  
<br/>

### 예제 코드 - ApplicationContextBasicFindTest
- `findBeanName()`: 빈 이름과 타입으로 조회
  - `MemberService.class` 타입의 `"memberService"`란 이름을 가진 빈을 조회 후 변수에 저장
  - 변수에 저장된 빈이 `MemberServiceImpl`의 인스턴스인지 확인
- `findBeanByType()` : 빈의 타입으로만 조회
- `findBeanName2()` : 인스턴스가 아닌 구현 클래스 타입으로 조회
  - 이와 같은 방법은 구현 클래스의 변경에 영향을 받으므로 유연성이 떨어져 좋지 않음
- `findBeanNameX()` : 빈의 이름이 없는 확인(있다면 테스트 실패, 없다면 테스트 성공)
  - 람다식을 사용하여 빈 이름을 조회할 때 `NoSuchBeanDefinitionException` 발생하는지 확인