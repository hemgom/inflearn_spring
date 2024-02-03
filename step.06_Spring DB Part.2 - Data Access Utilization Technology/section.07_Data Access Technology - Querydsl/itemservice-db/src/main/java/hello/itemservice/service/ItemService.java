package hello.itemservice.service;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;

import java.util.List;
import java.util.Optional;

// 추후에 서비스 구현체가 변경될 수 있음을 염두해 인터페이스를 사용 -> 실무에서는 구현체 변경이 많지는 않음
public interface ItemService {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findItems(ItemSearchCond itemSearch);
}
