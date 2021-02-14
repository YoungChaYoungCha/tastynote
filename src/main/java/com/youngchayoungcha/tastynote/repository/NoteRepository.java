package com.youngchayoungcha.tastynote.repository;

import com.youngchayoungcha.tastynote.domain.Note;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class NoteRepository {

    @PersistenceContext
    private EntityManager em;

    public Note save(Note note){
        em.persist(note);
        return note;
    }

    public Optional<Note> findNote(Long noteId) {
       return em.createQuery("select n from Note n left join fetch n.posts where n.id = :noteId", Note.class)
               .setParameter("noteId", noteId)
               .getResultList().stream().findFirst();
    }

    // 사용자에 관련된 노트를 Post를 fetch해서 가져옴.
    public List<Note> findUserNotes(Long memberId) {
        // Left join fetch를 하는 이유는 Post가 없는 경우에도 불러와야 함.

        return em.createQuery("select n from Note n left join fetch n.posts join n.member where n.member.id = :memberId", Note.class)
                .setParameter("memberId", memberId).getResultList();
    }

    public List<Note> findAll(){
        return em.createQuery("select n from Note n", Note.class).getResultList();
    }

    public void deleteNote(Note note){
        em.remove(note);
    }
}
