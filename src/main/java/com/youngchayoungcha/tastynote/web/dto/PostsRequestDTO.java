package com.youngchayoungcha.tastynote.web.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsRequestDTO {

    private String filter;
    private int page;
    private int size;
}
