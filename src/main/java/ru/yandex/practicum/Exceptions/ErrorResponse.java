package ru.yandex.practicum.Exceptions;

public class ErrorResponse {

    private String error;
    private String description;

    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }

    public String getError() {
        return this.error;
    }

    public String getDescription() {
        return this.description;
    }

}
