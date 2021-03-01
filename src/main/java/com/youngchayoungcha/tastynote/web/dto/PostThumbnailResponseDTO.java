package com.youngchayoungcha.tastynote.web.dto;

import com.youngchayoungcha.tastynote.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostThumbnailResponseDTO {

    private Long id;
    private String title;
    private String imageUrl;
    private LocalDateTime createdDateTime;
    private List<String> tags;

    public static PostThumbnailResponseDTO fromEntity(Post post){
        PostThumbnailResponseDTO dto = new PostThumbnailResponseDTO();
        dto.id = post.getId();
        dto.title = post.getTitle();
        dto.imageUrl = post.getPhotos().get(0).getUrl();
        dto.createdDateTime = post.getCreatedDateTime();
        dto.tags = post.getPostTags().stream().map(postTag -> postTag.getTag().getName()).collect(Collectors.toList());
        return dto;
    }
}
