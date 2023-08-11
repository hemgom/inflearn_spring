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
1. 정적 데이터 조회
	- 이미지, 정적 텍스트 문서
	- 조회의 경우 `GET` 사용
	- `정적 데이터`는 일반적으로 쿼리 파라미터 없이 `리소스 경로`로 단순하게 조회 가능
2. 동적 데이터 조회
	- 주로 검색, 게시판 목록에서 정렬 필터(검색어)
	- `조회 조건을 줄여주는 필터` `조회 결과를 정렬하는 정렬 조건`에 주로 사용
	- 조회의 경우 `GET` 사용
		-	GET의 경우 `쿼리 파라미터`를 사용해 데이터를 전달
		-	`HTTP 스펙`상으로는 GET도 `message body`를 사용할 수 있긴 하나 지원하지 않는 서버가 많아 권장하지 않음
3. HTML Form을 통한 데이터 전송
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
4. HTTP API를 통한 데이터 전송
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