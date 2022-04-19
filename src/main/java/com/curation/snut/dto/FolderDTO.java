package com.curation.snut.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FolderDTO {
    private Long folderNo;
    private String folderName;
    private Long curationNo;
    private Long collectionNo;
    private String email;
}
