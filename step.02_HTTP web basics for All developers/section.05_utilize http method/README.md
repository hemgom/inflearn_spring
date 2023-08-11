# 섹션 05. HTTP 메서드 활용
## 01. 클라이언트에서 서버로 데이터 전송
### 데이터 전달 방식은 크게 2가지!
1. 쿼리 파라미터를 통한 데이터 전송
	- GET
	- 주로 정렬 필터 (검색어)
2. `message body`를 통한 데이터 전송
	- POST, PUT, PATCH
	- 회원 가입, 상품 주문, 리소스 등록, 리소스 변경 등  
<br/>

### 4가지 상황
1. __정적 데이터 조회__
	- 이미지, 정적 텍스트 문서
	- 조회의 경우 `GET` 사용
	- `정적 데이터`는 일반적으로 쿼리 파라미터 없이 `리소스 경로`로 단순하게 조회 가능
2. __동적 데이터 조회__
	- 주로 검색, 게시판 목록에서 정렬 필터(검색어)
	- `조회 조건을 줄여주는 필터` `조회 결과를 정렬하는 정렬 조건`에 주로 사용
	- 조회의 경우 `GET` 사용
		-	GET의 경우 `쿼리 파라미터`를 사용해 데이터를 전달
		-	`HTTP 스펙`상으로는 GET도 `message body`를 사용할 수 있긴 하나 지원하지 않는 서버가 많아 권장하지 않음
3. __HTML Form을 통한 데이터 전송__
	- HTML Form submit POST 전송
		- ex) 회원 가입, 상품 주문, 데이터 변경 등
	- Content-Type : `application/x-www-form-urlencoded` 사용
		- `Form submit 버튼`을 누르면 웹 브라우저가 `Form 데이터`를 읽어 `HTTP 메시지`를 생성해줌
		- 전송 데이터를 `url encoding` 처리
		- HTML Form은 GET 전송도 가능
			- GET의 경우 `쿼리 파라미터`에 전송 데이터 저장
				-  `주의!` : GET은 조회에만 사용되고 `리소스 변경이 발생하는 곳`에선 사용하면 안됨
			- POST의 경우 `message body`에 전송 데이터 저장
				- 거의 GET의 `쿼리 파라미터`와 유사하게 서버에 전송함
	- Content-Type : `multipart/form-data`
		- 파일 업로드 같은 `바이너리 데이터 전송시` 사용
		- 다른 종류의 여러 파일과 폼의 내용이 함께 전송 (그래서 multipart!)
	- `참고!` : HTML Form 전송은 `GET, POST만 지원`함
4. __HTTP API를 통한 데이터 전송__
	- 서버 to 서버
		- 백엔드 시스템 통신
	- 앱 클라이언트
		- 아이폰, 안드로이드
	- 웹 클라이언트
		- HTML에서 Form 전송 대신 자바 스크립트를 통한 통신에 사용 (AJAX)
		- ex) React, VueJs 같은 웹 클라이언트와 API 통신
	- POST, PUT, PATCH : `message body`를 통해 데이터 전송
	- GET : 조회, `쿼리 파라미터`로 데이터 전달
	- Content-Type : `application/json`을 주로 사용 (실상 표준)
		- TEXT, XML, JSON 등  
<br/><br/><br/>

## 02. HTTP API 설계 예시
- HTTP API - 컬렉션
	- POST 기반 등록
	- ex) 회원 관리 API 제공
- HTTP API -스토어
	- PUT 기반 등록
	- ex) 정적 컨텐츠 관리, 원격 파일 관리
- HTML FORM 사용
	- 웹 페이지 회원 관리
	- GET, POST만 지원  
<br/>

### 회원 관리 시스템 개발의 경우
#### 1-1. API 설계 - POST 기반 등록
- 회원 목록 /members -> GET
- 회원 등록 /members -> __POST__
- 회원 조회 /members/{id} -> GET
- 회원 수정 /members/{id} -> __PATCH__, PUT, POST
- 회원 삭제 /members/{id} -> DELETE  
<br/>

#### 1-2. POST - 신규 자원 등록 특징
- 클라이언트는 등록될 리소스의 URI를 모름 (서버가 생성하기 때문)
	- 회원 등록 /members -> POST
	- POST /members
- `서버가 새로 등록된 리소스 URI를 생성`해 줌
```
HTTP/1.1 201 Created
Location: /members/100
```
- 컬렉션 (Collection)
	- 서버가 관리하는 리소스 디렉토리
	- 서버가 리소스의 URI를 생성/관리
	- 현재 예시에서 컬렉션은 `/members`  
<br/>

#### 2-1. API 설계 - PUT 기반 등록
- 파일 목록 /files -> GET
- 파일 조회 /files/{filename} -> GET
- 파일 등록 /files/{filename} -> __PUT__
- 파일 삭제 /files/{filename} -> DELETE
- 파일 대량 등록 /files -> POST
<br/>

#### 2-2. PUT - 신규 자원 등록 특징
- 클라이언트가 `리소스 URI`를 알고 있어야 함
	- 파일 등록 /files/{filesname} -> PUT
	- PUT /files/star.jpg
- `클라이언트가 직접` 리소스의 URI를 지정함 -> 스토어
	- 스토어(Store)
		- 클라이언트가 `리소스 URI`를 알고 있으면서 관리하는 `리소스 저장소`
		- 현재 예시에서 스토어는 `/files`  
<br/>

- `참고!` : 실무에서는 대부분 `POST 기반의 컬렉션`을 사용함, `PUT 기반 스토어`는 드뭄  
<br/>

#### 3-1. HTML FORM 특징
- GET, POST만 지원함
- `AJAX`같은 기술을 사용해 해결 가능
- 단, `순수한 HTML, HTML FORM`에 해당함
- 당연하지만 GET, POST만 지원하므로 `제약`이 존재  
<br/>

#### 3-2. HTML FORM 사용 - (feat. 순수한)
- 회원 목록 /members -> GET
- 회원 등록 폼 __/members/new__ -> GET
- 회원 등록 __/members/new__, /members -> POST
- 회원 조회 /members/{id} -> GET
- 회원 수정 폼 __/members/{id}/edit__ -> GET
- 회원 수정 __/members/{id}/edit__, /members/{id} -> POST
- 회원 삭제 /members/{id}/delete -> POST  
<br/>

#### 3-3. HTML FORM 사용 특징
- GET, POST만 사용 -> 제약이 있음 (회원 삭제)
- 컨트롤 URI (제약 문제 해결)
	- 동사로 된 리소스 경로 사용
		- ex) `/new` `/edit` `delete`
	- HTTP 메서드로 해결하기 애매한 경우 사용 (HTTP API 포함)
	- 정 안될 때 `대체제`로서 사용하길 권장함  
<br/>

### ※ 참고하기 좋은 [URI 설계 개념](https://restfulapi.net/resource-naming)
- 문서(document)
	- 단일 개념(파일 하나, 객체 인스턴스, 데이터베이스 row)
	- ex) /members/100, /files/star.jpg
- 컬렉션(collection)
	- `서버가 관리하는 리소스 디렉터리`
	- 서버가 리소스의 URI를 생성하고 관리
	-  ex) /members
- 스토어(store)
	- `클라이언트가 관리하는 자원(리소스) 저장소`
	- 클라이언트가 리소스의 URI를 알고 관리
	- ex) /files
- 컨트롤러(controller), 컨트롤 URI
	- 문서, 컬렉션, 스토어로 해결하기 어려운 `추가 프로세스 실행`
	- `동사`를 직접 사용
	- ex) /members/{id}/delete