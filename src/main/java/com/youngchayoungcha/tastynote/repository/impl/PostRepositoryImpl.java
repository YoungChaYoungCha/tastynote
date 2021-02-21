package com.youngchayoungcha.tastynote.repository.impl;

import com.youngchayoungcha.tastynote.domain.Post;
import com.youngchayoungcha.tastynote.repository.AbstractCustomRepository;
import com.youngchayoungcha.tastynote.repository.PostCustomRepository;
import static com.youngchayoungcha.tastynote.domain.QPost.post;

import java.util.Optional;

public class PostRepositoryImpl extends AbstractCustomRepository implements PostCustomRepository {

    @Override
    public Optional<Post> findPost(Long postId) {
        return getQueryFactory().selectFrom(post).leftJoin(post.photos).fetchJoin().
                leftJoin(post.postTags).fetchJoin().
                where(post.id.eq(postId)).fetch().stream().findFirst();
    }
}
