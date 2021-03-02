package com.youngchayoungcha.tastynote.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractCustomRepository {

    @Autowired
    private JPAQueryFactory queryFactory;

    public JPAQueryFactory getQueryFactory() {
        return queryFactory;
    }
}
