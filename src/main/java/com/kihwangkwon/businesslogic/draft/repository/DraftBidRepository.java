package com.kihwangkwon.businesslogic.draft.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kihwangkwon.businesslogic.draft.domain.DraftBid;

public interface DraftBidRepository extends JpaRepository<DraftBid, Long> {
	
	//해당 선수 비드 찾기
	List<DraftBid> findBySeasonAndPlayerId(int season, String playerId, Sort sort);
	
	//드래프트 된 애들만 찾기
	List<DraftBid> findBySeasonAndApproval(int season, boolean approval, Sort sort);
	
	//팀별 드래프트 된 애들만 찾기
	List<DraftBid> findBySeasonAndApprovalAndBidTeam(int season, boolean approval, String bidTeam, Sort sort);
	
	//해당 시즌 전체 찾기
	List<DraftBid> findBySeason(int season, Sort sort);
	
	//비딩 중인 애 찾기
	List<DraftBid> findFirstBySeasonOrderByIdDesc(int season, Sort sort);	
	
}
