package ru.yandex.learn.data;

import lombok.experimental.UtilityClass;
import ru.yandex.learn.db.entity.Item;
import ru.yandex.learn.db.entity.Order;
import ru.yandex.learn.utils.Status;

import java.time.LocalDateTime;

@UtilityClass
public class TestData {

    public static Item getItem(){
        var item = new Item();
        item.setTitle("tittle");
        item.setPrice(25.2d);
        item.setDescription("description");
        item.setImgPath("imgPath");
        return item;
    }

    public static Order getOrder(){
        var order = new Order();
        order.setStatus(Status.CURRENT);
        order.setCompleted(LocalDateTime.now());
        order.setCreated(LocalDateTime.now());
        return order;
    }
}
