package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
// 아래의 방식을 제약이 많고 복잡함, 실무에서는 검증 기능이 객체의 범위를 넘어서는 경우가 종종 있는데, 이를 대응하기 어려움
// 사실상 오브젝트 오류 관련 부분은 직접 자바 코드로 작성하는 것을 권장하는 편
//@ScriptAssert(lang = "javascript", script = "_this.price * _this.quantity >= 10000", message = "총합이 10,000원이 넘도록 입력해주세요.")
public class Item {

    private Long id;

    @NotBlank(message = "공백X")   // 빈값 + 공백만 있는 경우를 허용하지 않음
    private String itemName;

    @NotNull    // null 값을 허용하지 않음
    @Range(min = 1000, max = 1000000)   // 지정한 최소, 최대 값 범위 안의 값만 허용
    private Integer price;

    @NotNull
    @Max(9999)  // 지정한 최대 값까지만 허용
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
