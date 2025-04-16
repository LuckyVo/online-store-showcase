package ru.yandex.learn.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.learn.dto.OrderDto;
import ru.yandex.learn.mapper.ItemMapper;
import ru.yandex.learn.service.OrderService;


@RequiredArgsConstructor
@RequestMapping("/orders")
@Controller
public class OrderController {

    private final OrderService orderService;
    private final ItemMapper itemMapper;

    @GetMapping
    public String getOrders(Model model) {
        val ordersDto = orderService
                .findAllCompletedOrders()
                .stream()
                .map(order ->
                        OrderDto.builder()
                                .id(order.getId())
                                .items(orderService
                                        .getItemsFromOrder(order)
                                        .entrySet()
                                        .stream()
                                        .map(it -> itemMapper.toDto(it.getKey(), it.getValue()))
                                        .toList())
                                .totalSum(orderService.calcPrice(order))
                                .build())
                .toList();
        model.addAttribute("orders", ordersDto);
        return "orders";
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable long id,
                           @RequestParam(defaultValue = "false") boolean newOrder,
                           Model model) {
        val order = orderService.findById(id);
        val orderDto = OrderDto.builder()
                .id(order.getId())
                .items(orderService
                        .getItemsFromOrder(order)
                        .entrySet()
                        .stream()
                        .map(it -> itemMapper.toDto(it.getKey(), it.getValue()))
                        .toList())
                .totalSum(orderService.calcPrice(order))
                .build();
        model.addAttribute("order", orderDto);
        model.addAttribute("newOrder", newOrder);
        return "order";
    }

}
