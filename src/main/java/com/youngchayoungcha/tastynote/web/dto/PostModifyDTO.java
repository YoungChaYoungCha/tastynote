package com.youngchayoungcha.tastynote.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostModifyDTO {

    @NotEmpty(message = "노트 ID는 반드시 명시되어야합니다.")
    private Long postId;

    @NotEmpty(message = "제목은 비어있지 않아야 합니다.")
    private String title;

    private String content;

    @Min(value = 0, message = "점수는 0보다 커야합니다.")
    @Max(value = 5, message = "점수는 5보다 같거나 작아야합니다.")
    private Float score;

    @NotEmpty(message = "공개 여부는 선택되어야 합니다.")
    private boolean isPublic;

    private List<PhotoRequestDTO> newPhotos = new ArrayList<>();

    private List<Long> deletedPhotoIds = new ArrayList<>();

    private List<TagModifyDTO> tagEvents = new ArrayList<>();
}
