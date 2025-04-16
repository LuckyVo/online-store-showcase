package ru.yandex.learn.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.learn.dto.CartDto;
import ru.yandex.learn.mapper.ItemMapper;
import ru.yandex.learn.service.OrderService;
import ru.yandex.learn.utils.Action;

@RequiredArgsConstructor
@RequestMapping(value = "/cart")
@Controller
public class CartController {

    private final OrderService orderService;

    private final ItemMapper itemMapper;

    @GetMapping("/items")
    public String getCart(Model model) {
        val currentOrder = orderService.getCurrentOrder();
        val items = orderService
                .getItemsFromOrder(currentOrder)
                .entrySet()
                .stream()
                .map(it -> itemMapper.toDto(it.getKey(), it.getValue()))
                .toList();
        val cartDto = CartDto.builder()
                .items(items)
                .total(orderService.calcPrice(currentOrder))
                .empty(items.isEmpty())
                .build();
        model.addAttribute("items", cartDto.getItems());
        model.addAttribute("total", cartDto.getTotal());
        model.addAttribute("empty", cartDto.getEmpty());
        return "cart";
    }

    @PostMapping("/items/{id}")
    public String changeItemCountInCart(@PathVariable long id,
                                        @RequestParam Action action) {
        orderService.changeItemCountInCart(id, action);
        return "redirect:/cart/items";
    }
}
