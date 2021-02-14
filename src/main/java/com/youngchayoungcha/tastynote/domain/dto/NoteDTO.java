package com.youngchayoungcha.tastynote.domain.dto;

import com.youngchayoungcha.tastynote.domain.Note;
import com.youngchayoungcha.tastynote.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
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
