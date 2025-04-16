package ru.yandex.learn.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(value = "/")
@Controller
public class HomeController {

    @GetMapping
    public String home() {
        return "redirect:main/items";
    }
}
