package com.curation.snut.service;

import java.util.List;
import java.util.stream.Collectors;

import com.curation.snut.dto.FolderDTO;
import com.curation.snut.entity.Folder;
import com.curation.snut.repository.FolderRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;

    @Override
    public List<FolderDTO> getFoldersByFolderNo(Long folderNo) {
        List curations = folderRepository.findFoldersByFolderNo(folderNo);

        return (List) curations.stream().map(i -> folToFolDTO((Folder) i)).collect(Collectors.toList());

    }

}
