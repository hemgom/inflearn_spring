# 섹션 05. 검증2 - Bean Validation
## 01. Bean Validation - 소개
검증 기능을 매번 코드로 작성하는 것은 상당히 번거롭다.
- 특히 특정 필드 검증 로직은 대부분 `빈 값`인지, 특정 크기(범위)를 벗어났는지와 같이 매우 일반적인 로직임
```java
public class Item {
    
    private Long id;
    
    @NotBlank
    private String itemName;
    
    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;
    
    @NotNull
    @Max(9999)
    private Integer quantity;
    
    //...
}
```
위의 검증 로직을 모든 프로젝트에 적용할 수 있도록 `공통화`하고 `표준화` 한 것이 바로 `Bean Validation`임
- `Bean Validation`을 잘 활용한다면 애노테이션 하나로 검증 로직을 매우 편리하게 적용 가능함
<br/>

### Bean Validation 이란?
`Bean Validation`은 특정 구현체가 아니라 `Bean Validation 2.0(JSR-380)`이라는 기술 표준임
- 쉽게 말해 `검증 애노테이션과 여러 인터페이스의 모음`이다.
- JPA가 표준 기술이고 그 구현체로 하이버네이트가 있는 꼴
  - `Bean Validation`의 경우 일반적으로 `하이버네이트 Validator`을 구현체로 사용함
  - 이름에 하이버네이트가 들어가지만 `ORM`과는 무관함  
<br/>

### 하이버네이트 Validator 관련 링크
- 공식 사이트: http://hibernate.org/validator/
- 공식 메뉴얼: https://docs.jboss.org/hibernate/validator/6.2/reference/en-US/html_single/  
<br/><br/><br/>

## 11. Form 전송 객체 분리 - 소개
사실상 실무에서는 `groups`기능을 잘 사용하지 않는다고 한다.
- 이유: 등록시 폼에서 전달하는 데이터가 Item 도메인 객체와 딱 맞지 않기 때문임  
- 실무에서는 `회원 관련 데이터(현재 예제)`뿐만 아니라 `약관 정보 등`의 수많은 부가 데이터가 오고가기 때문이다.
<br/>

그렇기에 실무에서는 `Item`을 직접 주고 받지 않고 복잡한 폼의 데이터를 컨트롤러까지 전달할 별도의 객체를 만들어 전달한다고 한다.
- ex) `ItemSaveForm`을 전달 받는 `전용 객체를 생성`해 `@ModelAttribute`로 사용하는 식
  - 생성한 전용 객체를 통해 컨트롤러에서 폼 데이터를 받은 후 컨트롤러에서 필요 데이터를 사용해 `Item`을 생성한다.  
<br/>

#### 폼 데이터 전달에 Item 도메인 객체 사용
- `HTML Form -> Item -> Controller -> Item -> Repository`
  - 장점: Item 도메인 객체를 `컨트롤러, 리포지토리`까지 `직접 전달`, 중간에서 Item을 만드는 과정이 따로 없어 간단함
  - 단점: 정말 간단한 경우에만 적용이 가능함, 수정시 검증 중복 문제와 groups를 사용해야 함  
<br/>

#### 폼 데이터 전달을 위한 별도의 객체 사용
- `HTML Form -> ItemSaveForm -> Controller -> Item 생성 -> Repository`
  - 장점: 전송 폼 데이터가 복잡해도 맞춤 폼 객체를 사용해 데이터를 전달 받을 수 있음, 보통 등록/수정용으로 별도의 폼 객체를 만들기에 검증 중복이 없음
  - 단점: 폼 데이터 기반으로 컨트롤러에서 Item 객체를 생성하는 변환 과정이 추가됨  
<br/>

수정의 경우 등록과 완전히 다른 데이터가 넘어오게 됨
- 예로 등록 같은 경우 `로그인ID, 주민번호 등등`을 받겠지만 수정시에는 이런 부분이 빠지게 된다.
- 때문에 검증 로직도 많이 달라짐
  - 즉, `ItemUpdateForm`이라는 별도의 객체로 데이터를 전달받는 것이 좋다!  
<br/>

### 따라서
실무에서는 사실상 `groups` 기능을 적용할 일이 매우 드물다