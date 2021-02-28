package com.youngchayoungcha.tastynote.repository;

import com.youngchayoungcha.tastynote.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Post post){
        em.persist(post);
    }

    public Optional<Post> findPost(Long postId){
        return em.createQuery("select p from Post p " +
                "left join fetch p.photos " +
                "left join fetch p.postTags " +
                "where p.id = :postId", Post.class)
                .setParameter("postId", postId)
                .getResultList().stream().findFirst();
    }

    public void delete(Post post){
        em.remove(post);
    }

}
