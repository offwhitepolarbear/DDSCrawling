package com.kihwangkwon.businesslogic.draft.service;

import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.kihwangkwon.businesslogic.draft.domain.DraftBid;
import com.kihwangkwon.businesslogic.draft.domain.DraftTeam;

public interface DraftService {
	boolean login(DraftTeam draftTeam);
	boolean logout(DraftTeam draftTeam);
	
	void setNominateTime(int second);
	void bid(DraftBid draftBid);
	void setbidTime(int second);
	void draftStart(int season);
	void whileBid(DraftBid playerBid);
	void whileNominate();
	void autoNominate();
	void bidApproval(DraftBid playerBid);
	void draftFinish(int season);
	WebSocketMessage<?> messageEntry(WebSocketSession session, WebSocketMessage<?> webSocketMessage);
	public void bidTimer();
	
}
