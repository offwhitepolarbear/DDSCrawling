package com.kihwangkwon.businesslogic.player.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kihwangkwon.businesslogic.player.domain.PlayerStat;

public interface PlayerStatRepository extends JpaRepository<PlayerStat, Long> {
	List<PlayerStat> findByVersion(int version);
}
