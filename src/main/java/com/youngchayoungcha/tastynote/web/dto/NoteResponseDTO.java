package com.youngchayoungcha.tastynote.web.dto;

import com.youngchayoungcha.tastynote.domain.Note;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponseDTO {

    private Long id;

    private String title;

    private Set<PostThumbnailResponseDTO> posts;

    public static NoteResponseDTO fromEntity(Note note){
        NoteResponseDTO noteDTO = new NoteResponseDTO();
        noteDTO.id = note.getId();
        noteDTO.title = note.getTitle();
        noteDTO.posts = note.getPosts().stream().map(PostThumbnailResponseDTO::fromEntity).collect(Collectors.toSet());
        return noteDTO;
    }

}
