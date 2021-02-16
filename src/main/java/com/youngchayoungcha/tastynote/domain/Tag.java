package com.youngchayoungcha.tastynote.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<PostTag> postTags;

    public static Tag createTag(String name) {
        Tag tag = new Tag();
        tag.name = name;
        return tag;
    }

}
