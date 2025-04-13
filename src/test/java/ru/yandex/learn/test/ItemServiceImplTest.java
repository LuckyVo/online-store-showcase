package ru.yandex.learn.test;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.learn.AbstractTest;
import ru.yandex.learn.data.TestData;
import ru.yandex.learn.service.impl.ItemServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ItemServiceImplTest extends AbstractTest {

    @Autowired
    private ItemServiceImpl itemService;


    @Test
    void saveItemTest() {
        val item = itemService.save(TestData.getItem());
        Assertions.assertAll(
                () -> assertNotNull(item),
                () -> assertEquals(1L, item.getId())
        );
    }

}