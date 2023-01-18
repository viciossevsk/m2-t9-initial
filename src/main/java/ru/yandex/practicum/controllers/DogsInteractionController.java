package ru.yandex.practicum.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.Exceptions.IncorrectCountException;

import java.util.Map;

@RestController
@RequestMapping("/dogs")
public class DogsInteractionController {
    private int happiness = 0;

    @GetMapping("/converse")
    public Map<String, String> converse() {
        happiness += 2;
        return Map.of("talk", "Гав!");
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
        return Map.of("action", "Вильнул хвостом. ".repeat(count));
    }

    @GetMapping("/happiness")
    public Map<String, Integer> happiness() {
        return Map.of("happiness", happiness);
    }

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
