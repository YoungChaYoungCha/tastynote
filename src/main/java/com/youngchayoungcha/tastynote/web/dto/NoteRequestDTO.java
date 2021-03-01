package com.youngchayoungcha.tastynote.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class NoteRequestDTO {

    @NotEmpty(message = "제목은 반드시 존재해야합니다.")
    private String title;
}
