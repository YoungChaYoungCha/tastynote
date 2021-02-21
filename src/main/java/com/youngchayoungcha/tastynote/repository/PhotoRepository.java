package com.youngchayoungcha.tastynote.repository;

import com.youngchayoungcha.tastynote.domain.Photo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PhotoRepository {

    @PersistenceContext
    private EntityManager em;

    public List<String> getPhotoURLsByIds(List<Long> photoIds) {
        return em.createQuery("select ph.url from Photo ph where ph.id IN :ids", String.class)
                .setParameter("ids", photoIds)
                .getResultList();
    }
}
