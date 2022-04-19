package com.curation.snut.controller;

import com.curation.snut.entity.Member;
import com.curation.snut.service.LikesService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SnutCollectionController {
    private final LikesService likesService;

    @PostMapping("/read/{collectionNo}/likes")
    public ResponseEntity<?> likes(@PathVariable Long curationNo, Long collectionNo, Member member) {
        likesService.likes(collectionNo, collectionNo, member.getEmail());
        return new ResponseEntity<>("좋아요 성공", HttpStatus.OK);
    }

    @DeleteMapping("/read/{collectionNo}/unLikes")
    public ResponseEntity<?> unLikes(@PathVariable Long curationNo, Long collectionNo, Member member) {
        likesService.unLikes(collectionNo, collectionNo, member.getEmail());
        return new ResponseEntity<>("좋아요 취소 성공", HttpStatus.OK);
    }
}
