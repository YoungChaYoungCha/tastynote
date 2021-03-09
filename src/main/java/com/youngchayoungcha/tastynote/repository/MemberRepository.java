package com.youngchayoungcha.tastynote.repository;

import com.youngchayoungcha.tastynote.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
//    @Query("select m from Member m where m.email=%:memberEmail%")
//    Optional<Member> findMemberByEmail(String memberEmail);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByRefreshToken(String refreshToken);
}
