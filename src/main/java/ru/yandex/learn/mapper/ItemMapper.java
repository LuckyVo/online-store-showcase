package ru.yandex.learn.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.learn.dto.ItemDto;
import ru.yandex.learn.db.entity.Item;

@Mapper(componentModel = "spring")
public abstract class ItemMapper {

    @Mapping(target = "count", source = "count")
    public abstract ItemDto toDto(Item item, int count);
}
