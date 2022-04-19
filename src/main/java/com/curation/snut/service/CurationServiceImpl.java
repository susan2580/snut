package com.curation.snut.service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.curation.snut.dto.CurationDTO;
import com.curation.snut.entity.Curation;
import com.curation.snut.entity.CurationImage;
import com.curation.snut.entity.Hashtag;
import com.curation.snut.repository.CurationImageRepository;
import com.curation.snut.repository.CurationRepository;

import com.curation.snut.repository.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CurationServiceImpl implements CurationService {
    private final CurationRepository curationRepository;
    private final CurationImageRepository curationImageRepository;
    private final HashtagRepository hashtagRepository;

    // 성진


    @Override
    public List<CurationDTO> getCurationByEmail(String email) {
        List<Curation> curations = curationRepository.findCurationByEmail(email);

        return curations.stream().map(cu -> {
            List<CurationImage> imageList = curationRepository.findCurationImageByCurationNo(cu.getCurationNo());
            return entityToDTO(cu, imageList);
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CurationDTO getCurationsByCurationNo(Long curationNo) {
        Curation curation = curationRepository.findCurationsByCurationNo(curationNo);
        List<CurationImage> curationImageList = curationRepository.findCurationImageByCurationNo(curationNo);

        System.out.println("curation >>> " + curation);
        for(CurationImage ci : curationImageList) {
            System.out.println("curationImageList >>> " + ci);
        }

        return entityToDTO(curation, curationImageList);
    }

    @Transactional
    @Override
    public List<CurationDTO> getCurationsByCollectionNo(Long colId) {
        List<Curation> cuList = curationRepository.findCurationsByCollectionNo(colId);

        return cuList.stream().map(cu -> {
           List<CurationImage> curationImageList = curationRepository.findCurationImageByCurationNo(cu.getCurationNo());
           return entityToDTO(cu, curationImageList);
        }).collect(Collectors.toList());
    }
    @Transactional
    @Override
    public List<CurationDTO> getCurationsByWord(String word) {
        List<Curation> cuList = curationRepository.findCurationByWord22(word);
        return cuList.stream().map(cu -> {
            List<CurationImage> imgList = curationRepository.findCurationImageByCurationNo(cu.getCurationNo());
            return entityToDTO(cu, imgList);
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Long register(CurationDTO curationDTO) {

        Map<String, Object> entityMap = dtoToEntity(curationDTO);
        Curation curation = (Curation) entityMap.get("curation");
        List<CurationImage> curationImageList = (List<CurationImage>) entityMap.get("imgList");

        Set hashtag = curation.getHashtag();
        if(hashtag.size() > 0) {
            hashtag.stream().forEach(tag -> hashtagRepository.save((Hashtag) tag));
        }

        curationRepository.save(curation);

        if(curationImageList != null && curationImageList.size() > 0) {
            curationImageList.forEach(curationImage ->
                curationImageRepository.save(curationImage));
        }
        return curation.getCurationNo();
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////


//    @Override
//    public List<CurationDTO> CuList() {
//        List<Curation> cuList = curationRepository.findAll();
//        log.info(cuList);
//        List<CurationDTO> curationDTOList = cuList.stream()
//                .map(curation -> entityToDTO(curation)).collect(Collectors.toList());
//        return curationDTOList;
    }

//    @Override
//    public void write(CurationDTO curationDTO) {
//        Curation curation = dtoToEntity(curationDTO);
//        curationRepository.save(curation);
//    }

//    @Override
//    public void modify(CurationDTO dto) {
//        Optional<Curation> result = curationRepository.findById(dto.getCurationNo());
//        if (result.isPresent()) {
//            Curation curation = result.get();
//            curation.changeCurationTitle(dto.getCurationTitle());
//            curation.changeCurationText(dto.getCurationText());
//            curationRepository.save(curation);
//        }
//    }

//    @Transactional
//    @Override
//    public void delete(Long id) {
//        curationRepository.deleteById(id);
//    }
//
//    @Override
//    public CurationDTO getCuration(Long curationNo) {
//        Curation curation = curationRepository.findById(curationNo).orElseThrow();
//
//        CurationDTO curationDTO = entityToDTO(curation);
//        // CurationDTO.setNickName(nickname);
//
//        return curationDTO;
//
//    }

//    // 사용안하는중
//    @Override
//    public List<CurationDTO> searchCurationTitle(String searchCurationTitle) {
//        return null;
//    }
//}
// @Override
// public List<CurationDTO> searchCurationTitle(String searchCurationTitle) {
// List<Object[]> curationList =
// curationRepository.findByCurationTitleContaining(searchCurationTitle);
// List<CurationDTO> curationDTOList = curationList.stream()
// .map(entity -> entityToDTO((Curation) entity[0], (Long)
// entity[1])).collect(Collectors.toList());
// return curationDTOList;
// }

// }
