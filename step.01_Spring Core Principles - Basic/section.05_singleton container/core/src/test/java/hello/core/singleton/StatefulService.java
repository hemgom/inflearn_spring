package hello.core.singleton;

public class StatefulService {

    private int price;  // 상태를 유지하는 필드
                        // 10000원 --변경--> 20000원
                        // 문제해결을 위해 지워야함
    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price = price; // 이 부분이 문제!
        // 위 코드를 지우고 `order()`의 반환타입을 `int`로 바꾸어 `price`를 반환하도록 한다.
    }

    // 문제해결을 위해 아래의 코드도 지워준다.
    public int getPrice() {
        return price;
    }
}
