package com.youngchayoungcha.tastynote.exception;


public class InvalidParameterException extends RuntimeException{

    public InvalidParameterException(String parameterName) {
        super(String.format("Parameter %s is invalid", parameterName));
    }
}
