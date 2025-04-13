package ru.yandex.learn.test;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.learn.AbstractTest;
import ru.yandex.learn.data.TestData;
import ru.yandex.learn.service.OrderService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderServiceImplTest extends AbstractTest {


    @Autowired
    private OrderService orderService;


    @Test
    void testSaveOrder() {
        val order = orderService.save(TestData.getOrder());
        Assertions.assertAll(
                () -> assertNotNull(order),
                () -> assertEquals(1L, order.getId())
        );
    }

}
