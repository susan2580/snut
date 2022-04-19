package com.curation.snut.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Folder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long folderNo;
    String folderName;
    @ManyToOne(fetch = FetchType.LAZY)
    Curation curation;
    @ManyToOne(fetch = FetchType.LAZY)
    SnutCollection collection;
    @OneToMany(fetch = FetchType.LAZY)
    Member writer;
}