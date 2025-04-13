package ru.yandex.learn.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private List<ItemDto> items;
    private Double totalSum;

}