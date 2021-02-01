package com.youngchayoungcha.tastynote.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PostTag {

    @Id @GeneratedValue
    @Column(name = "post_tag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Long getId() {
        return id;
    }
}
