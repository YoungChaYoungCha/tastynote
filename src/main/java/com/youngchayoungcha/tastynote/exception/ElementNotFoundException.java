package com.youngchayoungcha.tastynote.exception;

// Rest API 에서 Path variable로 들어온 id값에 대해 Entity가 없을 때
public class ElementNotFoundException extends RuntimeException{

    public ElementNotFoundException(Long id) {
        super(String.format("Entity with Id %d not found", id));
    }
}
