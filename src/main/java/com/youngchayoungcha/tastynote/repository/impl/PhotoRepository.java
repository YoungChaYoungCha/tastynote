package com.youngchayoungcha.tastynote.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.youngchayoungcha.tastynote.domain.QPhoto.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class PhotoRepository {

    private final JPAQueryFactory factory;

    public List<String> getPhotoUrlsByIds(List<Long> ids){
        return factory.select(photo.url).from(photo).where(photo.id.in(ids)).fetch();
    }

}
