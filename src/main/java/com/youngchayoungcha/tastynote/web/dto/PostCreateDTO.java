package com.youngchayoungcha.tastynote.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDTO {

    private Long noteId;

    private String title;

    private String content;

    private short score;

    private boolean isPublic;

    private List<PhotoRequestDTO> photos = new ArrayList<>();

    private List<String> tags = new ArrayList<>();

    private RestaurantDTO restaurant;

}
