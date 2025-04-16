package ru.yandex.learn.dto;


public record PagingDto(int pageNumber, int pageSize, boolean hasNext, boolean hasPrevious) {
}