package com.kihwangkwon.businesslogic.draft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kihwangkwon.businesslogic.draft.domain.DraftInfo;

public interface DraftInfoRepository extends JpaRepository<DraftInfo, Long>{

}
