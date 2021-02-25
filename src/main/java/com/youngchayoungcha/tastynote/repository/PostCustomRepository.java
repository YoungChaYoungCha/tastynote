package com.youngchayoungcha.tastynote.repository;

import com.youngchayoungcha.tastynote.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostCustomRepository {

    Optional<Post> findPost(Long postId);

    List<Post> getPostList(int page, int size);

    Long getPostNumByRestaurantId(String restaurantId);

    Float getRestaurantScoreSum(String restaurantId);
}
