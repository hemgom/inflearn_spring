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