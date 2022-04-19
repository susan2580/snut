package com.curation.snut.repository;

import java.util.List;

import com.curation.snut.entity.Folder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    @Query("select f from Folder f order by folderNo")
    Page<Folder> list(Pageable pageable, Long folderNo);

    @Modifying
    @Transactional
    @Query("delete from Folder f where f.folderNo = :id")
    void deleteFolder(Long id);

    @Query("select fo.folder from ColCuration co where co.snutCollection.collectionNo = :no order by co.curation.modDate asc")
    List findFoldersByFolderNo(Long no);
}
