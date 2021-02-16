package com.youngchayoungcha.tastynote.web.dto;

import com.youngchayoungcha.tastynote.domain.Note;
import com.youngchayoungcha.tastynote.domain.Post;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {

    private Long id;

    private String title;

    private Set<Post> posts;

    public NoteDTO(String title) {
        this.title = title;
    }

    public static NoteDTO fromEntity(Note note){
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.id = note.getId();
        noteDTO.title = note.getTitle();
        noteDTO.posts = note.getPosts();
        return noteDTO;
    }

}
