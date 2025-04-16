package ru.yandex.learn.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.learn.mapper.ItemMapper;
import ru.yandex.learn.service.ItemService;
import ru.yandex.learn.service.OrderService;
import ru.yandex.learn.utils.Action;


@RequiredArgsConstructor
@RequestMapping(value = "/items")
@Controller
public class ItemController {

    private final ItemService itemService;
    private final OrderService orderService;
    private final ItemMapper itemMapper;

    @GetMapping("/{id}")
    public String getItem(@PathVariable("id") long id,
                          Model model) {
        val item = itemService.findById(id);
        val itemDto = itemMapper.toDto(item.getKey(), item.getValue());
        model.addAttribute("item", itemDto);
        return "item";
    }

    @PostMapping("/{id}")
    public String changeItemCountInCart(@PathVariable Long id,
                                        @RequestParam Action action) {
        orderService.changeItemCountInCart(id, action);
        return "redirect:/items/" + id;
    }
}
