package com.kihwangkwon.businesslogic.draft.service;

import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public interface DraftHandlerService {
	WebSocketMessage messageArrive(WebSocketSession session, WebSocketMessage<?> message);
	public void testMethod();
}
