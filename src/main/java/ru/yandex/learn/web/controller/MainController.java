package ru.yandex.learn.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.learn.dto.PagingDto;
import ru.yandex.learn.mapper.ItemMapper;
import ru.yandex.learn.service.ItemService;
import ru.yandex.learn.service.OrderService;
import ru.yandex.learn.utils.Action;
import ru.yandex.learn.utils.SortType;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/main")
@Slf4j
public class MainController {

    private final ItemService itemService;
    private final OrderService orderService;
    private final ItemMapper itemMapper;


    @GetMapping("/items")
    public String getItems(@RequestParam(defaultValue = "") String search,
                           @RequestParam(defaultValue = "NO") SortType sort,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam(defaultValue = "1") Integer pageNumber,
                           Model model) {
        val itemsDto = itemService
                .search(search, sort, pageNumber - 1, pageSize)
                .entrySet()
                .stream()
                .map(it -> itemMapper.toDto(it.getKey(), it.getValue()))
                .toList();
        val pagingDto = new PagingDto(
                pageNumber,
                pageSize,
                (long) pageNumber * pageSize < itemService.count(search),
                pageNumber != 1);
        model.addAttribute("items", itemsDto);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort.name());
        model.addAttribute("paging", pagingDto);
        return "main";
    }

    @PostMapping("/items/{id}")
    public String changeItemCountInCart(@PathVariable long id,
                                        @RequestParam Action action,
                                        @RequestParam String search,
                                        @RequestParam SortType sort,
                                        @RequestParam Integer pageSize,
                                        @RequestParam Integer pageNumber) {
        orderService.changeItemCountInCart(id, action);
        return "redirect:/main/items" +
                "?" +
                "search=" + search + "&" +
                "sort=" + sort + "&" +
                "pageSize=" + pageSize + "&" +
                "pageNumber=" + pageNumber;
    }
}
