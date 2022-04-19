package com.curation.snut.dto;

import com.curation.snut.entity.Curation;
import com.curation.snut.entity.Member;
import com.curation.snut.entity.SnutCollection;

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

public class LikesDTO {
    private Member member;
    private Curation curation;
    private SnutCollection snutcollection;

}
