package com.curation.snut.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.curation.snut.dto.CommentDTO;
import com.curation.snut.entity.CommuJoin;
import com.curation.snut.entity.Community;
import com.curation.snut.repository.CommuJoinRepository;
import com.curation.snut.repository.CommunityRepository;
import com.curation.snut.service.CommentService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommuJoinRepository commuJoinRepository;
    private final CommunityRepository communityRepository;

    @GetMapping(value = "/commentList")
    public ResponseEntity<Map<String, Page<CommentDTO>>> commentListTest(
            @PageableDefault(size = 2) final Pageable pageable, final Long no) {
        Page<CommentDTO> commentDTOList = commentService.commentList(pageable, no);
        Page<CommentDTO> commentDTOList2 = commentService.ancommentList(pageable, no);

        Map<String, Page<CommentDTO>> map = new HashMap<>();
        map.put("import", commentDTOList2);
        map.put("nomal", commentDTOList);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(value = "/commentList")
    public ResponseEntity<?> commentWrite(CommentDTO commentDTO) {

        String writer = commentDTO.getWriter().getEmail();
        Long commuNo = commentDTO.getCommunityName().getNo();
        Optional<CommuJoin> findWriteAuthority = commuJoinRepository.findWriteAuthority(commuNo, writer);

        Optional<Community> findCreater = communityRepository.findByCreater(writer, commuNo);

        boolean check = commentDTO.isAnnouncement();
        if (!check) {
            if (findWriteAuthority.isPresent()) {
                commentService.write(commentDTO);
                return new ResponseEntity<>("등록", HttpStatus.OK);
            } else if (findCreater.isPresent()) {
                commentService.write(commentDTO);
                return new ResponseEntity<>("등록", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("권한 없음", HttpStatus.OK);
            }
        } else {
            if (findCreater.isPresent()) {
                commentService.write(commentDTO);
                return new ResponseEntity<>("등록", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("권한 없음", HttpStatus.OK);
            }
        }

    }

    @PostMapping("/comment/delete")
    public ResponseEntity<?> commentDelete(@RequestBody Map body) {
        Long cno = Long.valueOf(body.get("cno").toString());
        String memberEmail = body.get("memberEmail").toString();
        String commnetEmail = body.get("commentEmail").toString();

        if (memberEmail == commnetEmail) {
            commentService.delete(cno);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("권한이 없습니다", HttpStatus.OK);
        }

    }
}
