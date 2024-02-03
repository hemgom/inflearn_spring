# 섹션 07. 데이터 접근 기술 - Querydsl
## 01. Querydsl 소개1 - 기존 방식의 문제점
### 문제
#### Query 의 문제점
- `Query`는 문자, `Type-check 불가능`
- 실행하기 전까지 `작동여부 확인이 불가능`
  - `컴파일 에러(편하고 좋은 에러)`로는 확인이 불가능하다는 말  
<br/>

#### JPQL
- SQL 도 자바 클래스 처럼 `타입`이 있고, `자바 코드`로 작성
- Type - safe
  - 컴파일시 에러를 확인(체크) 가능  
<br/>

#### QueryDSL
- `Query`를 자바 코드로 `type-safe`하게 개발 할 수 있도록 지원하는 `프레임 워크`
- 주로 `JPA 쿼리(JPQL)`에 사용됨  
<br/>

### JPA Query
#### JPA 에서 QUERY 방법 3가지
- `JPQL (HQL)`
- `Criteria API`
- `MetaModel Criteria API (type-safe)`  
<br/>

#### 장단점
- 장점 : `SQL QUERY`와 비슷해 빠른 학습 가능
- 단점 : `type-safe`가 아님, 동적 쿼리 생성이 어려움  
<br/>

#### Criteria API
- 장점 : `동적쿼리` 생성이 쉬울 지도?
- 단점 : `type-safe` 아니며, 높은 난이도의 복잡도로 인한 학습의 어려움(부담)
- 이후 `MetaModel Criteria API (type-safe)`가 등장
  - 근데 `Criteria API`에서 `type-safe`가 추가 되었을 뿐 여전히 복잡하고 어려움  
<br/><br/><br/>

## 02. Querydsl 소개2 - 해결
### QueryDSL
#### DSL - Domain Specific Language (도메인 특화 언어)
- `특정한 도메인에 초점`을 맞춰, `제한적인 표현력`을 가진 컴퓨터 프로그래밍 언어
- `단순`, `간결`, `유창`  
<br/>

#### QueryDSL - 쿼리 도메인 특화 언어
- `JPA`, `Mongo DB`, `SQL` 같은 기술들을 위해 `type-safe SQL`을 만드는 프레임워크
- 쿼리에 특화된 프로그래밍 언어
- 다양한 저장소 쿼리 기능 통합  
<br/>

### QueryDSL - JPA
- `JPA Query(JPQL)`을 `type-safe`하게 작성하는데 주로 
- 장점 : `type-safe`, `단순`하고 `쉬움`
- 단점 : `APT`를 설정해야 함  
<br/>

### SpringDataJPA + Querydsl
- `SpringData 프로젝트`의 약점은 조회 -> `Querydsl`으로 복잡한 조회 기능을 보완
  - 복잡한 조회 기능 = 복잡한 쿼리 + 동적 쿼리
- 경우에 따라 선택적 사용
  - `단순 - SpringFataJPA`, `복잡 - Querydsl 직접 사용`