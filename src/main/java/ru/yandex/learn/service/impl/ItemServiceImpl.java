package ru.yandex.learn.service.impl;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.learn.db.entity.Item;
import ru.yandex.learn.db.entity.OrderItem;
import ru.yandex.learn.db.repository.ItemRepository;
import ru.yandex.learn.service.ItemService;
import ru.yandex.learn.utils.SortType;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static ru.yandex.learn.utils.Status.CURRENT;


@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    @NonNull
    public Item save(@NonNull Item item) {
        return itemRepository.save(item);
    }

    @Override
    @NonNull
    public Entry<Item, Integer> findById(@NonNull Long id) {
        var item = itemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Не удалось найти item c id: %d".formatted(id)));
        Integer count = item
                .getOrderItems()
                .stream()
                .filter(it -> it.getOrder().getStatus().equals(CURRENT))
                .map(OrderItem::getCount)
                .findFirst()
                .orElseGet(() -> 0);
        return new SimpleEntry<>(item, count);
    }

    @Override
    public Map<Item, Integer> search(String search, SortType sort, int pageNumber, int pageSize) {
        val pageRequest = switch (sort) {
            case NO -> PageRequest.of(pageNumber, pageSize);
            case TITLE -> PageRequest.of(pageNumber, pageSize, Sort.by("title").ascending());
            case PRICE -> PageRequest.of(pageNumber, pageSize, Sort.by("price").ascending());
        };
        val items = search.isEmpty() ?
                itemRepository.findAll(pageRequest).getContent() :
                itemRepository.search(search, pageRequest);
        return items.stream()
                .collect(Collectors.toMap(
                        item -> item,
                        item -> item
                                .getOrderItems()
                                .stream()
                                .filter(it -> it.getOrder().getStatus().equals(CURRENT))
                                .map(OrderItem::getCount)
                                .findFirst()
                                .orElseGet(() -> 0),
                        (existing, replacement) -> replacement,
                        LinkedHashMap::new
                ));
    }

    @Override
    public Long count(@NonNull String search) {
        return search.isEmpty() ?
                itemRepository.count() :
                itemRepository.countBySearch(search);
    }
}
