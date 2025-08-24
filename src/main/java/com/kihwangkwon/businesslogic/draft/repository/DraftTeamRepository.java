package com.kihwangkwon.businesslogic.draft.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kihwangkwon.businesslogic.draft.domain.DraftTeam;

public interface DraftTeamRepository extends JpaRepository<DraftTeam, Long> {
	DraftTeam findBySeasonAndTeamNameAndPassword(int season, String teamName, String password);
	List<DraftTeam> findBySeasonAndBiddable(int season, boolean biddable, Sort sort);
}