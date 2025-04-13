package ru.yandex.learn.service.impl;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.yandex.learn.db.entity.Item;
import ru.yandex.learn.db.entity.Order;
import ru.yandex.learn.db.entity.OrderItem;
import ru.yandex.learn.db.repository.ItemRepository;
import ru.yandex.learn.db.repository.OrderRepository;
import ru.yandex.learn.service.OrderService;
import ru.yandex.learn.utils.Action;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static ru.yandex.learn.utils.Status.COMPLETED;
import static ru.yandex.learn.utils.Status.CURRENT;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Override
    @NonNull
    public Order save(@NonNull Order order) {
        order.setCreated(LocalDateTime.now());
        order.setCompleted(LocalDateTime.now());
        return orderRepository
                .save(order);
    }

    @Override
    @NonNull
    public List<Order> findAllCompletedOrders() {
        return orderRepository
                .findByStatus(COMPLETED);
    }

    @Override
    @NonNull
    public Order findById(@NonNull Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Не удалось найти order c id: %d".formatted(id)));
    }

    @Override
    @NonNull
    public Order getCurrentOrder() {
        return orderRepository
                .findByStatus(CURRENT)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Не удалось найти текущий order"));
    }

    @Override
    @NonNull
    public Map<Item, Integer> getItemsFromOrder(@NonNull Order order) {
        return order
                .getOrderItems()
                .stream()
                .collect(
                        Collectors
                                .toMap(
                                        OrderItem::getItem,
                                        OrderItem::getCount,
                                        (existing, replacement) -> replacement,
                                        LinkedHashMap::new)
                );
    }

    @Override
    @NonNull
    public Double calcPrice(@NonNull Order order) {
        AtomicReference<Double> price = new AtomicReference<>((double) 0);
        order
                .getOrderItems()
                .forEach(orderItem -> {
                    Double itemPrice = orderItem.getItem().getPrice();
                    int count = orderItem.getCount();
                    price.updateAndGet(v -> (v + itemPrice) * count);
                });

        return price.get();
    }

    @Override
    @NonNull
    public Order completeCurrentOrder() {
        var currentOrder = getCurrentOrder();
        currentOrder.setStatus(COMPLETED);
        currentOrder.setCompleted(LocalDateTime.now());
        orderRepository.save(currentOrder);
        return orderRepository.save(new Order());
    }

    @Override
    public void changeItemCountInCart(@NonNull Long id,
                                      @NonNull Action action) {
        val item = itemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Не удалось найти item c id: %d".formatted(id)));
        val currentOrder = getCurrentOrder();
        val orderItem = currentOrder
                .getOrderItems()
                .stream()
                .filter(it -> it.getItem().equals(item))
                .findFirst();
        switch (action) {
            case PLUS -> {
                if (orderItem.isPresent()) {
                    orderItem
                            .get()
                            .setCount(orderItem.get().getCount() + 1);
                } else {
                    currentOrder
                            .getOrderItems()
                            .add(new OrderItem(currentOrder, item));
                }
            }
            case MINUS -> orderItem.ifPresent(value -> {
                        if (value.getCount() > 0) {
                            value.setCount(value.getCount() - 1);
                        }
                    }
            );
            case DELETE -> orderItem.ifPresent(value -> currentOrder.getOrderItems().remove(value));
        }

        orderRepository.save(currentOrder);
    }
}
