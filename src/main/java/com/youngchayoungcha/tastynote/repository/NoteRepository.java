package com.youngchayoungcha.tastynote.repository;

import com.youngchayoungcha.tastynote.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>, NoteCustomRepository {

}
