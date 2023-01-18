package ru.yandex.practicum.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.Exceptions.ErrorResponse;
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

//    @ExceptionHandler
//    // добавьте сюда метод handleError по обработке RuntimeException
//    public Map<String, String> handleRunTimeError(RuntimeException e) {
//        return Map.of("error", "Произошла ошибка!");
//    }

//    @ExceptionHandler
//    public Map<String, String> handleIncorrectCount(IncorrectCountException e) {
//        return Map.of("error", "Ошибка с параметром count.",
//                      "errorMessage", e.getMessage());
//    }


    /***
     * Частая практика — создание специального объекта для универсального формата ошибки
     * Меняем код ответа через @ResponseStatus(HttpStatus.BAD_REQUEST)
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(final IncorrectCountException e) {
        return new ErrorResponse("error", "Ошибка с параметром count.");
    }


    /***
     * Существует ещё один способ быстро вернуть необходимый http-код при ошибке в приложении — с помощью исключения
     * ResponseStatusException (англ. «исключение статуса ответа»). Это программный интерфейс к аннотации
     * @ResponseStatus и базовый класс для исключений, который используется для применения статус-кода к http-ответу.
     * Так как ResponseStatusException — непроверяемое исключение, в сигнатуру функции его описание добавлять не нужно.
     * Например, если программист начал реализовывать какой-то метод, но не дописал его до конца, он может
     * сгенерировать в нём исключение ResponseStatusException с кодом возврата 501 — «метод не реализован».
     *
     * В нём не фигурирует переданное дополнительное сообщение (reason). Это нужно настраивать отдельно:
     * установить в application.properties параметр server.error.include-message=always. После чего
     * ответ программы станет таким:
     * "message":"Happiness not implemented",
     */
    @GetMapping("/feed")
    public Map<String, Integer> feed() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Метод /feed ещё не реализован.");
    }

}