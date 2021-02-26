package com.youngchayoungcha.tastynote.service;

import com.youngchayoungcha.tastynote.domain.Member;
import com.youngchayoungcha.tastynote.domain.Note;
import com.youngchayoungcha.tastynote.repository.NoteRepository;
import com.youngchayoungcha.tastynote.web.dto.NoteResponseDTO;
import com.youngchayoungcha.tastynote.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class NoteServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    NoteService noteService;


    @Test
    @Transactional
    public void 노트생성_테스트(){
        //given
        Member member = Member.createMember("cbh1203@naver.com", "asdfasdf", "훈키");
        Long memberId = memberRepository.createMember(member);

        //when
        NoteResponseDTO note = noteService.createNote(memberId, "라멘노트");
        Optional<Note> getNote = noteRepository.findNote(note.getId());

        //then
        Assertions.assertEquals(note.getTitle(), "라멘노트");
        Assertions.assertEquals(getNote.get().getMember().getEmail(), "cbh1203@naver.com");
    }

    @Test
    @Transactional
    public void 노트수정_테스트(){
        //given
        Member member = Member.createMember("cbh1203@naver.com", "asdfasdf", "훈키");
        Long memberId = memberRepository.createMember(member);
        NoteResponseDTO note = noteService.createNote(memberId,"라멘노트");

        //when
        Optional<Note> getNote = noteRepository.findNote(note.getId());
        noteService.updateNote(getNote.get().getId(),"라멘노트투투");

        //then
        Assertions.assertEquals(getNote.get().getTitle(), "라멘노트투투");
    }

    @Test
    @Transactional
    public void 사용자_노트_조회(){
        //given
        Member member = Member.createMember("cbh1203@naver.com", "asdfasdf", "훈키");
        Long memberId = memberRepository.createMember(member);
        for(int i = 0; i < 5; i++){
            noteService.createNote(memberId, "라멘노트" + i);
        }

        //when
        System.out.println(memberId);
        List<NoteResponseDTO> noteList = noteService.getMemberNotes(memberId);

        //then
        Assertions.assertEquals(5, noteList.size());
        for (int i = 0; i < 5; i++) {
            System.out.println(noteList.get(i).getTitle());
            Assertions.assertEquals("라멘노트" + i, noteList.get(i).getTitle());
        }
    }

    @Test
    @Transactional
    public void 사용자_노트_삭제(){
        //given
        Member member = Member.createMember("cbh1203@naver.com", "asdfasdf", "훈키");
        Long memberId = memberRepository.createMember(member);
        for(int i = 0; i < 5; i++){
            noteService.createNote(memberId,"라멘노트" + i);
        }

        //when
        List<NoteResponseDTO> notes = noteService.getMemberNotes(memberId);
        noteService.deleteNote(notes.get(0).getId());

        //then
        List<NoteResponseDTO> newNotes = noteService.getMemberNotes(memberId);
        Assertions.assertEquals(4, newNotes.size());
    }

}
