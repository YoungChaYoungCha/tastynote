package com.youngchayoungcha.tastynote.repository;

import com.youngchayoungcha.tastynote.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
//    @Query("select m from Member m where m.email=%:memberEmail%")
//    Optional<Member> findMemberByEmail(String memberEmail);
}
