package com.youngchayoungcha.tastynote.web.dto;

import com.youngchayoungcha.tastynote.domain.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagResponseDTO {

    private Long id;
    private String name;

    public static TagResponseDTO fromEntity(Tag tag) {
        TagResponseDTO tagResponseDTO = new TagResponseDTO();
        tagResponseDTO.id = tag.getId();
        tagResponseDTO.name = tag.getName();

        return tagResponseDTO;
    }
}
