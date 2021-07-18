package com.kihwangkwon.businesslogic.game.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.NaturalId;

import lombok.Setter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GameId {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	private int version;
	int gameId;
	
	@Builder
	public GameId(int version, int gameId) {
		this.version = version;
		this.gameId = gameId;
	}
}
