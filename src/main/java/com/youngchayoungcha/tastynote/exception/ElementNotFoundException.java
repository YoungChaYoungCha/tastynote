package com.youngchayoungcha.tastynote.exception;

// Rest API 에서 Path variable로 들어온 id값에 대해 Entity가 없을 때
public class ElementNotFoundException extends RuntimeException{

    public static final int ELEMENT_EMAIL = 0;
    public static final int ELEMENT_TOKEN = 1;

    public ElementNotFoundException(Long id) {
        super(String.format("Entity with Id %d not found", id));
    }

    public ElementNotFoundException(String str, int num) {
        switch (num) {
            case ELEMENT_EMAIL:
                String.format("Entity with Email {} not found", str);
            case ELEMENT_TOKEN:
                String.format("Entity with Refresh Token not found");
            default:
                String.format("Entity not found");
        }
    }

}
