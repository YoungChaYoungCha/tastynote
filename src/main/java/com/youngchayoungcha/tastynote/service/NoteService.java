package com.youngchayoungcha.tastynote.service;

import com.youngchayoungcha.tastynote.domain.Member;
import com.youngchayoungcha.tastynote.domain.Note;
import com.youngchayoungcha.tastynote.web.dto.NoteDTO;
import com.youngchayoungcha.tastynote.exception.ElementNotFoundException;
import com.youngchayoungcha.tastynote.repository.MemberRepository;
import com.youngchayoungcha.tastynote.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final MemberRepository memberRepository;
    private final NoteRepository noteRepository;

    @Transactional
    public NoteDTO createNote(Long memberId, NoteDTO noteDTO){
        Member member = memberRepository.findMember(memberId);
        return NoteDTO.fromEntity(noteRepository.save(Note.createNote(noteDTO.getTitle(), member)));
    }

    @Transactional
    public NoteDTO updateNote(Long noteId, NoteDTO noteDTO) {
        Optional<Note> note = noteRepository.findNote(noteId);
        note.orElseThrow(() -> new ElementNotFoundException(noteId));

        Note noteEntity = note.get();
        noteEntity.modifyNote(noteDTO.getTitle());
        return NoteDTO.fromEntity(noteEntity);
    }

    @Transactional
    public Long deleteNote(Long noteId) {
        Optional<Note> note = noteRepository.findNote(noteId);
        note.orElseThrow(() -> new ElementNotFoundException(noteId));
        noteRepository.deleteNote(note.get());
        return noteId;
    }

    public List<NoteDTO> getMemberNotes(Long memberId){
        return noteRepository.findUserNotes(memberId).stream().map(NoteDTO::fromEntity).collect(Collectors.toList());
    }

    public NoteDTO getNote(Long noteId) {
        Optional<Note> note = noteRepository.findNote(noteId);
        note.orElseThrow(() -> new ElementNotFoundException(noteId));
        return NoteDTO.fromEntity(note.get());
    }
}
