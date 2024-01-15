# 섹션 02. 서블릿
## 03. HttpServletRequest - 개요
### HttpServletRequest 역할
- HTTP 요청 메시지를 개발자가 직접 파싱해서 사용해도 되지만, 매우 불편할 것
- 서블릿은 개발자가 HTTP 요청 메시지를 편리하게 사용할 수 있도록 개발자 대신에 HTTP 요청 메시지를 파싱
	- `HttpServletRequest` 객체에 담아서 제공  
<br/>

### HTTP 요청 메시지
```
POST /save HTTP/1.1
Host: localhost:8080
Content-Type: application/x-www-form-urlencoded

username=kim&age=20
```
- START LINE
	- HTTP 메소드
	- URL
	- 쿼리 스트링
	- 스키마, 프로토콜
- 헤더
	- 헤더 조회
- 바디
	- form 형식 조회
	- message body 데이터 직접 조회  
<br/>

### 여러가지 부가기능
- 임시 저장소 기능
	- 해당 HTTP 요청의 시작부터 끝날 때까지 유지되는 임시 저상소 기능
		- 저장: `request.setAttribute(name, value)`
		- 조회: `request.getAttribute(name)`
- 세션 관리 기능
	- `request.getSession(create: true)`  
<br/>

#### 중요
HttpServletRequest, HttpServletResponse를 사용할 때 가장 중요한 점
- 객체들이 HTTP 요청 메시지, HTTP 응답 메시지를 편리하게 사용하도록 도와주는 객체라는 점
- 해당 기능에 대해서 깊이있는 이해를 하려면 HTTP 스펙이 제공하는 요청, 응답 메시지 자체를 이해해야 함  
<br/><br/><br/>

## 05. HTTP 요청 데이터 - 개요
### HTTP 요청 메시지를 통해 클라이언트에서 서버로의 데이터 전달 방법
1. GET - 쿼리 파라미터
	- `/url?username=hello&age=20`
	- 메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해 전달
	- ex) 검색, 필터, 페이징 등에서 많이 사용하는 방식
2. POST - HTML Form
	- `content-type: application/x-www-form-urlencoded`
	- 메시지 바디에 쿼리 파라미터 형식으로 전달 `username=hello&age=20`
	- ex) 회원 가입, 상품 주문, HTML Form 사용
3. HTTP message body 에 데이터를 직접 담아서 요청
	- HTTP API에서 주로 사용, JSON, XML, TEXT
4. 데이터 형식은 주로 JSON 사용
	- POST, PUT, PATCH