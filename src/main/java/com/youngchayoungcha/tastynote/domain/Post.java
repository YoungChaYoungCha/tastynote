package com.youngchayoungcha.tastynote.domain;

import com.youngchayoungcha.tastynote.web.dto.PostCreateDTO;
import com.youngchayoungcha.tastynote.web.dto.PostModifyDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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
    private Set<Photo> photos;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "note_id")
    private Note note;

    private String title;

    private String content;

    private short score;

    private boolean isPublic;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostTag> postTags = new ArrayList<>();

    public static Post createPost(PostCreateDTO postDTO, Set<Photo> photos, Note note, List<Tag> tags){
        Post post = new Post();
        post.title = postDTO.getTitle();
        post.content = postDTO.getContent();
        post.score = postDTO.getScore();
        post.isPublic = postDTO.isPublic();
        post.note = note;
        post.photos = photos;
        post.addTags(tags);
        return post;
    }

    public void modifyPost(PostModifyDTO postDTO, Set<Photo> photos, List<Tag> tags, Set<String> deleteTags){
        this.title = postDTO.getTitle();
        this.content = postDTO.getContent();
        this.score = postDTO.getScore();
        this.isPublic = postDTO.isPublic();
        this.photos.addAll(photos);
        Set<Long> photoIds = this.photos.stream().map(Photo::getId).collect(Collectors.toSet());
        photoIds.retainAll(postDTO.getDeletedPhotoIds());
        this.photos = photos.stream().filter(data -> !photoIds.contains(data.getId())).collect(Collectors.toSet());
        this.addTags(tags);
        this.postTags = this.postTags.stream().filter(postTag -> !deleteTags.contains(postTag.getTag().getName())).collect(Collectors.toList());
    }

    public void setNote(Note note) {
        this.note = note;
        note.getPosts().add(this);
    }

    public void addTags(List<Tag> tags){
        for (Tag tag : tags) {
            PostTag postTag = PostTag.createPostTag(this, tag);
            postTags.add(postTag);
        }
    }

}
