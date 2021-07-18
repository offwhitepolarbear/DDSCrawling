package com.kihwangkwon.businesslogic.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kihwangkwon.businesslogic.player.domain.PlayerRating;

public interface PlayerRatingRepository extends JpaRepository<PlayerRating, Long> {

}
