package com.curation.snut.repository;

import com.curation.snut.entity.Curation;
import com.curation.snut.entity.Likes;
import com.curation.snut.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findLikesByCurationAndMember(Curation curation, Member memeber);

    @Modifying
    @Query(value = "INSERT INTO likes(curation_no, email) VALUES(:curationNo,:email)", nativeQuery = true)
    void likes(Long curationNo, Long collectionNo, String email);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE curation_no = :curationNo AND email =:email", nativeQuery = true)
    void unLikes(Long curationNo, Long collectionNo, String email);

}
