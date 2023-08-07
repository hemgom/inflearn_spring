# 섹션 09. 빈 스코프
## 01. 빈 스코프란?
지금까지 강의에서 스프링 빈이 컨테이너의 시작과 함께 생성되어 스프링 컨테이너가 종료될 때 까지 유지된다고 배움  
 - 스프링 빈이 기본적으로 `싱글톤 스코프`로 생성되기 때문
 - `스코프` : 번역하면 `범위`, 스프링 빈이 존재할 수 있는 범위를 뜻함  
<br/>

### 스프링이 지원하는 다양한 스코프
- `싱글톤` : 기본 스코프, `스프링 컨테이너의 시작과 종료까지` 유지되는 `가장 넓은 범위`의 스코프
- `프로토타입` : 스프링 컨테이너는 `프로토타입 빈 생성과 의존관계 주입까지`만 관여하는 `매우 짧은 범위`의 스코프
- `웹 관련 스코프`
  - `request` : `웹 요청이 들어오고 나갈때 까지` 유지되는 스코프
  - `session` : `웹 세션이 생성되고 종료될 때 까지` 유지되는 스코프
  - `application` : `웹의 서블릿 컨텍스트와 같은 범위`로 유지되느 스코프  
<br/>

### 빈 스코프 지정
#### 컴포넌트 스캔 자동 등록
```
@Scope("prototype")
@Component
public class HelloBean {}
```
#### 수동 등록
```
@Scope("prototype")
@Bean
PrototypeBean HelloBean() {
    return new HelloBean();
}
```  
<br/>

### 정리
지금까지는 결국 `싱글톤 스코프`를 사용했던 것!  
<br/><br/><br/>

## 02. 프로토타입 스코프
`싱글톤 스코프`의 경우 빈을 조회하면 스프링 컨테이너는 항상 같은 인스턴스의 스프링 빈을 제공함  
하지만 `프로토타입 스코프`의 경우에는 스프링 컨테이너에 빈을 조회하면 항상 새로운 인스턴스를 생성해 반환함  
<br/>

### 빈 요청 - 싱글톤 스코프
![img.jpg](img.jpg)
1. 빈을 스프링 컨테이너에 요청
2. 스프링 컨테이너는 생성 당시에 생성해둔 스프링 빈을 반환(모두 같은 인스턴스)
   - 그렇기에 이후 같은 스프링 빈에 대한 요청이 와도 같은 인스턴스의 스프링 빈을 반환함  
<br/>

### 빈 요청 - 프로토타입 스코프
__요청 과정(그림.1)__
![img_1.jpg](img_1.jpg)
__반환 과정(그림.2)__
![img_2.jpg](img_2.jpg)
1. 빈을 스프링 컨테이너에 요청
2. 스프링 컨테이너는 요청 시점에 프로토타입 빈을 생성하고 필요한 의존관계를 주입함
   - 여기까지가 `그림.1`의 과정
3. 스프링 컨테이너는 생성한 프로토타입 빈을 클라이언트에 반환함
   - 이후 같은 스프링 빈에 대한 요청시 스프링 컨테이너는 새로운 프로토타입 빈을 생성해 반환함
   - 여기까지가 `그림.2`의 과정  
<br/>

### 정리
`스프링 컨테이너는 프로토타입 빈을 생성하고 의존관계 주입 및 초기화까지만 처리한다.`  
프로토타입 빈을 관리하는 책임은 스프링 컨테이너가 아닌 반환 받은 클라이언트에 있다.  
그래서 `@PreDestroy`같은 소멸 메서드가 호출되지 않음
<br/>

### 싱글톤 스코프 빈 테스트 - SingletonTest
__실행 결과__
```
SingletonBean.init
singletonBean1 = hello.core.scope.PrototypeTest$SingletonBean@54504ecd
singletonBean2 = hello.core.scope.PrototypeTest$SingletonBean@54504ecd
org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing 
SingletonBean.destroy
```
- `빈 초기화 메서드 실행` -> `같은 인스턴스의 빈을 조회` -> `종료 메서드 정상 호출`
  - 출력된 결과물로 위의 과정이 순차적으로 진행됨을 알 수 있음  
<br/>

### 프로토타입 스코프 빈 테스트 - PrototypeTest
__실행 결과__
```
find prototypeBean1
PrototypeBean.init
find prototypeBean2
PrototypeBean.init
prototypeBean1 = hello.core.scope.PrototypeTest$PrototypeBean@13d4992d
prototypeBean2 = hello.core.scope.PrototypeTest$PrototypeBean@302f7971
org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing
```
- `프로토타입 스코프 빈`은 스프링 컨테이너에서 요청을 받아 빈을 조회할 때 생성하고 초기화 메서드도 실행됨
- 스프링 빈 생성시 서로다른 인스턴스의 빈을 생성해 초기화도 2번 실행된 것을 알 수 있다.
- 생성된 프로토타입 빈을 반환한 이후 스프링 컨테이너는 더 이상 반환한 스프링 빈을 관리하지 않음
  - 실행 결과를 보면 `@PreDestroy`(소멸 메서드)가 실행되지 않았음
  - 프로토타입 빈을 소멸(종료)시키려면 클라이언트(`PrototypeTest`)에서 생성된 인스턴스에 `destroy()`를 사용해야 함  
<br/>

### 정리 - 프로토타입 스코프 빈의 특징
- 스프링 컨테이너에 빈 조회를 요청 할 때마다 새로운 프로토타입 빈이 생성됨
- 스프링 컨테이너는 `프로토타입 빈의 생성, 의존관계 주입, 초기화`까지만 관리(관여)한다.
- 스프링 컨테이너를 통한 소멸(종료) 메서드는 수행되지 않는다.
  - 소멸(종료) 메서드에 대한 호출은 프로토타입 빈을 호출한 클라이언트에서 해야한다.