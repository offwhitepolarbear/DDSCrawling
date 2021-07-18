package com.kihwangkwon.businesslogic.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kihwangkwon.businesslogic.player.domain.PlayerStat;

public interface PlayerStatRepository extends JpaRepository<PlayerStat, Long> {

}
