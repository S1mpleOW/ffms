package s1mple.dlowji.ffms_refactor.services;

import s1mple.dlowji.ffms_refactor.entities.Item;

public interface ItemService {
    Item save(Item item);

    boolean existsByNameIgnoreCase(String name);

    Item findByName(String name);
}
