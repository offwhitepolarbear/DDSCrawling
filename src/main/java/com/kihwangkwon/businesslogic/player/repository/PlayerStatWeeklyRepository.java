package com.kihwangkwon.businesslogic.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kihwangkwon.businesslogic.player.domain.PlayerStatWeekly;

public interface PlayerStatWeeklyRepository extends JpaRepository<PlayerStatWeekly, Long> {

}
