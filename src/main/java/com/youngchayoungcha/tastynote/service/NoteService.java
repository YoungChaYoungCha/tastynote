package com.youngchayoungcha.tastynote.service;

import com.youngchayoungcha.tastynote.domain.Member;
import com.youngchayoungcha.tastynote.domain.Note;
import com.youngchayoungcha.tastynote.exception.UnAuthorizedResourceAccessException;
import com.youngchayoungcha.tastynote.repository.NoteRepository;
import com.youngchayoungcha.tastynote.util.CurrentLoginInfoUtils;
import com.youngchayoungcha.tastynote.web.dto.NoteResponseDTO;
import com.youngchayoungcha.tastynote.exception.ElementNotFoundException;
import com.youngchayoungcha.tastynote.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoteService {

    private final MemberRepository memberRepository;
    private final NoteRepository noteRepository;

    @Transactional
    public NoteResponseDTO createNote(String title){
        Long memberId = CurrentLoginInfoUtils.getLoginMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ElementNotFoundException(memberId));
        return NoteResponseDTO.fromEntity(noteRepository.save(Note.createNote(title, member)));
    }

    @Transactional
    public NoteResponseDTO updateNote(Long noteId, String title) {
        Long memberId = CurrentLoginInfoUtils.getLoginMemberId();
        Optional<Note> note = noteRepository.findNote(noteId);
        note.orElseThrow(() -> new ElementNotFoundException(noteId));

        Note noteEntity = note.get();
        if (!noteEntity.isOwner(memberId)) {
            throw new UnAuthorizedResourceAccessException(noteEntity.getTitle(), noteEntity.getId(), memberId);
        }
        noteEntity.modifyNote(title);
        return NoteResponseDTO.fromEntity(noteEntity);
    }

    @Transactional
    public Long deleteNote(Long noteId) {
        Long memberId = CurrentLoginInfoUtils.getLoginMemberId();
        Optional<Note> note = noteRepository.findNote(noteId);
        note.orElseThrow(() -> new ElementNotFoundException(noteId));
        Note noteEntity = note.get();
        if (!noteEntity.isOwner(memberId)) {
            throw new UnAuthorizedResourceAccessException(noteEntity.getTitle(), noteEntity.getId(), memberId);
        }
        noteRepository.delete(note.get());
        return noteId;
    }

    public List<NoteResponseDTO> getMemberNotes(Long memberId){
        return noteRepository.findUserNotes(memberId).stream().map(NoteResponseDTO::fromEntity).collect(Collectors.toList());
    }

    public NoteResponseDTO getNote(Long noteId) {
        Optional<Note> note = noteRepository.findNote(noteId);
        note.orElseThrow(() -> new ElementNotFoundException(noteId));
        return NoteResponseDTO.fromEntity(note.get());
    }
}
