package com.kihwangkwon.businesslogic.game.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kihwangkwon.businesslogic.game.domain.GameId;

public interface GameIdRepository extends JpaRepository<GameId, Long> {
	List<GameId> findByVersionOrderByGameIdAsc(int version);
	GameId findByVersionAndGameId(int version, int gameId);
}
