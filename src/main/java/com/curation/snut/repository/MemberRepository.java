package com.curation.snut.repository;

import java.util.Optional;

import com.curation.snut.entity.Member;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, String> {
        @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraph.EntityGraphType.LOAD)
        @Query("select m from Member m where m.fromSocial=:social "
                        + "and m.email=:email")
        Optional<Member> findByEmail(String email);

        @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraph.EntityGraphType.LOAD)
        @Query("select m from Member m where m.email=:email")
        Optional<Member> findByNickName(String nickName);

        @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraph.EntityGraphType.LOAD)
        @Query("select m from Member m where m.fromSocial=:social "
                        + "and m.email=:email")
        Member findByEmail2(String email);

        @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraph.EntityGraphType.LOAD)
        @Query("select m from Member m where m.email=:email")
        Member findByNickName2(String nickName);

}
