package ru.yandex.learn.service;


import ru.yandex.learn.db.entity.Item;
import ru.yandex.learn.db.entity.Order;
import ru.yandex.learn.utils.Action;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Order save(Order order);

    List<Order> findAllCompletedOrders();

    Order findById(Long id);

    Order getCurrentOrder();

    Map<Item, Integer> getItemsFromOrder(Order order);

    Double calcPrice(Order order);

    Order completeCurrentOrder();

    void changeItemCountInCart(Long id, Action action);
}
