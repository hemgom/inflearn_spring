# 섹션 03. 메시지, 국제화
## 02. 메시지, 국제화 소개
### 메시지
특정 문구의 수정 요청이 온다면 어떻게 해야할까?<br/>
여러 화면에 보이는 단어들을 수정하려면 화면을 하나하나 찾아가며 모두 변경해야하는 경우가 생길 것이다.<br/>
이러한 이유는 바로 HTML 파일에 메시지가 `하드코딩` 되어 있기 때문이다.<br/>
위와 같은 상황에 편리하게 메시지 수정을 위해 한 곳에서 메시지를 관리하는 기능을 `메시지 기능`이라 한다.  
```
item=상품
item.id=상품 ID
item.itemName=상품명
item.price=가격
item.quantity=수량
```
예를 들어 위와 같이 `messages.properties`라는 관리 파일을 만들고 각 HTML들을 아래와 같이 key 값으로 불러 사용한다.
- `addForm.html`: `<label for="itemName" th:text="#{item.itemName}"></label>`
- `editForm.html`: `<label for="itemName" th:text="#{item.itemName}"></label>`  
<br/>

### 국제화
한 발 더 나아가서 `메시지 파일(messages.properties)`을 각 나라별로 별도로 관리하면 서비스를 국제화 할 수 있다.
```
//messages_en.properties -> 영어
item=Item
item.id=Item ID
item.itemName=Item Name
item.price=price
item.quantity=quantity
```
```
//messages_ko.properties -> 한국어
item=상품
item.id=상품 ID
item.itemName=상품명
item.price=가격
item.quantity=수량
```
위처럼 한국어, 영어 2개의 파일을 만들어 분류해 특정 언어에 맞게 메시지 관리 파일을 사용하게 개발한다.<br/>
어떤 국가에서 접근하였는지 인식하는 방법은 `HTTP accept-language`해더 값을 사용하거나 사용자가 직접 선택, 또는 쿠키 등을 사용한다.<br/>
스프링은 기본적인 메시지와 국제화 기능 모두를 제공하며 타임리프 또한 해당 기능들은 통합해 제공한다.