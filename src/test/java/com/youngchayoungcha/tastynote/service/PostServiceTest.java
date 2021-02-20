package com.youngchayoungcha.tastynote.service;

import com.youngchayoungcha.tastynote.config.FileConfig;
import com.youngchayoungcha.tastynote.domain.Member;
import com.youngchayoungcha.tastynote.domain.Post;
import com.youngchayoungcha.tastynote.exception.ElementNotFoundException;
import com.youngchayoungcha.tastynote.repository.MemberRepository;
import com.youngchayoungcha.tastynote.repository.NoteRepository;
import com.youngchayoungcha.tastynote.repository.PostRepository;
import com.youngchayoungcha.tastynote.web.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    NoteService noteService;
    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;

    @Test
    public void 포스트_생성_테스트() throws IOException {
        //given
        Member member = Member.createMember("cbh1203@naver.com", "asdfasdf", "훈키");
        Long memberId = memberRepository.createMember(member);
        NoteDTO note = noteService.createNote(memberId,"라멘노트");

        //when
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "hello file".getBytes());
        List<PhotoRequestDTO> photoDTOs = Collections.singletonList(new PhotoRequestDTO(file, "신기해"));
        List<String> tags = Collections.singletonList("태그");
        PostResponseDTO postResponseDTO = postService.createPost(new PostCreateDTO(note.getId(), "포스트 제목", "포스트 컨텐츠", (short) 10, true, photoDTOs, tags));
        Optional<Post> post = postRepository.findPost(postResponseDTO.getId());

        //then
        Assertions.assertEquals("포스트 제목", postResponseDTO.getTitle());
        Assertions.assertEquals("포스트 컨텐츠", postResponseDTO.getContent());
        Assertions.assertEquals((short) 10, postResponseDTO.getScore());
        Assertions.assertEquals(post.get().getPhotos().size(), 1);
    }

    @Test
    public void 포스트_삭제_테스트() throws IOException {
        //given
        Member member = Member.createMember("cbh1203@naver.com", "asdfasdf", "훈키");
        Long memberId = memberRepository.createMember(member);
        NoteDTO note = noteService.createNote(memberId,"라멘노트");
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "hello file".getBytes());
        List<PhotoRequestDTO> photoDTOs = Collections.singletonList(new PhotoRequestDTO(file, "신기해"));
        List<String> tags = Collections.singletonList("태그");
        PostResponseDTO postResponseDTO = postService.createPost(new PostCreateDTO(note.getId(), "포스트 제목", "포스트 컨텐츠", (short) 10, true, photoDTOs, tags));

        //when
        postService.deletePost(postResponseDTO.getId());

        //then
        Assertions.assertThrows(ElementNotFoundException.class, () -> postService.findPost(postResponseDTO.getId()));
    }

    @Test
    public void 포스트_업데이트_테스트() throws IOException {
        //given
        Member member = Member.createMember("cbh1203@naver.com", "asdfasdf", "훈키");
        Long memberId = memberRepository.createMember(member);
        NoteDTO note = noteService.createNote(memberId, "라멘노트");
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "hello file".getBytes());
        List<PhotoRequestDTO> photoDTOs = Collections.singletonList(new PhotoRequestDTO(file, "신기해"));
        List<String> tags = new ArrayList<String>() {
            {
                add("태그");
                add("태그2");
                add("태그3");
                add("태그4");
            }
        };
        PostResponseDTO postResponseDTO = postService.createPost(new PostCreateDTO(note.getId(), "포스트 제목", "포스트 컨텐츠", (short) 10, true, photoDTOs, tags));

        //when
        MockMultipartFile modifiedFile = new MockMultipartFile("file", "test.txt", "text/plain", "hello file".getBytes());
        List<PhotoRequestDTO> newPhotoDTOs = Collections.singletonList(new PhotoRequestDTO(modifiedFile, "새로운 파일"));
        List<TagModifyDTO> tagModifyDTOs = Arrays.asList(new TagModifyDTO(TagEventStatus.CREATE, "훈투"), new TagModifyDTO(TagEventStatus.DELETE, "태그"));
        PostResponseDTO modifiedPostDTO = postService.modifyPost(new PostModifyDTO(postResponseDTO.getId(), "바뀐 포스트", "바뀐 컨텐츠", (short) 6, true, newPhotoDTOs, Collections.singletonList(postResponseDTO.getPhotos().get(0).getId()), tagModifyDTOs));

        //then
        Assertions.assertEquals(modifiedPostDTO.getTitle(), "바뀐 포스트");
        Assertions.assertEquals(modifiedPostDTO.getContent(), "바뀐 컨텐츠");
        Assertions.assertEquals(modifiedPostDTO.getScore(), (short) 6);
        Assertions.assertEquals(modifiedPostDTO.getPhotos().size(), 1);
        Assertions.assertEquals(modifiedPostDTO.getTags().size(), 4);
        // 사진이 제대로 삭제되었는지 테스트
        String url = postResponseDTO.getPhotos().get(0).getFileUrl();
        String filePath = url.replace(FileConfig.uploadFileBaseUrl, FileConfig.uploadFileBasePath);
        File file2 = new File(filePath);
        Assertions.assertFalse(file2.exists());
    }
}
