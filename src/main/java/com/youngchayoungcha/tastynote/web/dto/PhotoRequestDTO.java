package com.youngchayoungcha.tastynote.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoRequestDTO {

    @NotEmpty(message = "사진 파일은 반드시 존재해야 합니다.")
    private MultipartFile file;
    private String comment;
}
