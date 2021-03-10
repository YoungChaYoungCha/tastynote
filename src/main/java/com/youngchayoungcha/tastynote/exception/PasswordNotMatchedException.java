package com.youngchayoungcha.tastynote.exception;

public class PasswordNotMatchedException extends RuntimeException {

    public PasswordNotMatchedException() {
        super(String.format("Password is not matched"));
    }
}
