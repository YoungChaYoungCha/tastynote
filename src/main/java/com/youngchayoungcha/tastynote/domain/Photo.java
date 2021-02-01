package com.youngchayoungcha.tastynote.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Photo {

    @Id @GeneratedValue
    @Column(name = "photo_id")
    private Long id;

    private String url;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
