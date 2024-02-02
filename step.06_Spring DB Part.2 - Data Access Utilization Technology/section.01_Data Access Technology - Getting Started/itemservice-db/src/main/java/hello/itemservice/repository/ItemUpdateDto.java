package hello.itemservice.repository;

import lombok.Data;

// 상품 수정 : 단순 데이터 전달용 객체로 `Dto`로 사용
@Data
public class ItemUpdateDto {
    private String itemName;
    private Integer price;
    private Integer quantity;

    public ItemUpdateDto() {
    }

    public ItemUpdateDto(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
