package com.youngchayoungcha.tastynote;

import com.youngchayoungcha.tastynote.domain.Member;
import com.youngchayoungcha.tastynote.domain.Note;
import com.youngchayoungcha.tastynote.repository.MemberRepository;
import com.youngchayoungcha.tastynote.repository.NoteRepository;
import com.youngchayoungcha.tastynote.repository.PostRepository;
import com.youngchayoungcha.tastynote.service.MemberService;
import com.youngchayoungcha.tastynote.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestDataGenerator implements CommandLineRunner {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private NoteRepository noteRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        MemberResponseDTO dto = memberService.register(MemberRegisterDTO.builder().email("cbh12032@gmail.com").password("12341234").name("훈키").build());
//        Member member = memberRepository.findById(dto.getId()).get();

        Member member = Member.createMember("cbh1203@naver.com", "asdfasdf", "훈키");
        String memberId = memberRepository.save(member).getId().toString();
        Note note = Note.createNote("title", member);
        noteRepository.save(note);
        member.certify();

//        Post post = Post.createPost(postDTO, photos, noteEntity, tags, restaurant);
//        postRepository.save(new Post())
//        postService.createPost(new PostCreateDTO(note.getId(), "포스트 제목", "포스트 컨텐츠", 10.0f, true, photoDTOs, tags, restaurantDTO));

    }
}
