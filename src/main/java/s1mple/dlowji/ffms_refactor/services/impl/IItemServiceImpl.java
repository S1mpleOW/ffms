package s1mple.dlowji.ffms_refactor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s1mple.dlowji.ffms_refactor.entities.Item;
import s1mple.dlowji.ffms_refactor.entities.Services;
import s1mple.dlowji.ffms_refactor.repositories.ItemRepository;
import s1mple.dlowji.ffms_refactor.repositories.ServicesRepository;
import s1mple.dlowji.ffms_refactor.services.ItemService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item findById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if(optionalItem.isEmpty() && !optionalItem.isPresent())
            return null;
        return optionalItem.get();
    }

    @Override
    public boolean existsById(Long id) {
        return itemRepository.existsById(id);
    }

    public boolean existsByNameIgnoreCase(String name) {
        return itemRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public Item findByName(String name) {
        return itemRepository.findByNameIgnoreCase(name);
    }

    @Override
    public int findSellPrice(Long id) {
        List<Services> services = servicesRepository.findAll();
        for(Services s : services) {
            if(s.getItems().stream().filter(i -> i.getId() == id).count() != 0) {
                return s.getPriceSell();
            }
        }
        return 0;
    }
}
