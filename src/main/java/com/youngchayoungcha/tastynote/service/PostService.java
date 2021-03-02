package com.youngchayoungcha.tastynote.service;

import com.youngchayoungcha.tastynote.config.FileConfig;
import com.youngchayoungcha.tastynote.domain.*;
import com.youngchayoungcha.tastynote.repository.*;
import com.youngchayoungcha.tastynote.repository.impl.PhotoRepository;
import com.youngchayoungcha.tastynote.repository.RestaurantRepository;
import com.youngchayoungcha.tastynote.util.FileUtils;
import com.youngchayoungcha.tastynote.web.dto.*;
import com.youngchayoungcha.tastynote.exception.ElementNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final NoteRepository noteRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PhotoRepository photoRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public PostResponseDTO createPost(PostCreateDTO postDTO) throws IOException {
        Optional<Note> note = noteRepository.findNote(postDTO.getNoteId());
        note.orElseThrow(() -> new ElementNotFoundException(postDTO.getNoteId()));

        List<PhotoRequestDTO> photoDTOs = postDTO.getPhotos();
        List<Photo> photos = generatePhotos(photoDTOs);
        Set<Tag> tags = findOrCreateTag(postDTO.getTags());
        Restaurant restaurant = findOrCreateRestaurant(postDTO.getRestaurant(), postDTO.getScore());

        Post post = Post.createPost(postDTO, photos, note.get(), tags, restaurant);
        postRepository.save(post);

        refreshRestaurantScore(restaurant);
        return PostResponseDTO.fromEntity(post);
    }

    public PostResponseDTO findPost(Long postId) {
        Optional<Post> post = postRepository.findPost(postId);
        post.orElseThrow(() -> new ElementNotFoundException(postId));

        return PostResponseDTO.fromEntity(post.get());
    }

    public List<PostResponseDTO> getPostList(int page, int size) {
        List<Post> postList = postRepository.getPostList(page, size);
        return postList.stream().map(PostResponseDTO::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDTO modifyPost(PostModifyDTO postDTO) throws IOException {
        Optional<Post> post = postRepository.findPost(postDTO.getPostId());
        post.orElseThrow(() -> new ElementNotFoundException(postDTO.getPostId()));

        List<Photo> photos = generatePhotos(postDTO.getNewPhotos());
        // 기존 업로드된 파일은 로컬 스토리지에서 삭제.
        photoRepository.getPhotoUrlsByIds(postDTO.getDeletedPhotoIds()).forEach(FileUtils::deleteUploadedFileByUrl);
        List<TagModifyDTO> createEvent = postDTO.getTagEvents().stream().filter((data) -> data.getStatus().equals(TagEventStatus.CREATE)).collect(Collectors.toList());
        Set<Tag> tags = findOrCreateTag(createEvent.stream().map(TagModifyDTO::getTag).collect(Collectors.toList()));
        Set<String> deleteTags = postDTO.getTagEvents().stream().filter((data) -> data.getStatus().equals(TagEventStatus.DELETE)).map(TagModifyDTO::getTag).collect(Collectors.toSet());

        post.get().modifyPost(postDTO, photos, tags, deleteTags);
        refreshRestaurantScore(post.get().getRestaurant());

        return PostResponseDTO.fromEntity(post.get());
    }

    @Transactional
    public void deletePost(Long postId) {
        Optional<Post> post = postRepository.findPost(postId);
        post.orElseThrow(() -> new ElementNotFoundException(postId));
        postRepository.delete(post.get());
        refreshRestaurantScore(post.get().getRestaurant());
    }

    private List<Photo> generatePhotos(List<PhotoRequestDTO> photoDTOs) throws IOException {
        List<Photo> photos = new ArrayList<>();
        for (PhotoRequestDTO photo : photoDTOs) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(photo.getFile().getOriginalFilename()));
            String newFileName = UUID.randomUUID() + "." + FileUtils.getFileExtensions(fileName);
            photo.getFile().transferTo(new File(FileConfig.uploadFileBasePath + newFileName));
            photos.add(Photo.createPhoto(FileConfig.uploadFileBaseUrl + newFileName, photo.getComment()));
        }
        return photos;
    }

    private Set<Tag> findOrCreateTag(List<String> tagNames) {
        Set<Tag> tags = tagRepository.findByNameIn(tagNames);
        List<String> existsTags = tags.stream().map(Tag::getName).collect(Collectors.toList());
        tagNames.removeAll(existsTags);

        Set<Tag> resultTags = new LinkedHashSet<>(tags);
        for (String tagName : tagNames) {
            Tag tag = Tag.createTag(tagName);
            tagRepository.save(tag);
            resultTags.add(tag);
        }
        return resultTags;
    }

    private Restaurant findOrCreateRestaurant(RestaurantDTO dto, float score) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(dto.getPlaceId());
        if (restaurant.isPresent()) {
            refreshRestaurantScore(restaurant.get());
            return restaurant.get();
        } else {
            return Restaurant.createRestaurant(dto.getPlaceId(), dto.getName(), dto.getFormattedAddress(), dto.getLatitude(), dto.getLongitude(), score);
        }
    }

    private void refreshRestaurantScore(Restaurant restaurant) {
        restaurant.setAverageScore((float) (restaurant.getPosts().stream().mapToDouble(Post::getScore).sum()));
    }
}
