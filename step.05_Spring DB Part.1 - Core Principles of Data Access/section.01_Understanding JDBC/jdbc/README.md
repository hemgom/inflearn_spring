# 섹션 01. JDBC 이해
## 02. H2 데이터베이스 설정
### h2
- `H2 데이터베이스`는 개발이나 테스트 용도로 사용하기 좋은 `DB`
  - 굉장히 가볍고 편리함, `SQL`을 실행할 수 있는 웹 화면도 제공
- 버전 : `2.2.224`  
<br/>

### 테이블 생성하기
```
create table member (
    member_id varchar(10),
    money integer not null default 0,
    primary key (member_id)
);

insert into member(member_id, money) values ('hi1',10000);
insert into member(member_id, money) values ('hi2',20000);
```
- 위의 `SQL`을 h2 웹 콘솔에 입력해 `memeber` 테이블을 생성
- `select * from memeber;`를 입력해 저장한 데이터가 출력되는지 확인