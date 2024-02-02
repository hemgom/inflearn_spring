# 섹션 01. 데이터 접근 기술
## 01. 데이터 접근 기술 진행 방식 소개
### 적용 데이터 접근 기술
- JdbcTemplate
- MyBatis
- JPA, Hibernate
- 스프링 데이터 JPA
- Querydsl  
<br/>

### SQLMapper
- JdbcTemplate
- MyBatis  
<br/>
### ORM 관련 기술
- JPA, Hibernate
- 스프링 데이터 JPA
- Querydsl  
<br/>

### SQLMapper 주요 기능
- 개발자는 SQL 만 작성하면 해당 SQL 의 결과를 객체로 편리하게 매핑해줌
- JDBC 를 직접 사용할 때 발생하는 여러가지 중복을 제거해줌, 기타 개발자가에 여러 편리한 기능을 제공  
<br/>

### ORM 주요 기능
- JPA 를 사용하면 기본적인 SQL 은 대신 작성하고 처리해줌
  - 개발자는 저장할 객체를 자바 컬렉션에 저장하고 조회하듯이 사용하면 ORM 기술이 DB 에 해당 객체를 저장하고 조회해줌
  - `JdbcTemplate`이나 `MyBatis` 같은 SQL 매퍼 기술은 개발자가 SQL 을 직접 작성해야 함
- `JPA`는 자바 진영의 ORM 표준이며, `Hibernate`는 JPA 에서 가장 많이 사용하는 구현체임
  - 즉, 자바에서는 ORM 사용시 JPA 인터페이스를 사용하고, 구현체로 Hibernate 를 사용한다고 생각하면 됨
- `스프링 데이터 JPA`, `Querydsl`은 JPA 를 더 편리하게 사용할 수 있게 도와주는 프로젝트 임
  - 실무에서는 거의 필수라고 보면 됨  
<br/><br/><br/>

## 03. 프로젝트 구조 설명1 - 기본
### DTO(data transfer object)
- 데이터 전송 객체
  - 기능은 없고, 데이터 전달 용도로 사용되는 객체를 뜻 함
    - 기능이 없어야만 하는건 아니며 데이터 전달이 주목적인 객체라면 DTO 라 칭할 수 있음
- DTO 를 붙이는 건 객체의 용도를 구별하기 위함 (= 안 붙여도 됨, 권장할 뿐)
  - 이러한 규칙은 정해진 것이 아니라 일관성 있게 유지하는 것에 의미가 있음  
<br/>

### CRUD 란?
데이터를 다루는 방법 네 가지의 첫 글자를 조합한 이름
- `생성(Create)`, `조회(Read)`, `갱신(Update)`, `삭제(Delete)`를 뜻 함  
<br/><br/><br/>

## 04. 프로젝트 구조 설명2 - 설정
### 프로필
스프링은 로딩 시점, `application.properties`의 `spring.profiles.active` 속성을 읽어 프로필로 사용함
- `프로필`: 로컬, 운영 환경, 테스트 실행 등, 다양한 환경에 따라 다른 설정을 할 때 사용하는 정보
- 프로필을 통해 환경에 따라 다른 스프링 빈을 등록할 수 있음