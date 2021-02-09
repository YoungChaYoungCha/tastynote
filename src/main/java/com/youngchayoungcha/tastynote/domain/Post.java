package com.youngchayoungcha.tastynote.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    @OneToMany(mappedBy = "post")
    private Set<Photo> photos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id")
    private Note note;

    private String content;

    private short score;

    private boolean isPublic;

    private LocalDateTime postingDateTime;

}
