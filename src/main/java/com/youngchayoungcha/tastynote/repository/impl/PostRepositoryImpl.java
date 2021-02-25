package com.youngchayoungcha.tastynote.repository.impl;

import com.youngchayoungcha.tastynote.domain.Post;
import com.youngchayoungcha.tastynote.repository.AbstractCustomRepository;
import com.youngchayoungcha.tastynote.repository.PostCustomRepository;
import static com.youngchayoungcha.tastynote.domain.QPost.post;

import java.util.List;
import java.util.Optional;

public class PostRepositoryImpl extends AbstractCustomRepository implements PostCustomRepository {

    @Override
    public Optional<Post> findPost(Long postId) {
        return getQueryFactory().selectFrom(post).leftJoin(post.photos).fetchJoin().
                leftJoin(post.postTags).fetchJoin().
                where(post.id.eq(postId)).fetch().stream().findFirst();
    }

    @Override
    public List<Post> getPostList(int page, int size) {
        return getQueryFactory().selectFrom(post).leftJoin(post.photos).fetchJoin()
                .leftJoin(post.postTags).fetchJoin()
                .where(post.isPublic.eq(true))
                .orderBy(post.createdDateTime.desc()).offset((long) page * size).limit(size).fetch();
    }

    @Override
    public Long getPostNumByRestaurantId(String restaurantId) {
        return getQueryFactory().selectFrom(post).
                innerJoin(post.restaurant).
                where(post.restaurant.id.eq(restaurantId)).fetchCount();
    }

    @Override
    public Float getRestaurantScoreSum(String restaurantId) {
        return getQueryFactory().select(post.score.sum()).from(post)
                .innerJoin(post.restaurant)
                .where(post.restaurant.id.eq(restaurantId)).fetchFirst();
    }
}
