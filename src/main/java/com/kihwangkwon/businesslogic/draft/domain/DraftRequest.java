package com.kihwangkwon.businesslogic.draft.domain;

import com.kihwangkwon.businesslogic.draftchat.domain.DraftChat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Setter
@Getter
public class DraftRequest {
	private DraftRequestType requestType;
	private DraftTeam draftTeam;
	private DraftChat draftChat;
	private DraftBid draftBid;
}
