package com.youngchayoungcha.tastynote;

import com.youngchayoungcha.tastynote.domain.Member;
import com.youngchayoungcha.tastynote.domain.Note;
import com.youngchayoungcha.tastynote.domain.Post;
import com.youngchayoungcha.tastynote.repository.MemberRepository;
import com.youngchayoungcha.tastynote.repository.NoteRepository;
import com.youngchayoungcha.tastynote.service.PostService;
import com.youngchayoungcha.tastynote.web.dto.PhotoRequestDTO;
import com.youngchayoungcha.tastynote.web.dto.PostCreateDTO;
import com.youngchayoungcha.tastynote.web.dto.PostResponseDTO;
import com.youngchayoungcha.tastynote.web.dto.RestaurantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Component
public class TestDataGenerator implements CommandLineRunner {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private PostService postService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Member member = Member.createMember("cbh1203@naver.com", "12341234", "훈키");
        memberRepository.save(member);
        Note note = Note.createNote("title", member);
        noteRepository.save(note);

        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "hello file".getBytes());

        List<PhotoRequestDTO> photoDTOs = Collections.singletonList(new PhotoRequestDTO(file, "신기해"));
        List<String> tags = Collections.singletonList("태그");
        RestaurantDTO restaurantDTO = new RestaurantDTO("ieofqiewf", "멘텐", "대한민국 서울특별시 ", 1.4, 2.4);
        postService.createPost(new PostCreateDTO(note.getId(), "포스트 제목", "포스트 컨텐츠", 10.0f, true, photoDTOs, tags, restaurantDTO));

    }
}
