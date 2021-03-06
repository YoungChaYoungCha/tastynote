package com.youngchayoungcha.tastynote.domain;

import com.youngchayoungcha.tastynote.web.dto.PostCreateDTO;
import com.youngchayoungcha.tastynote.web.dto.PostModifyDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Photo> photos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "note_id")
    private Note note;

    private String title;

    private String content;

    private Float score;

    private boolean isPublic;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<PostTag> postTags = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public static Post createPost(PostCreateDTO postDTO, List<Photo> photos, Note note, Set<Tag> tags, Restaurant restaurant){
        Post post = new Post();
        post.title = postDTO.getTitle();
        post.content = postDTO.getContent();
        post.score = postDTO.getScore();
        post.isPublic = postDTO.isPublic();
        post.setNote(note);
        post.setRestaurant(restaurant);
        post.setPhotos(photos);
        post.addTags(tags);
        return post;
    }

    public void modifyPost(PostModifyDTO postDTO, List<Photo> photos, Set<Tag> tags, Set<String> deleteTags){
        this.title = postDTO.getTitle();
        this.content = postDTO.getContent();
        this.score = postDTO.getScore();
        this.isPublic = postDTO.isPublic();
        this.photos.addAll(photos);
        Set<Long> photoIds = this.photos.stream().map(Photo::getId).collect(Collectors.toSet());
        photoIds.retainAll(postDTO.getDeletedPhotoIds());
        this.setPhotos(photos.stream().filter(data -> !photoIds.contains(data.getId())).collect(Collectors.toList()));
        this.addTags(tags);
        this.postTags = this.postTags.stream().filter(postTag -> !deleteTags.contains(postTag.getTag().getName())).collect(Collectors.toSet());
    }

    public boolean isOwner(Long memberId){
        return (memberId.equals(this.getNote().getMember().getId()));
    }

    private void addTags(Set<Tag> tags){
        for (Tag tag : tags) {
            PostTag postTag = PostTag.createPostTag(this, tag);
            postTags.add(postTag);
            postTag.setPost(this);
        }
    }

    private void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
        this.getRestaurant().addPost(this);
    }

    private void setNote(Note note){
        this.note = note;
        this.note.getPosts().add(this);
    }

    private void setPhotos(List<Photo> photos){
        this.photos = photos;
        for (Photo photo : photos) {
            photo.setPost(this);
        }
    }

    // ManyToOne Reference Entity와의 관계를 삭제
    @PreRemove
    private void removeReferenceEntities(){
        this.getNote().removePost(this);
        this.getRestaurant().removePost(this);
    }
}
