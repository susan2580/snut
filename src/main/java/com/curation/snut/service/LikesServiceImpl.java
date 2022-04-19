package com.curation.snut.service;

import javax.transaction.Transactional;

import com.curation.snut.entity.Member;
import com.curation.snut.repository.LikesRepository;
import com.curation.snut.repository.MemberRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {
    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void likes(Long curationNo, Long collectionNo, String email) {
        Member member = memberRepository.findByEmail2(email);
        likesRepository.likes(curationNo, collectionNo, member.getEmail());
    }

    @Transactional
    @Override
    public void unLikes(Long curationNo, Long collectionNo, String email) {
        Member member = memberRepository.findByEmail2(email);
        likesRepository.likes(curationNo, collectionNo, member.getEmail());
    }

}