package s1mple.dlowji.ffms_refactor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s1mple.dlowji.ffms_refactor.entities.Item;
import s1mple.dlowji.ffms_refactor.repositories.ItemRepository;
import s1mple.dlowji.ffms_refactor.services.ItemService;

@Service
public class IItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public boolean existsByNameIgnoreCase(String name) {
        return itemRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public Item findByName(String name) {
        return itemRepository.findByNameIgnoreCase(name);
    }
}
