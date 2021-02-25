package com.youngchayoungcha.tastynote.web.dto;

import com.youngchayoungcha.tastynote.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostResponseDTO {

    private Long id;

    private String writerNickName;

    private String title;

    private String content;

    private Float score;

    private boolean isPublic;

    private List<PhotoResponseDTO> photos = new ArrayList<>();

    private List<TagResponseDTO> tags = new ArrayList<>();

    private RestaurantDTO restaurant;

    private LocalDateTime createdDateTime;

    public static PostResponseDTO fromEntity(Post post) {
        PostResponseDTO postDTO = new PostResponseDTO();
        postDTO.id = post.getId();
        postDTO.writerNickName = post.getNote().getMember().getName();
        postDTO.content = post.getContent();
        postDTO.title = post.getTitle();
        postDTO.score = post.getScore();
        postDTO.isPublic = post.isPublic();
        postDTO.createdDateTime = post.getCreatedDateTime();
        postDTO.photos = post.getPhotos().stream().map(PhotoResponseDTO::fromEntity).collect(Collectors.toList());
        postDTO.tags = post.getPostTags().stream().map(postTag -> TagResponseDTO.fromEntity(postTag.getTag())).collect(Collectors.toList());
        postDTO.restaurant = RestaurantDTO.fromEntity(post.getRestaurant());
        return postDTO;
    }
}
