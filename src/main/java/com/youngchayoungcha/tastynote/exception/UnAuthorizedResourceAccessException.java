package com.youngchayoungcha.tastynote.exception;

public class UnAuthorizedResourceAccessException extends RuntimeException{

    public UnAuthorizedResourceAccessException(String entityName, Long entityId, Long memberId) {
        super(String.format("Member %d does not have access to Entity %s #%d", memberId, entityName, entityId));
    }
}
