package hello.itemservice.domain.item;

import lombok.Data;

@Data   // 위헙 getter, setter가 필요하다면 해당 기능만 추가해서 사용하는 걸 추천, 예제라 안 좋은 경험(?)을 위해 일부러 설정
// @Getter @Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
