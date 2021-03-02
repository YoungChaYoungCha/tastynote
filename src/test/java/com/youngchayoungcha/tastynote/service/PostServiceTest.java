package com.youngchayoungcha.tastynote.service;

import com.youngchayoungcha.tastynote.config.FileConfig;
import com.youngchayoungcha.tastynote.domain.Member;
import com.youngchayoungcha.tastynote.domain.Post;
import com.youngchayoungcha.tastynote.domain.Restaurant;
import com.youngchayoungcha.tastynote.exception.ElementNotFoundException;
import com.youngchayoungcha.tastynote.repository.MemberRepository;
import com.youngchayoungcha.tastynote.repository.PostRepository;
import com.youngchayoungcha.tastynote.repository.RestaurantRepository;
import com.youngchayoungcha.tastynote.web.dto.*;
import org.junit.jupiter.api.*;
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
    private MemberRepository memberRepository;
    @Autowired
    private NoteService noteService;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    private Member member;

    private NoteResponseDTO noteDTO;

    @BeforeEach
    public void initData() throws IOException {
        member = Member.createMember("cbh1203@naver.com", "asdfasdf", "훈키");
        Long memberId = memberRepository.save(member).getId();
        noteDTO = noteService.createNote(memberId, "라멘노트");
    }

    @Test
    public void 포스트_생성_테스트() throws IOException {

        //when
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "hello file".getBytes());
        List<PhotoRequestDTO> photoDTOs = Collections.singletonList(new PhotoRequestDTO(file, "신기해"));
        List<String> tags = Collections.singletonList("태그");
        RestaurantDTO restaurantDTO = new RestaurantDTO("efwfzdf", "멘텐", "대한민국 서울특별시 ", 1.4, 2.4);
        PostResponseDTO postResponseDTO = postService.createPost(new PostCreateDTO(noteDTO.getId(), "포스트 제목", "포스트 컨텐츠", 10.0f, true, photoDTOs, tags, restaurantDTO));
        Optional<Post> post = postRepository.findPost(postResponseDTO.getId());

        //then
        Assertions.assertEquals("포스트 제목", postResponseDTO.getTitle());
        Assertions.assertEquals("포스트 컨텐츠", postResponseDTO.getContent());
        Assertions.assertEquals((short) 10, postResponseDTO.getScore());
        Assertions.assertEquals(1, post.get().getPhotos().size());
        Assertions.assertEquals(10F, post.get().getRestaurant().getAverageScore());
    }

    @Test
    public void 포스트_삭제_테스트() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "hello file".getBytes());
        List<PhotoRequestDTO> photoDTOs = Collections.singletonList(new PhotoRequestDTO(file, "신기해"));
        List<String> tags = Collections.singletonList("태그");
        RestaurantDTO restaurantDTO = new RestaurantDTO("efwfzdf", "멘텐", "대한민국 서울특별시 ", 1.4, 2.4);
        PostResponseDTO postResponseDTO = postService.createPost(new PostCreateDTO(noteDTO.getId(), "포스트 제목", "포스트 컨텐츠", (float) 10, true, photoDTOs, tags, restaurantDTO));

        //when
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantDTO.getPlaceId());
        postService.deletePost(postResponseDTO.getId());

        //then
        Assertions.assertThrows(ElementNotFoundException.class, () -> postService.findPost(postResponseDTO.getId()));
        Assertions.assertEquals(0F, restaurant.get().getAverageScore());
    }

    @Test
    public void 포스트_업데이트_테스트() throws IOException {
        //given
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
        RestaurantDTO restaurantDTO = new RestaurantDTO("efwfzdf", "멘텐", "대한민국 서울특별시 ", 1.4, 2.4);
        PostResponseDTO postResponseDTO = postService.createPost(new PostCreateDTO(noteDTO.getId(), "포스트 제목", "포스트 컨텐츠", (float) 10, true, photoDTOs, tags, restaurantDTO));

        //when
        MockMultipartFile modifiedFile = new MockMultipartFile("file", "test.txt", "text/plain", "hello file".getBytes());
        List<PhotoRequestDTO> newPhotoDTOs = Collections.singletonList(new PhotoRequestDTO(modifiedFile, "새로운 파일"));
        List<TagModifyDTO> tagModifyDTOs = Arrays.asList(new TagModifyDTO(TagEventStatus.CREATE, "훈투"), new TagModifyDTO(TagEventStatus.DELETE, "태그"));
        PostResponseDTO modifiedPostDTO = postService.modifyPost(new PostModifyDTO(postResponseDTO.getId(), "바뀐 포스트", "바뀐 컨텐츠", 6.0F, true, newPhotoDTOs, Collections.singletonList(postResponseDTO.getPhotos().get(0).getId()), tagModifyDTOs));

        //then
        Assertions.assertEquals("바뀐 포스트", modifiedPostDTO.getTitle());
        Assertions.assertEquals("바뀐 컨텐츠", modifiedPostDTO.getContent());
        Assertions.assertEquals((short) 6, modifiedPostDTO.getScore());
        Assertions.assertEquals(1, modifiedPostDTO.getPhotos().size());
        Assertions.assertEquals(4, modifiedPostDTO.getTags().size());

        String url = postResponseDTO.getPhotos().get(0).getFileUrl();
        String filePath = url.replace(FileConfig.uploadFileBaseUrl, FileConfig.uploadFileBasePath);
        File file2 = new File(filePath);
        Assertions.assertFalse(file2.exists());
    }

    @Test
    public void 포스트리스트_조회_테스트() throws IOException {
        for (int i = 0; i < 10; i++) {
            MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "hello file".getBytes());
            List<PhotoRequestDTO> photoDTOs = Collections.singletonList(new PhotoRequestDTO(file, "신기해" + i));
            List<String> tags = new ArrayList<String>() {
                {
                    add("태그");
                }
            };
            RestaurantDTO restaurantDTO = new RestaurantDTO("dfqweqt", "멘텐" + i, "대한민국 서울특별시 ", 1.4, 2.4);
            postService.createPost(new PostCreateDTO(noteDTO.getId(), "포스트 제목" + i, "포스트 컨텐츠", (float) i, true, photoDTOs, tags, restaurantDTO));
        }
        //when
        List<PostResponseDTO> postList1 = postService.getPostList(0, 5);
        List<PostResponseDTO> postList2 = postService.getPostList(1, 5);
        Optional<Restaurant> restaurant = restaurantRepository.findById("dfqweqt");

        //then
        Assertions.assertEquals(5, postList1.size());
        Assertions.assertEquals(5, postList2.size());
        Assertions.assertEquals(4.5, restaurant.get().getAverageScore());
    }

    @AfterEach
    public void 테스트끝난_파일삭제() {
        File file = new File(FileConfig.uploadFileBasePath);
        File[] files = file.listFiles();  // 해당 폴더 안의 파일들을 files 변수에 담음

        assert files != null;
        for (File value : files) { // 개수만큼 루프
            if (value.isFile()) { // 파일일경우 해당파일 삭제
                value.delete();
            }
        }
    }
}
