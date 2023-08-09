# 섹션 02. URI와 웹 브라우저 요청 흐름
## 01. URI(Uniform Resource Identifier)
["URI는 로케이터(locator), 이름(name) 또는 둘 다 추가로 분류 될 수 있다"](https://www.ietf.org/rfc/rfc3986.txt) - `1.1.3. URI, URL, and URN`  
<br/>

### URI - 단어 뜻
- `Uniform` : 리소스 식별 방식의 통일
- `Resource` : 자원, URI로 식별할 수 있는 모든 것(제한 X)
- `Identifire` : 다른 항복과 구분시 필요한 정보
- 그 외 용어
	- `URL` : Uniform Resource Locator(웹 브라우저 주소창에 적는 그것!)
	- `URN` : Uniform Resource Name  
<br/>

### URL, URN
- `URL - Locator` : 리소스가 있는 위치를 지정
- `URN -Name` : 리소스에 이름을 부여
- 위치는 변할 수 있지만 이름은 변하지 않음
	- ex) `urn:isbn:5465498421 (어떤 책의 isbn URN)
- 사실 `URN`만으로 실제 리소스를 찾는 방법이 보편화 되지 않았음
	- 그렇기 때문에 사실 `URI`와 `URL`을 같은 의미로 봐도 무방함  
<br/>

### URL - 전체 문법
- `scheme://[userinfo@]host[:port][/path][?query][#fragment]`
- ex) `https://www.google.com:443/search?q=hello&hl=ko`
	- `scheme` : 프로토콜(https)
	- `[userinfo@]` : 사용자정보 포함 인증(거의 사용하지 않음)
	- `host` : 호스트명(www.google.com)
	- `[:port]` : 포트 번호(443)
	- `[/path]` : 패스(/search)
	- `[?query][#fragment]` : 쿼리 파라미터(q=hello&hl=ko)  
<br/>

#### scheme
- 주로 프로토콜 사용
	- 프로토콜 : 어떤 방식으로 자원에 접근할 지에 대한 규칙(약속)
	- ex) http, https, frp 등
- `http`는 `80 포트`, `https`는 `443 포트`를 주로 사용
	-  위 둘을 사용하게 될 경우 포트는 생략이 가능함
- `https(HTTP Secure)`는 기존 `http`에 강력한 보안이 추가된 것  
<br/>

#### userinfo
- `URL`에 사용자정보를 포함해 인증
	- 거의 사용하지 않음  
<br/>

#### host
- 호스트명
- `도메인명` 또는 `IP 주소`를 직접 사용가능  
<br/>

#### PORT
- 포트(접속 포트)
- 일반적으로 사용시 생략(생략한 경우 `http`라면 80, `https`라면 443)  
<br/>

#### path
- 리소스 경로(계층적 구조)  
<br/>

#### query
- `key = value`의 형태
- `?`로 시작 `&`로 추가가능
- `query parameter` `query string` 등으로 불리며 웹서버에 제공하는 파라미터/문자 형태  
<br/>

#### fragment
- html 내부 북마크 등에 사용
- 서버에 전송하는 정보가 아님  
<br/><br/><br/>

## 02. 웹 브라우저 요청 흐름
1. `URL`입력
	- ex) https://www.google.com/search?q=hello&hl=ko
2. `DNS` 조회(`www.google.com`) - `IP`정보 획득
3. `PORT`조회(`https`의 경우 `443`) - `PORT`정보 획득
4. HTTP 요청 메시지 생성
__예시__
```
GET /search?q=hello&hl=ko HTTP/1.1
Host: www.google.com
```
5. HTTP 메시지 전송
	- 생성된 메시지를 `SOCKET`라이브러리를 통해 전달
		- A : TCP/IP 연결(IP, PORT)
		- B : 데이터 전달
	- `TCP/IP 패킷`생성 (HTTP 메시지 포함)
	- 네트워크 인터페이스를 통해 인터넷망으로 송출
6. 요청 패킷이 목적지 서버에 도착
	- 서버에서 패킷을 제거 `HTTP 메시지 확인`
	- 클라이언트에게 전달할 `HTTP 응답 메시지` 작성 후 전송
7. 클라이언트가 패킷 제거후 `HTTP 응답 메시지` 확인
	- 메시지 속 `HTML`코드를 렌더링
	- 우리가 아는 웹 검색 결과 화면이 출력됨!