package com.youngchayoungcha.tastynote.repository;

import com.youngchayoungcha.tastynote.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Set<Tag> findByNameIn(List<String> names);
}
