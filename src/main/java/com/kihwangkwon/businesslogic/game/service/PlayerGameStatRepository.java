package com.kihwangkwon.businesslogic.game.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kihwangkwon.businesslogic.game.domain.PlayerGameStat;

public interface PlayerGameStatRepository extends JpaRepository<PlayerGameStat, Long> {

}
