package com.kihwangkwon.businesslogic.game.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kihwangkwon.businesslogic.game.domain.TeamGameStat;

public interface TeamGameStatRepository extends JpaRepository<TeamGameStat, Long> {

}
