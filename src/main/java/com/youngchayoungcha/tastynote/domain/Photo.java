package com.youngchayoungcha.tastynote.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo {

    @Id @GeneratedValue
    @Column(name = "photo_id")
    private Long id;

    private String url;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public static Photo createPhoto(String url, String comment){
        Photo photo = new Photo();
        photo.url = url;
        photo.comment = comment;
        return photo;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
