package com.youngchayoungcha.tastynote.repository;

import com.youngchayoungcha.tastynote.domain.Note;

import java.util.List;
import java.util.Optional;

public interface NoteCustomRepository {

    Optional<Note> findNote(Long noteId);

    List<Note> findUserNotes(Long memberId);

}
