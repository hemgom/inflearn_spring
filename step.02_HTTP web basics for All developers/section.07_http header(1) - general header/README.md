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
<br/><br/><br/>

## 02. 표현 (Representation)
- 표현 헤더
	- `Content-Type` : 표현 데이터의 형식
	- `Content-Encoding` : 표현 데이터의 압축 방식
	- `Content-Language` : 표현 데이터의 자연 언어
	- `Content-Length` : 표현 데이터의 길이
-  표현 헤더는 전송과 응답 둘다 사용함  
<br/>

### Content - Type (표현 데이터의 형식)
"메시지 바디의 데이터가 뭐야?"에 대한 힌트 또는 답을 알려줌
- 미디어 타입, 문자 인코딩
	- ex) `text/html; charset=utf-8` `appication/json` `image/png`  
<br/>

### Content - Encoding (표현 데이터 인코딩)
- 표현 데이터 압축을 위해 사용
- 데이터를 전달하는 쪽에서 압축 후 인코딩 헤더를 추가함
- 데이터를 받는 쪽에서 인코딩 헤더의 정보로 압축을 해제함
	- ex) `gzip` `deflate` `identity`  
<br/>

### Content - Language (표현 데이터의 자연언어)
- 표현 데이터의 자연 언어를 표현
	- ex) `ko` `en` `en-US`  
<br/>

### Content - Length (표현 데이터의 길이)
- 바이트 단위
- `Transfer - Encoding(전송 코딩)`을 사용하면  `Content - Length`를 사용하면 안됨  
<br/><br/><br/>

## 03. 콘텐츠 협상
### 협상 (콘텐츠 네고시에이션)
클라이언트가 선호하는 표현 요청
- 협상 헤더
	- `Accept` : 클라이언트가 선호하는 미디어 타입 전달
	- `Accept-Charset` : 클라이언트가 선호하는 문자 인코딩
	- `Accept-Encoding` : 클라이언트가 선호하는 압축 인코딩
	- `Accept-Language` : 클라이언트가 선호하는 자연 언어
- 협상 헤더는 요청시에만 사용가능  
<br/>

###  예시
#### Accept-Language 적용 전과 후
- 클라이언트 : 한국 브라우저
- 서버 (다중 언어 지원 서버)
	1. 기본 영어(en) 
	2. 한국어 지원(ko)
- Accept-Language 적용 전
	1. 클라이언트가 서버에 접속 요청
		- 하지만 요청에는 클라이언트가 선호하는 언어에 대한 데이터가 없음
	2. 서버는 기본 언어로 요청에 응답함
		- 영어로 된 사이트가 출력됨
- Accept-Language 적용 후
	1. 클라이언트가 서버에 접속 요청
		- 선호하는 언어가 한국어라는 데이터가 요청에 들어있음
	2. 서버는 한국어도 지원하기에 한국어로 요청에 응답함
		- 한국어로 된 사이트가 출력됨  
<br/>

### 협상과 우선순위 (1)
- `Quality Values(q)` 값 사용
- `0 ~1` 범위를 값으로 가지며, 값이 클수록 높은 우선순위를 가짐
	- `1` 값은 생략이 가능하기에 만약 `q`가 생략되어 있다면 그 값은 `1`이다.  
- ex) Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7
	- `ko-KR(=ko-KR;q=1)` : 1순위
	- `ko;q=0.9` : 2순위
	- `en-US;q=0.8` : 3순위
	- `en;q=0.7` : 4순위
- 서버는 클라이언트가 선호하는 언어의 순위를 확인하고 지원하는 언어 중 높은 순위에 해당 하는 언어로 응답을 제공한다.  
<br/>

### 협상과 우선순위 (2)
- 구체적인 것을 우선시 함
- ex) Accept: text/\*, text/plain, text/plain;format=flowed, \*/\*
	-  `text/plain, text/plain;format=flowed` : 1순위
	- `text/plain` : 2순위
	-  `text/*` : 3순위
	- `*/*` : 4순위  
<br/>

### 협상과 우선순위 (3)
- 구체적인 것을 기준으로 미디어 타입을 맞춤
- ex) Accept: text/*;q=0.3, text/html;q=0.7, text/html;level=1, text/html;level=2;q=0.4, \*/\*;q=0.5
	- `text/html;level=1` : q = 1
	- `text/html` : q = 0.7
	- `text/plain` : q = 0.3
	- `image/jpeg` : q = 0.5
	- `text/html;level=2` : q = 0.4
	- `text/html;level=3` : q = 0.7  
