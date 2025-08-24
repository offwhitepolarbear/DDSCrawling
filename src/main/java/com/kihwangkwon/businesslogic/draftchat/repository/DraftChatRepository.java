package com.kihwangkwon.businesslogic.draftchat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kihwangkwon.businesslogic.draftchat.domain.DraftChat;

public interface DraftChatRepository extends JpaRepository<DraftChat, Long>{
	List<DraftChat> findBySeason(int season);
}
