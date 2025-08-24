package com.kihwangkwon.businesslogic.draft.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ToString
@Setter
@Getter
@NoArgsConstructor
@Entity
public class DraftBid {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private int season;
	private int pick;
	private String playerId;
	private String bidTeam;
	private int bidPrice;
	private boolean approval;
}