<br/><br/><br/>

## 04. 전송 방식
### 단순 전송 - Content-Length
서버가 응답시 `메시지 바디`의 길이를 적어 전송함 (처음부터 끝까지 단순하게)
	- 한 번에 요청/응답이 이루어짐  
<br/>

### 압축 전송 - Content-Encoding
서버가 응답시 `메시지 바디`의 내용을 압축하여 전송함
	- 단, 요청메시지에 어떤 방식으로 압축했는지에 대한 정보가 담겨있어야함
		- ex) Content-Encoding: gzip
		- 클라이언트가 압축방식을 알아야 압축을 해제할 수 있기 때문  
<br/>

### 분할 전송 - Transfer-Encoding
서버가 `메시지 바디`를 덩어리(청크)로 나누어 순차적으로 클라이언트에게 전송함
	- 해당 전송 방식 사용시 `Content-Length`가 들어가면 안됨
		- 총 길이가 예상하기 어렵고, 애초에 바이트 단위로 데이터를 나누어 전송하기 때문  
<br/>

### 범위 전송 - Range, Content-Range
클라이언트가 일정 범위의 데이터를 요구하면 서버가 일정 범위의 데이터를 전송해줌
	- 예를 들어 클라이언트가 서버가 전송한 데이터 다운로드 중 서버가 끊겨 작업을 완료하지 못한 경우
	- 다시 처음부터 데이터를 받지 않고 다운로드된 마지막 부터 데이터를 재요청하면 됨  
<br/><br/><br/>

## 05. 일반 정보
### 정보성 헤더 - 일반 정보
#### From - 유저에이전트의 이메일 정보
- 일반적으로 `잘 사용되지 않음`
- `검색 엔진` 같은 곳에서 주로 사용됨
- `요청`에서 사용  
<br/>

#### Referer - 이전 웹 페이지 주소
- 현재 요청된 페이지의 이전 웹 페이지 주소 (뒤로가기를 누르면 나오는 웹 페이지)
- `A -> B`로 이동하는 경우 `B`요청시 `Referer: A`를 포함해 요청
- `Referer`를 통해 `유입 경로 분석` 가능
- `요청`에서 사용
- `참고!` : 단어 `referrer`의 오타임  
<br/>

#### User-Agent - 유저 에이전트 애플리케이션 정보
- 클라이언트의 애플리케이션 정보 (웹 브라우저 정보 등)
	- 유저 에이전트 = 클라이언트 애플리케이션
- 통계 정보
- 어떤 종류의 브라우저에서 `장애가 발생하는지 확인` 가능
- `요청`에서 사용  
<br/>

#### Server - 요청을 처리하는 ORIGIN 서버의 소프트웨어 정보
- ORIGIN 서버 : 클라이언트의 요청에 응답해주는 진짜 서버
- `응답`에서 사용  
<br/>

#### Date - 메시지가 발생한 날짜와 시간
- `응답`에서 사용 (최신 스펙)  
<br/><br/><br/>

## 06. 특별한 정보
### 정보성 헤더 - 특별한 정보
#### Host - 요청한 호스트 정보 (도메인)
- `요청`에서 사용
- `필수 헤더`
	- 하나의 서버가 여러 도메인을 처리 할 때
	- 하나의 IP 주소에 여러 도메인이 적용되어 있을 때  
<br/>

#### Location - 페이지 리다이렉션
- 웹 브라우저는 300 번대 응답의 결과에 `Location 헤더`가 있으면, `Location 위치`로 자동 이동 (리다이렉트)
- `201 (Created)` : Location 값은 요청에 의해 생성된 리소스 URI
- `3xx (Redirection)` : Location 값은 요청을 자동으로 리다이렉션하기 위한 대상 리소스를 가리킴  
<br/>

#### Allow - 허용가능한 HTTP 메서드
- `405 (Method Not Allowed)`, 응답에 포함해야함
	- ex) Allow: GET, HEAD, PUT -> 해당 메서드만 지원합니다!  
<br/>

#### Retry-After - 유저 에이전트가 다음 요청을 하기까지 기다려야 하는 시간
- `503 (Service Unavailable)` : 서비스가 언제까지 불능인지 알려줄 수 있음
	- 날짜, 초단위 표기`로 서버 정상화까지 걸리는 시간을 알려줄 수 있음