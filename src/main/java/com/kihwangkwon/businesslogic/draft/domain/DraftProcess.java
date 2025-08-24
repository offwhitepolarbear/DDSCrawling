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
public class DraftProcess {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private int season;
	
	//드래프트 진행중
	private boolean onProcess;
	//노미네이트 단계
	private boolean onNominate;
	//비딩단계
	private boolean onBid;
	//잔여시간 초
	private int timeLeft;
	
}
