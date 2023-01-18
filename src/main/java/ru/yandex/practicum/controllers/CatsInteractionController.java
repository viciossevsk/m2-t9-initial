package ru.yandex.practicum.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.Exceptions.IncorrectCountException;

import java.util.Map;

@RestController
@RequestMapping("/cats")
public class CatsInteractionController {
    private int happiness = 0;

    @GetMapping("/converse")
    public Map<String, String> converse() {
        happiness++;
        return Map.of("talk", "Мяу");
    }

    @GetMapping("/pet")
    public Map<String, String> pet(@RequestParam(required = false) final Integer count) {
        if (count == null) {
            throw new IncorrectCountException("Параметр count равен null.");
        }
        if (count <= 0) {
            throw new IncorrectCountException("Параметр count имеет отрицательное значение.");
        }
        happiness += count;
        return Map.of("talk", "Муррр. ".repeat(count));
    }

    @GetMapping("/happiness")
    public Map<String, Integer> happiness() {
        return Map.of("happiness", happiness);
    }

    // перечисление обрабатываемых исключений
    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
// в аргументах указывается родительское исключение
    public Map<String, String> handleIncorrectCount(final RuntimeException e) {
        return Map.of(
                "error", "Ошибка с параметром count.",
                "errorMessage", e.getMessage()
        );
    }

//    @ExceptionHandler
//// отлавливаем исключение IllegalArgumentException
//    public Map<String, String> handleNegativeCount(final IllegalArgumentException e) {
//        // возвращаем сообщение об ошибке
//        return Map.of("error", "Передан отрицательный параметр count.", "errorMessage", e.getMessage());
//    }
//    @ExceptionHandler
//    // добавьте сюда метод handleNullableCount по обработке NullPointerException
//    public Map<String, String> handleNoExistCount(final NullPointerException e) {
//        return Map.of("Ошибка", "Отсутствует значение count");
//    }

    @ExceptionHandler
    // добавьте сюда метод handleError по обработке RuntimeException
    public Map<String, String> handleRunTimeError(RuntimeException e) {
        return Map.of("error", "Произошла ошибка!");
    }

    @ExceptionHandler
    // добавьте сюда метод handleError по обработке RuntimeException
    public Map<String, String> handleIncorrectCount(IncorrectCountException e) {
        return Map.of("error", "Ошибка с параметром count.",
                      "errorMessage", e.getMessage());
    }
}