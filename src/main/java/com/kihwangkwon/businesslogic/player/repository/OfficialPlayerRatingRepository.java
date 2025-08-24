package com.kihwangkwon.businesslogic.player.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kihwangkwon.businesslogic.player.domain.OfficialPlayerRating;

public interface OfficialPlayerRatingRepository extends JpaRepository<OfficialPlayerRating, Long> {
	List<OfficialPlayerRating> findByVersion(int version, Sort sort);
	List<OfficialPlayerRating> findByVersionAndPosition(int version, String position);

}
