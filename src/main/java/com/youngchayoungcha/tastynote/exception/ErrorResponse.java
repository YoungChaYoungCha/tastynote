package com.youngchayoungcha.tastynote.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private LocalDateTime timestamp = LocalDateTime.now();

    private String message;

    private int status;

    static public ErrorResponse create(){
        return new ErrorResponse();
    }

    public ErrorResponse message(String message) {
        this.message = message;
        return this;
    }

    public ErrorResponse status(int status) {
        this.status = status;
        return this;
    }

}
