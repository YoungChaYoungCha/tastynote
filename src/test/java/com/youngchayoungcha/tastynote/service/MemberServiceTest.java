package com.youngchayoungcha.tastynote.service;

import com.youngchayoungcha.tastynote.domain.Member;
import com.youngchayoungcha.tastynote.domain.Note;
import com.youngchayoungcha.tastynote.repository.MemberRepository;
import com.youngchayoungcha.tastynote.repository.NoteRepository;
import com.youngchayoungcha.tastynote.web.dto.NoteResponseDTO;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    NoteService noteService;

    @After
    public void tearDown() throws Exception {
        memberRepository.deleteAll();
    }

    @Test
    public void Member_멤버삭제시노트삭제확인() throws Exception {
        //given
        Member member = Member.createMember("cbh1203@naver.com", "asdfasdf", "훈키");
        Long memberId = memberRepository.save(member).getId();

        //when
        NoteResponseDTO note = noteService.createNote(memberId, "라멘노트");
        Long noteId = note.getId();
        Optional<Note> getNote = noteRepository.findNote(noteId);

        //then
        Assertions.assertEquals(note.getTitle(), "라멘노트");
        Assertions.assertEquals(getNote.get().getMember().getEmail(), "cbh1203@naver.com");

        memberRepository.deleteAll();

        assertThat(noteRepository.findNote(noteId)).isEqualTo(Optional.empty());

    }
}
