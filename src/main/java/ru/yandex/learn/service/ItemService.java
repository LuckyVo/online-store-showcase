package ru.yandex.learn.service;


import ru.yandex.learn.db.entity.Item;
import ru.yandex.learn.utils.SortType;

import java.util.Map;

public interface ItemService {

    Item save(Item item);

    Map.Entry<Item, Integer> findById(Long id);

    Map<Item, Integer> search(String search, SortType sort, int pageNumber, int pageSize);

    Long count(String search);
}
