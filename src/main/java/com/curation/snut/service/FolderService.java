package com.curation.snut.service;

import java.util.List;

import com.curation.snut.dto.FolderDTO;
import com.curation.snut.entity.Curation;
import com.curation.snut.entity.Folder;
import com.curation.snut.entity.Member;
import com.curation.snut.entity.SnutCollection;

public interface FolderService {
    List<FolderDTO> getFoldersByFolderNo(Long folderNo);

    default FolderDTO folToFolDTO(Folder folder) {

        FolderDTO dto = FolderDTO.builder()
                .folderNo(folder.getFolderNo())
                .folderName(folder.getFolderName())
                .collectionNo(folder.getCollection().getCollectionNo())
                .curationNo(folder.getCuration().getCurationNo())
                .email(folder.getWriter().getEmail())
                .build();

        return dto;
    }

    default Folder folDTOToFol(FolderDTO folderDTO) {

        Folder folder = Folder.builder()
                .folderNo(folderDTO.getFolderNo())
                .folderName(folderDTO.getFolderName())
                .collection(SnutCollection.builder().collectionNo(folderDTO.getCollectionNo()).build())
                .curation(Curation.builder().curationNo(folderDTO.getCurationNo()).build())
                .writer(Member.builder().email(folderDTO.getEmail()).build())
                .build();

        return folder;
    }

}
