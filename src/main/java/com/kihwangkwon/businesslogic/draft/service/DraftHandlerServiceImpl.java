package com.kihwangkwon.businesslogic.draft.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;
import com.kihwangkwon.businesslogic.draft.domain.DraftBid;
import com.kihwangkwon.businesslogic.draft.domain.DraftInfo;
import com.kihwangkwon.businesslogic.draft.domain.DraftProcess;
import com.kihwangkwon.businesslogic.draft.domain.DraftRequest;
import com.kihwangkwon.businesslogic.draft.domain.DraftRequestType;
import com.kihwangkwon.businesslogic.draft.handler.DraftHandler;
import com.kihwangkwon.businesslogic.draftchat.service.DraftChatService;

@Service
public class DraftHandlerServiceImpl implements DraftHandlerService{
	
	@Autowired
	public DraftHandlerServiceImpl(
			//DraftHandler draftHandler, 
			DraftService draftService
			, DraftChatService draftChatService
			) {
		//this.draftHandler = draftHandler;
		this.draftService = draftService;
		this.draftChatService = draftChatService;
	}
	
	//private DraftHandler draftHandler;
	private DraftService draftService;
	private DraftChatService draftChatService;
	
	private DraftInfo draftInfo;
	private DraftProcess draftProcess;
	private DraftBid onBid;
	private int leftTIme;
	
	//메세지 오자마자 여기로 보내서 어디로 보낼지 판별
	public WebSocketMessage messageArrive(WebSocketSession session, WebSocketMessage<?> message){
		Object resultObject = null;
		WebSocketMessage resultMessage = null;
		String payLoad = message.getPayload().toString();
		Gson gson = new Gson();
		DraftRequest request = gson.fromJson(payLoad, DraftRequest.class);
		/*
		 request.getDraftChat().setPlayerId("플레이어아이디이이미424");
		
		System.out.println(request);
		TextMessage result = objcetToPayLoad(request);
		*/
		//메시지 보낼곳 판별해서 보내고
		DraftRequestType requestType = request.getRequestType();
		// 채팅인 경우
		
		if(requestType.equals(DraftRequestType.login)) {
			resultObject = draftChatService.chatSave(request.getDraftChat());
		}
		
		if(requestType.equals(DraftRequestType.chat)) {
			resultObject = draftChatService.chatSave(request.getDraftChat());
		}
		
		//입찰인 경우
		if(requestType.equals(DraftRequestType.bid)){
			//resultObject = draftService
		}
		
		//리턴 온거 다시 메시지로 보내기
		
		resultMessage = objcetToPayLoad(resultObject);
		
		return resultMessage;
	}
	
	private TextMessage objcetToPayLoad(Object object) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(object);
		CharSequence jsonChar = jsonString;
		return new TextMessage(jsonChar);
	}
	
	
	//서버에서 자동처리해서 보낼 애들은 여기 태워서 소켓에 접속해 있는 애들에 다 보냄 다른 처리는 하지말고 메시지를 받아서 전파하는 역할만 
	public void testMethod() {
		System.out.println("ㅇㅋㅇㅋ");
		List<WebSocketSession> sockets = DraftHandler.getSocketList();
		for(WebSocketSession socket : sockets) {
			System.out.println("반복문");
			TextMessage msg = new TextMessage("sssaa");
			try {
				System.out.println("트라이");
				socket.sendMessage(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("캐치");
			}
		}
	}
	
	
}
