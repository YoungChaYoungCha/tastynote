package com.youngchayoungcha.tastynote.repository;

import com.youngchayoungcha.tastynote.domain.Tag;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Tag> findOrCreateTags(List<String> tagStrings) {

        List<Tag> tags = entityManager.createQuery("select t from Tag t where t.name IN :names", Tag.class)
                    .setParameter("names", tagStrings).getResultList();

        List<String> existsTags = tags.stream().map(Tag::getName).collect(Collectors.toList());
        tagStrings.removeAll(existsTags);

        List<Tag> resultTags = new ArrayList<>(tags);
        for (String tagString : tagStrings) {
            Tag tag = Tag.createTag(tagString);
            entityManager.persist(tag);
            resultTags.add(tag);
        }

        return resultTags;
    }
}
