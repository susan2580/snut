package com.curation.snut.controller;

import java.util.List;
import java.util.Map;

import com.curation.snut.dto.CommuJoinDTO;
import com.curation.snut.dto.CommunityDTO;
import com.curation.snut.entity.CommuJoin;
import com.curation.snut.entity.CommuJoinTemp;
import com.curation.snut.service.CommuJoinService;
import com.curation.snut.service.CommuJoinTempService;
import com.curation.snut.service.CommunityService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;
    private final CommuJoinTempService commuJoinTempService;
    private final CommuJoinService commuJoinService;

    @GetMapping(value = "/commuList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommunityDTO>> commuList(String searchTitle) {
        if (searchTitle != null) {
            List<CommunityDTO> searchCommunityList = communityService.searchTitle(searchTitle);
            return new ResponseEntity<>(searchCommunityList, HttpStatus.OK);
        } else {
            List<CommunityDTO> list = communityService.communityListWithCnt();
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/myCommuList") // 내가 가입한 커뮤 (내가 만든 커뮤 제외)
    public ResponseEntity<List<CommuJoin>> mycommuList(@RequestBody Map body) {
        String memberEmail = body.get("memberEmail").toString();
        List<CommuJoin> list = commuJoinService.findJoinCommu(memberEmail);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/commuList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> commuReg(CommunityDTO communityDTO) {
        communityService.write(communityDTO);
        return new ResponseEntity<>("완료", HttpStatus.CREATED);
    }

    @PostMapping(value = "/commuList/delete")
    public ResponseEntity<String> testdelete(@RequestBody Map body) {
        String memberEmail = body.get("memberEmail").toString();
        String commuEmail = body.get("commuEmail").toString();
        Long commuNo = Long.valueOf(body.get("commuNo").toString());

        if (memberEmail == commuEmail) {
            communityService.delete(commuNo);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("권한없음", HttpStatus.OK);
        }

    }

    @PostMapping(value = "/commuJoinApply") // 커뮤가입신청
    public ResponseEntity<String> commuJoinApply(CommuJoinDTO commuJoinDTO) {
        String message = commuJoinTempService.joinApply(commuJoinDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping(value = "/commuMyPage") // 가입신청보는 페이지
    public ResponseEntity<List<CommuJoinTemp>> commuMyPage(@RequestBody Map body) {
        String memberEmail = body.get("memberEmail").toString();
        List<CommuJoinTemp> joinAlertList = commuJoinTempService.joinAlertList(memberEmail);
        return new ResponseEntity<>(joinAlertList, HttpStatus.OK);
    }

    @PostMapping(value = "/commuJoinAccept") // 가입신청 수락
    public ResponseEntity<?> commuJoinAccept(CommuJoinDTO commuJoinDTO) {
        commuJoinService.joinAccept(commuJoinDTO);
        commuJoinTempService.joinAcceptAfterProcess(commuJoinDTO);
        return new ResponseEntity<>("수락 완료", HttpStatus.OK);
    }

    @GetMapping(value = "/commuJoinList", produces = MediaType.APPLICATION_JSON_VALUE) // 내가 관리자인 커뮤니티에 가입한 유저 목록
    public ResponseEntity<List<CommuJoin>> commuJoinList(@RequestBody Map body) {
        String memberEmail = body.get("memberEmail").toString();
        List<CommuJoin> joinList = commuJoinService.joinList(memberEmail);
        return new ResponseEntity<>(joinList, HttpStatus.OK);
    }

    @PostMapping(value = "/commuJoinList/commuJoinDelete") // 가입한 유저 강퇴 (권한 삭제)
    public ResponseEntity<?> commuJoinDelete(CommuJoinDTO commuJoinDTO) {
        commuJoinService.joinDelete(commuJoinDTO);
        return new ResponseEntity<>("강퇴 완료", HttpStatus.OK);
    }

}
