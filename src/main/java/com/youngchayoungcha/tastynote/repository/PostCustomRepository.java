package com.youngchayoungcha.tastynote.repository;

import com.youngchayoungcha.tastynote.domain.Post;

import java.util.Optional;

public interface PostCustomRepository {

    Optional<Post> findPost(Long postId);
}
