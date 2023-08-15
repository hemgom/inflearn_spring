# 섹션 07. HTTP 헤더(1) - 일반 헤더
## 01. HTTP 헤더 개요
- `hearder-field` = `field-name`: OWS `field-value` OWS
	- OWS : 띄어쓰기 허용
	- `field-name`은 영대소문자 구분 없음  
<br/>

### 용도
- HTTP 전송에 필요한 모든 부가정보
	- ex) 메시지 바디에 내용, 메시지 바디의 크기/압축/인증, 요청 클라이언트, 서버 정보 등
- [표준 헤더](https://en.wikipedia.org/wiki/List_of_HTTP_header_fields)는 많다.
- 필요시 `임의 헤더`추가 가능  
<br/>

### 분류 - RFC2616 (과거)
- `헤더` 분류
	- General 헤더: 메시지 전체에 적용되는 정보
		- ex) Connection: close
	- Request 헤더: 요청 정보 
		- ex) User-Agent: Mozilla/5.0 (Macintosh; ..)
	- Response 헤더: 응답 정보
		- ex) Server: Apache
	- Entity 헤더: 엔티티 바디 정보
		- ex) Content-Type: text/html, Content-Length: 3423
- `메시지 본문(message body)`
	- 메시지 본문은 `엔티티 본문(entity body)`을 전달하는데 사용
		- 엔티티 본문 ; 요청이나 응답에서 잔달할 실제 데이터
	- `엔티티 헤더`는 `엔티티 본문`의 데이터를 해석할 수 있는 정보 제공
		- 엔티티 헤더 : 데이터 유형(html, json), 데이터 길이, 압축 정보 등  
<br/>

### 그런데...
- 1999년 RFC2616 -> 2014년 RFC7230 ~ 7235 등장
	- RFC2616 폐기, `엔티티 본문(entity body)`라는 용어가 사라짐  
<br/>

### RFC723x 변화
- `엔티티(Entity)` -> `표현(Representation)`
	- 완변한 `1:1` 대체는 아님 폐기된 후 새로운 개념이 생겼다.
- `표현(Representation)` = `표현 메타데이터(Representation Metadata)` + `표현 데이터(Representation Data)`  
<br/>

### RFC7230 (최신)
- `메시지 본문(message body)`을 통해 표현 데이터 전달
	- 메시지 본문 = `페이로드(payload)`
- `표현(Representation)` : 요청이나 응답에서 전달할 실제 데이터
- `표현 헤더`는 `표현 데이터`를 해석할 수 있는 정보를 제공함
	- 데이터 유형(html, json), 데이터 길이, 압축 정보 등  
<br/>
 
- `참고!` : 표현 헤더는 표현 메타데이터와, 페이로드 메시지를 구분해야 하나 해당 강의에선 생략