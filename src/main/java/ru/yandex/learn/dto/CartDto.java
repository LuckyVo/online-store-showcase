package ru.yandex.learn.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private List<ItemDto> items;
    private Double total;
    private Boolean empty;

}