# 섹션 03. 스프링 핵심 원리 이해(2) - 객체 지향 원리 적용
### 01. 새로운 할인 정책 개발
- `참고(1)` : [애자일 소프트웨어 개발 선언](https://agilemanifesto.org/iso/ko/manifesto.html)  
<br/>

- __RateDiscountPolicy__ 추가
![img.png](img.png)  
<br/>

- `class RateDiscountPolicy` : 일정 %의 할인 금액을 반환한다.
- `class RateDiscountPolicyTest` : 회원의 등급이 `VIP`인지 아닌지에 따라 원하는 결과가 나오는지 확인한다.