# 섹션 04. MVC 프레임워크 만들기
## 01. 프론트 컨트롤러 패턴 소개
### 프론트 컨트롤러 도입 전
![img_001.jpg](img/img_001.jpg)  
<br/>

### 프론트 컨트롤러 도입 후
![img_002.jpg](img/img_002.jpg)  
<br/>

### FrontController 패턴 특징
- 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받음
  - 요청에 맞는 컨트롤러를 찾아서 호출해준다.
- 공통 처리 가능
- 프론트 컨트롤러를 제외한 나머지 컨트롤러들은 서블릿을 사용하지 않아도 된다.  
<br/>

### 스프링 웹 MVC와 프론트 컨트롤러
- 스프링 웹 MVC의 핵심이 `FrontController`이다.
- 스프링 웹 MVC의 `DispatcherServlet`이 프론트 컨트롤러 패턴으로 구현되어 있다.  
<br/><br/><br/>

## 02. 프론트 컨트롤러 도입 - v1
### v1 구조
![img_003.jpg](img/img_003.jpg)  
<br/><br/><br/>

## 03. View 분리 - v2
### v2 구조
![img_004.jpg](img/img_004.jpg)  
<br/><br/><br/>

## 04. Model 추가 - v3
### v3 구조
![img_005.jpg](img/img_005.jpg)
