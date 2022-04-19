package com.curation.snut.repository;

import java.util.List;
import java.util.Optional;

import com.curation.snut.entity.Community;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    @EntityGraph(attributePaths = "creater", type = EntityGraphType.LOAD)
    @Query("select c, count(cc.communityName.no) from Community c left outer join CommunityComment cc on c.no = cc.communityName.no where c.title like %:searchTitle% group by c.no")
    List<Object[]> findByTitleContaining(String searchTitle);

    @EntityGraph(attributePaths = "creater", type = EntityGraphType.LOAD)
    @Query("select c, count(cc.communityName.no) from Community c left outer join CommunityComment cc on c.no = cc.communityName.no group by c.no")
    List<Object[]> countList();

    // @Query("select c, count(cc.communityName.no) from Community c left outer join
    // CommunityComment cc on c.no = cc.communityName.no group by c.no")
    // List<Object[]> countList();

    @EntityGraph(attributePaths = "creater", type = EntityGraphType.LOAD)
    @Query("select c from Community c where c.creater.email =:email and c.no =:no")
    Optional<Community> findByCreater(String email, Long no);
}
