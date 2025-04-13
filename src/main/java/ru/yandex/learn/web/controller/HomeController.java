package ru.yandex.learn.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping(value = "/")
@RestController
public class HomeController {

    @GetMapping
    public String home() {
        return "redirect:main/items";
    }
}
