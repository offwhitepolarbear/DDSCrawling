package com.kihwangkwon.businesslogic.game.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kihwangkwon.businesslogic.game.domain.GameId;

public interface GameIdRepository extends JpaRepository<GameId, Long> {

}
