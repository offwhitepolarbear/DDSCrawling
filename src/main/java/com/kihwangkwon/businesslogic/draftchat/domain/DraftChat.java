package com.kihwangkwon.businesslogic.draftchat.domain;


import java.sql.Timestamp;

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
@Entity
@NoArgsConstructor
public class DraftChat {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private int season;
	private String playerId;
	private String textMessage;
	private Timestamp timestamp;
}
