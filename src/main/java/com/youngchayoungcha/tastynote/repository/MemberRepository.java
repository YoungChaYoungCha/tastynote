package com.youngchayoungcha.tastynote.repository;

import com.youngchayoungcha.tastynote.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Member findMember(Long memberId) {
        return em.find(Member.class, memberId);
    }

    public Long createMember(Member member){
        em.persist(member);
        return member.getId();
    }
}
