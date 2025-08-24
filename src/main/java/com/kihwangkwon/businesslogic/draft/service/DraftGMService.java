package com.kihwangkwon.businesslogic.draft.service;

import com.kihwangkwon.businesslogic.draft.domain.DraftBid;

public interface DraftGMService {
	void nominatePlayer(DraftBid playerBid);
	void bidOffer(DraftBid playerBid);
}
