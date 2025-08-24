package com.kihwangkwon.businesslogic.draftchat.service;

import java.util.List;

import com.kihwangkwon.businesslogic.draftchat.domain.DraftChat;

public interface DraftChatService {

	public List<DraftChat> chatList(int season);
	public DraftChat chatSave(DraftChat chat);
}
