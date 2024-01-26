# 섹션 09. API 예외 처리
## 02. 스프링 부트 기본 오류 처리
API 예외 처리도 스프링 부트가 제공하는 기본 오류 방식을 사용할 수 있음  
<br/>

#### BasicErrorController 코드
```java
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {}

@RequestMapping
public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {}
```
- `errorHtml()`: `produces = MediaType.TEXT_HTML_VALUE`: 클라이언트 요청의 Accept 헤더 값이 `text/html`일 경우 `errorHtml()`을 호출해 view를 제공함
- `error()`: 그 외의 경우에 호출됨, `ResponseEntity`로 HTTP Body에 JSON 데이터를 반환함  
<br/>

### 스프링 부트의 예외 처리
스프링 부트의 기본 설정은 오류 발생시 `/error`를 오류 페이지로 요청함<br/>
`BasicErrorController`는 해당 경로를 기본을 받음(`server.error.path`로 수정 가능, 기본 경로 `/error`)
- 스프링 부트는 `BasicErrorController`가 제공하는 기본 정보를 활용해 `오류 API`를 생성해줌
- 아래 옵션을 설정하면 더 상세한 오류 정보를 추가할 수 있음
  - server.error.include-binding-errors=always
  - server.error.include-exception=true
  - server.error.include-message=always
  - server.error.include-stacktrace=always