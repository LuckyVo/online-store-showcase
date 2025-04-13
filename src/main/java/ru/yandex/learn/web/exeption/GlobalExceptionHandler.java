package ru.yandex.learn.web.exeption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void handleException(Throwable exc) {
        log.error("Ошибка обработки сообщения: {}", exc.getMessage());
    }
}
