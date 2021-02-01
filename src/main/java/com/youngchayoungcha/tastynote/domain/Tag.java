package com.youngchayoungcha.tastynote.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Tag {

    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<PostTag> postTags;


}
