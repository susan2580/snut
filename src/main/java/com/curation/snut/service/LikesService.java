package com.curation.snut.service;

import com.curation.snut.dto.LikesDTO;
import com.curation.snut.entity.Curation;
import com.curation.snut.entity.Likes;
import com.curation.snut.entity.Member;
import com.curation.snut.security.dto.AuthMemberDTO;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;

public interface LikesService {

        default void likes(Long curationNo, Long collectionNo, String email) {

        }

        default void unLikes(Long curationNo, Long collectionNo, String email) {

        }

        default Likes dtoToEntity(LikesDTO dto) {
                Member member = Member.builder()
                                .email(dto.getMember().getEmail())
                                .build();
                Curation curation = Curation.builder()
                                .curationNo(dto.getCuration().getCurationNo())
                                .build();
                Likes curationLike = Likes.builder()
                                .member(member)
                                .curation(curation).build();
                return curationLike;
        }

        default LikesDTO entityToDTO(Likes like) {
                LikesDTO dto = LikesDTO.builder()
                                .member(like.getMember())
                                .curation(like.getCuration())
                                .build();

                return dto;
        }

}
