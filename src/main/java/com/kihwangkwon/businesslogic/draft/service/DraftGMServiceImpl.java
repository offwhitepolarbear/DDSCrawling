package com.kihwangkwon.businesslogic.draft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kihwangkwon.businesslogic.draft.domain.DraftBid;
@Service
public class DraftGMServiceImpl implements DraftGMService {

	private DraftService draftService;
	
	@Autowired
	public DraftGMServiceImpl(DraftService draftService) {
		this.draftService = draftService;
	}
	
	@Override
	public void nominatePlayer(DraftBid playerBid) {
		// TODO Auto-generated method stub
	}

	@Override
	public void bidOffer(DraftBid playerBid) {
		// TODO Auto-generated method stub
	}

}
