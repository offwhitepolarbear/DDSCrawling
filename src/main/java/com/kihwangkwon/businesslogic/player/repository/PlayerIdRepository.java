package com.kihwangkwon.businesslogic.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kihwangkwon.businesslogic.player.domain.PlayerId;

public interface PlayerIdRepository extends JpaRepository<PlayerId, Long> {

}
