package com.kihwangkwon.businesslogic.draftchat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kihwangkwon.businesslogic.draftchat.domain.DraftChat;
import com.kihwangkwon.businesslogic.draftchat.repository.DraftChatRepository;

@Service
public class DraftChatServiceImpl implements DraftChatService {
	
	@Autowired
	public DraftChatServiceImpl(DraftChatRepository draftChatRepository) {
		this.draftChatRepository = draftChatRepository;
	}
	
	private DraftChatRepository draftChatRepository;
	
	@Override
	public List<DraftChat> chatList(int season) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DraftChat chatSave(DraftChat chat) {
		chat = draftChatRepository.save(chat);
		return chat;
	}

}
