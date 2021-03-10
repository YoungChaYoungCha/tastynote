package com.youngchayoungcha.tastynote.exception;

public class CertifyNotCompleteException extends RuntimeException {
    public CertifyNotCompleteException(Long id) { super(String.format("Entity with Id %d is not certified", id)); }
}
