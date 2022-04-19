package com.curation.snut.controller;

import java.util.List;
import java.util.Map;

import com.curation.snut.service.CurationService;
import com.curation.snut.service.FolderService;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
public class FolderController {
    private final FolderService folderService;

    @RequestMapping(value = "", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List folderListByFolderNo(@RequestParam Map body) {
        log.info("folderListByFolderNo............");
        log.info("no >>>" + body.get("folderNo"));
        Long a = Long.parseLong((String) body.get("folderNo"));
        return folderService.getFoldersByFolderNo(a);
    }
}
