package com.kihwangkwon.businesslogic.draft.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.support.WebSocketHttpRequestHandler;

import com.kihwangkwon.businesslogic.draft.domain.DraftRequest;
import com.kihwangkwon.businesslogic.draft.domain.DraftTeam;
import com.kihwangkwon.businesslogic.draft.service.DraftHandlerService;
import com.kihwangkwon.businesslogic.draft.service.DraftService;
@Component
public class DraftHandler implements WebSocketHandler{
	
	@Autowired
	public DraftHandler(DraftService draftService) {
		this.draftService = draftService;
	}

	private DraftService draftService;

	private static List<WebSocketSession> socketList = new ArrayList<>();
	private static Set<DraftTeam> loginList;
	private static Map<String,String> loginSessionIds;
	
	public static List getSocketList() {
		return socketList;
	}
	
	public static Set getLoginTeamList() {
		return loginList;
	}
	
	public static Map getSessionIdTeamMap() {
		return loginSessionIds;
	}
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		socketList.add(session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		socketList.remove(session);
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub
	}

	
	//메시지 올경우 세션과 메시지를 전부 드래프트 서비스로 이전
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		draftService.messageEntry(session, message);		
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}
}
