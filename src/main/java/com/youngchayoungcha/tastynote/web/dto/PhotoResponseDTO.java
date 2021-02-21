package com.youngchayoungcha.tastynote.web.dto;

import com.youngchayoungcha.tastynote.domain.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoResponseDTO {

    private Long id;
    private String fileUrl;
    private String comment;

    public static PhotoResponseDTO fromEntity(Photo photo) {
        PhotoResponseDTO dto = new PhotoResponseDTO();
        dto.id = photo.getId();
        dto.fileUrl = photo.getUrl();
        dto.comment = photo.getComment();
        return dto;
    }
}
