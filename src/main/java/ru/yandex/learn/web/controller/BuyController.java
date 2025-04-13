package ru.yandex.learn.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.learn.service.OrderService;

@RequiredArgsConstructor
@RequestMapping("/buy")
@RestController
public class BuyController {

    private final OrderService orderService;

    @PostMapping
    public String buy() {
        val order = orderService.completeCurrentOrder();
        return "redirect:/orders/" + order.getId() +
                "?" +
                "newOrder=" + true;
    }
}
