package com.kihwangkwon.businesslogic.draft.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class DraftInfo {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private int season;
	private int budget;
	private int playerPerTeam;
	private int bidTime;
	private int nominateTime;
	
}
