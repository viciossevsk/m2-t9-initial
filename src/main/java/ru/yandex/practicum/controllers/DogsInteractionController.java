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

    /***
     * Существует ещё один способ быстро вернуть необходимый http-код при ошибке в приложении — с помощью исключения
     * ResponseStatusException (англ. «исключение статуса ответа»). Это программный интерфейс к аннотации
     * @ResponseStatus и базовый класс для исключений, который используется для применения статус-кода к http-ответу.
     * Так как ResponseStatusException — непроверяемое исключение, в сигнатуру функции его описание добавлять не нужно.
     * Например, если программист начал реализовывать какой-то метод, но не дописал его до конца, он может
     * сгенерировать в нём исключение ResponseStatusException с кодом возврата 501 — «метод не реализован».
     */
//    @GetMapping("/feed")
//    public Map<String, Integer> feed() {
//        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Метод /feed ещё не реализован.");
//    }

}
