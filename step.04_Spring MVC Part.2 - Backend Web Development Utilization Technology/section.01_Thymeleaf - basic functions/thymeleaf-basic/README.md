# 섹션 01. 타임리프 - 기본 기능
## 01. 타임리프(Thymeleaf) 소개
- 공식 사이트: https://www.thymeleaf.org/
- 공식 메뉴얼 - 기본기능: https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html
- 공식 메뉴얼 - 스프링 통합: https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html  
<br/>

### 타임리프 특징
- 서버 사이드 HTML 렌더링 (SSR)
  - 타임리프는 백엔드 서버에서 `HTML을 동적으로 렌더링` 하는 용도로 사용됨
- 네츄럴 템플릿
  - 타임리프는` 순수 HTML`을 최대한 유지하는 특징이 있음
  - 타임리프로 작성한 파일은 `HTML을 유지`하기에 웹 브라우저에서 파일을 직업 열어 내용확인이 가능함
    - 서버를 통해 뷰 템플릿을 거쳐 동적으로 변경된 결과도 확인 가능
  - `순수 HTML을 그대로 유지`하면서 뷰 템플릿도 사용할 수 있음
    - 이런 특징을 `네츄럴 템플릿(natural templates)`이라고 함
- 스프링 통합 지원
  - 타임리프는 스프링과 자연스럽게 통합되고 스프링의 다양한 기능을 편리하게 사용할 수 있도록 지원함  
<br/>

### 타임리프 기본 기능
- 타임리프를 사용하려면 다음 선언을 하면 됨
#### 타임리프 사용 선언
```html
<html xmlns:th="http://www.thymeleaf.org">
```
#### 기본 표현식
```
• 간단한 표현:
    ◦ 변수 표현식: ${...}
    ◦ 선택 변수 표현식: *{...}
    ◦ 메시지 표현식: #{...}
    ◦ 링크 URL 표현식: @{...}
    ◦ 조각 표현식: ~{...}
• 리터럴
    ◦ 텍스트: 'one text', 'Another one!',…
    ◦ 숫자: 0, 34, 3.0, 12.3,…
    ◦ 불린: true, false
    ◦ 널: null
    ◦ 리터럴 토큰: one, sometext, main,…
• 문자 연산:
    ◦ 문자 합치기: +
    ◦ 리터럴 대체: |The name is ${name}|
• 산술 연산:
    ◦ Binary operators: +, -, *, /, %
    ◦ Minus sign (unary operator): -
• 불린 연산:
    ◦ Binary operators: and, or
    ◦ Boolean negation (unary operator): !, not
• 비교와 동등:
    ◦ 비교: >, <, >=, <= (gt, lt, ge, le)
    ◦ 동등 연산: ==, != (eq, ne)
• 조건 연산:
    ◦ If-then: (if) ? (then)
    ◦ If-then-else: (if) ? (then) : (else)
    ◦ Default: (value) ?: (defaultvalue)
• 특별한 토큰:
    ◦ No-Operation: _
```
- 참고: https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#standard-expression-syntax