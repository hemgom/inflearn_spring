# 섹션 08. 빈 생명주기 콜백
## 01. 빈 생명주기 콜백 시작
`데이터베이스 커넥션 풀`이나, 네트워크 소켓처럼 애플리케이션 시작 시점에 필요한 연결을 미리 해두고, 애플리케이션 종료 시점에 연결을 모두 종료하는 작업을 진행하려면, `객체의 초기화와 종료 작업`이 필요  
<br/>

간단하게 외부 네트워크에 미리 연결하는 객체를 하나 생성한다고 가정
- 실제로 네트워크에 연결하는 것은 아니고 단순히 문자만 출력하도록 함
- `NetworkClient`은 애플리케이션 시점에 따라 아래의 내용을 수행하여야 함
  - 시작 시점 : `connect()`를 호출해서 연결을 맺어두어야 함
  - 종료 시점 : `disConnect()`를 호출해서 연결을 끊어야 함  
<br/>

### 예제 코드 
- `class NetworkClient` : 외부 네트워크 역할, 실제 연결이 아닌 메서드 호출시 메세지 출력을 통해 결과 확인
- `class BeanLifeCycleTest` : 스프링 환경설정 및 실행 역할  
<br/>

### 실행 결과
```
생성자 호출, url = null
connect: null
call: null message = 초기화 연결 메시지
```
- 너무 당연하지만 객체 생성 당시에 `url`의 정보가 없었기 때문에 위와 같은 결과가 출력된다.
- 현재는 생성 후에 `url` 정보를 `networkClient.setUrl("http://hello-spring.dev");`코드로 설정 했음
  - 만약 설정된 `url`을 연결하고 싶으면 `lifeCycleTest()`메소드(외부)에서 `connect()`를 사용해 연결하면 됨  
<br/>

### 간단한 스프링 빈의 라이프사이클
`객체 생성` -> `의존관계 주입`
- 스프링은 의존관계 주입이 완료되면 스프링 빈에게 콜백 메서드를 통해서 초기화 시점을 알려주는 다양한 기능을 제공
- 스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 준다.  
<br/>

### 간단한 스프링 빈의 라이프사이클(싱글톤의 경우)
`스프링 컨테이너 생성` -> `스프링 빈 생성` -> `의존관계 주입` -> `초기화 콜백` -> `사용` -> `소멸전 콜백` -> `스프링 종료`  
- `초기화 콜백` : 빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출
- `소멸전 콜백` : 빈이 소멸되기 직전에 호출
- 스프링 빈은 다양한 방식으로 생명주기 콜백을 지원  
<br/>

- `참고!` : 객체의 생성과 초기화를 분리하자!
  - `생성자`는 필수정보(파라미터)를 받고, 메모리를 할당해서 `객체를 생성하는 책임`을 가짐
  - `초기화`는 생성된 값들을 활용해서 `외부 커넥션을 연결`하는 등 `무거운 동작을 수행`  
- `참고!` : 싱글톤 빈들은 `스프링 컨테이너가 종료될 때` 싱글톤 빈들도 함께 종료됨
  - 그래서 스프링 컨테이너가 종료되기 직전에 소멸전 콜백이 일어남
    - 컨테이너와 무관하게 해당 빈이 종료되기 직전에 소멸전 콜백이 일어나는 `생명주기가 짧은 빈들`도 있음  
<br/>

### 스프링은 크게 3가지 방법으로 빈 생명주기 콜백을 지원한다
- `인터페이스`(InitializingBean, DisposableBean)
- `설정 정보`에 초기화 메서드, 종료 메서드 지정
- @PostConstruct, @PreDestroy `애노테이션 지원`  
<br/><br/><br/>

## 02. 인터페이스 InitializingBean, DisposableBean
### 코드 수정 - NetworkClient (테스트 코드)
```
public class NetworkClient implements InitializingBean, DisposableBean {
  ...
  
  @Override
  public void afterPropertiesSet() throws Exception {
    connect();
    call("초기화 연결 메시지");
  }

  @Override
  public void destroy() throws Exception {
    disConnect();
  }
}
```
- `InitializingBean`은 `afterPropertiesSet()`으로 `초기화`를 지원함
- `DisposableBean`은 `destroy()`로 `소멸`을 지원함  
<br/>

### 출력 결과
```
생성자 호출, url = null
NetworkClient.afterPropertiesSet
connect: http://hello-spring.dev
call: http://hello-spring.dev message = 초기화 연결 메시지
13:24:49.043 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing 
NetworkClient.destroy
close: http://hello-spring.dev
```
- 출력을 확인하면 의존관계 주입이 완료된 이후에 메서드가 적절하게 호출된걸 알 수 있음
- `close()`로 스프링 컨테이너 종료가 호출되자 `소멸 메서드(destroy())`가 호출됨을 알 수 있음  
<br/>

### 초기화 및 소멸 인터페이스의 단점
- 해당 인터페이스들은 `스프링 전용 인터페이스`임, 즉 `스프링 전용 인터페이스에 의존`하는 성향을 가짐
- 초기화 및 소멸 메서드의 `이름을 변경할 수 없음`
- 개발자가 코드를 직접 고칠 수 없는 `외부 라이브러리`에 적용이 불가능함  
<br/>

- `참고!` : 인터페이스를 사용한 초기화 및 소멸 방법은 `스프링 초창기 기술`이다.
  - 즉, 현재는 더 나은 기술들이 있기에 거의 사용하지 않는 기술임