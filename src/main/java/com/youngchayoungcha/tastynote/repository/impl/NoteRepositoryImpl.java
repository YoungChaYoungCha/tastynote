package com.youngchayoungcha.tastynote.repository.impl;

import com.youngchayoungcha.tastynote.domain.Note;
import com.youngchayoungcha.tastynote.repository.AbstractCustomRepository;
import com.youngchayoungcha.tastynote.repository.NoteCustomRepository;

import java.util.List;
import java.util.Optional;

import static com.youngchayoungcha.tastynote.domain.QNote.note;

public class NoteRepositoryImpl extends AbstractCustomRepository implements NoteCustomRepository {

    @Override
    public Optional<Note> findNote(Long noteId) {
        return getQueryFactory().selectFrom(note).leftJoin(note.posts).fetchJoin().where(note.id.eq(noteId)).fetch().stream().findFirst();
    }

    @Override
    public List<Note> findUserNotes(Long memberId) {
        return getQueryFactory().selectFrom(note).leftJoin(note.posts).fetchJoin().innerJoin(note.member).where(note.member.id.eq(memberId)).fetch();
    }
}
