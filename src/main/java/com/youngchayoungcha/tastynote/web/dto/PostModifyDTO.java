package com.youngchayoungcha.tastynote.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostModifyDTO {

    private Long postId;

    private String title;

    private String content;

    private Float score;

    private boolean isPublic;

    private List<PhotoRequestDTO> newPhotos = new ArrayList<>();

    private List<Long> deletedPhotoIds = new ArrayList<>();

    private List<TagModifyDTO> tagEvents = new ArrayList<>();
}
