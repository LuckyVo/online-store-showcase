package ru.yandex.learn.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Long id;
    private String title;
    private String description;
    private String imgPath;
    private Integer count;
    private Integer price;

}