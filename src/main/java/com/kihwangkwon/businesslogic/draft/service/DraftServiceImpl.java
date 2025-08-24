package com.kihwangkwon.businesslogic.draft.service;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.kihwangkwon.businesslogic.draft.domain.DraftProcess;
import com.kihwangkwon.businesslogic.draft.domain.DraftRequest;
import com.kihwangkwon.businesslogic.draft.domain.DraftRequestType;
import com.kihwangkwon.businesslogic.draft.domain.DraftTeam;
import com.kihwangkwon.businesslogic.draft.handler.DraftHandler;
import com.kihwangkwon.businesslogic.draft.repository.DraftBidRepository;
import com.kihwangkwon.businesslogic.draft.repository.DraftInfoRepository;
import com.kihwangkwon.businesslogic.draft.repository.DraftTeamRepository;
import com.kihwangkwon.businesslogic.draftchat.service.DraftChatService;
import com.kihwangkwon.businesslogic.player.domain.OfficialPlayerRating;
import com.kihwangkwon.businesslogic.player.repository.OfficialPlayerRatingRepository;

import lombok.RequiredArgsConstructor;

import com.google.gson.Gson;
import com.kihwangkwon.businesslogic.draft.domain.DraftBid;
import com.kihwangkwon.businesslogic.draft.domain.DraftInfo;

@RequiredArgsConstructor
@Service
public class DraftServiceImpl implements DraftService {
	
	private DraftChatService draftChatService;
	private DraftBidRepository draftBidRepository;
	private DraftTeamRepository draftTeamRepository;
	private DraftInfoRepository draftInfoRepository;
	private OfficialPlayerRatingRepository officialPlayerRatingRepository;
	
	
	private DraftInfo draftInfo;
	
	private DraftProcess draftPorcess;

	private List<DraftBid> bidResult;
	
	private List<DraftBid> playerBids;
	
	private List<DraftTeam> teamList;
	
	private Map<String,List> draftList;

	
	private int timeGuide = 15;
	
	private int timeRemain;
	
	private int season;
	
	@Override
	public void draftStart(int season) {
		
		this.season = season;
		
		//소켓 확인
		
		//참가 팀 목록 로딩
		List<Order> sortList = null;
		
		Order order = Order.asc("pick");
		sortList.add(order);
		Sort sort = Sort.by(sortList);
				
		List<DraftTeam> teamList = draftTeamRepository.findBySeasonAndBiddable(season, true, sort);
		//현재 드래프트 프로세스 로딩
		
		//드래프트 결과 로딩
		
		
		//비드 로딩
		
		//시간 초기화
	}

	@Override
	public void setNominateTime(int second) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setbidTime(int second) {
		// TODO Auto-generated method stub
		
	}
		
	@Override
	public void whileBid(DraftBid playerBid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void whileNominate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void autoNominate() {
		
	}
	
	@Override
	public void bidApproval(DraftBid playerBid) {
		//비드 승인 시키고
		playerBid.setApproval(true);
		
		//저장
		draftBidRepository.save(playerBid);
		
		//비딩 대상에서 제외하고 노미네이트 메뉴 호출
				
		
	}

	@Override
	public void draftFinish(int season) {
		// TODO Auto-generated method stub
		
	}
	
	
	// 로스터 다 찬 사람들 노미네이트 못하게 막는 기능
	private void passNominate() {
		
	}
	
	
	//비드를 확인하고 적용
	private boolean checkBid(DraftBid playerBid) {
		boolean result=false;
		
		//비딩한 사람 로스터 수 확인
		boolean roasterLimit = checkBiderRoasterCount(playerBid);
				
		//개인 비딩 가격 제한 확인
		boolean price = checkBidPrice(playerBid);
		
		//현재 최고가 보다 높은 가격인지 확인
		boolean isHighestPrice = checkHighestPrice(playerBid);

		//세가지 체크 다 될경우 트루 리턴해서 비딩 적용
		if(roasterLimit&&price&&isHighestPrice) {
			result = true;
		}
		
		return result;
	}

	private boolean checkBidPrice(DraftBid playerBid) {
		boolean result = false;
		
		return result;
	}
	
	private boolean checkHighestPrice(DraftBid playerBid) {
		boolean result = false;
		DraftBid lastBid = playerBids.get(playerBids.size()-1);
		int lastPrice = lastBid.getBidPrice();
		if(playerBid.getBidPrice()>lastPrice) {
			result = true;
		}
		return result;

	}
	
	private boolean checkBiderRoasterCount(DraftBid playerBid) {
		boolean result = false;
		
		return result;
	}

	@Override
	public WebSocketMessage<?> messageEntry(WebSocketSession session, WebSocketMessage<?> webSocketMessage) {
		Object resultObject = null;
		
		String payLoad = webSocketMessage.getPayload().toString();
		Gson gson = new Gson();
		DraftRequest request = gson.fromJson(payLoad, DraftRequest.class);
		
		DraftRequestType requestType = request.getRequestType();
		
		
		//로그인 이후 소켓열릴때 보내게 처리
		if(requestType.equals(DraftRequestType.login)) {
			resultObject = draftChatService.chatSave(request.getDraftChat());
		}
		
		//접속 끊어질 때 자동으로 이 메시지 보내게 하고 로그 아웃 처리
		if(requestType.equals(DraftRequestType.logout)) {
			resultObject = draftChatService.chatSave(request.getDraftChat());
		}
		
		
		
		if(requestType.equals(DraftRequestType.chat)) {
			resultObject = draftChatService.chatSave(request.getDraftChat());
		}
		
		//입찰인 경우
		if(requestType.equals(DraftRequestType.bid)){
			//resultObject = draftService
		}
		
		request.getDraftChat().setPlayerId("플레이어아이디이이미424");
		TextMessage result = objcetToPayLoad(request);
		return result;
	}

	private TextMessage objcetToPayLoad(Object object) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(object);
		CharSequence jsonChar = jsonString;
		return new TextMessage(jsonChar);
	}

	@Override
	public boolean login(DraftTeam draftTeam) {
		
		boolean result = false;
		
		int season = draftTeam.getSeason();
		String teamName = draftTeam.getTeamName();
		String password = draftTeam.getPassword();
		
		draftTeam = draftTeamRepository.findBySeasonAndTeamNameAndPassword(season, teamName, password);
	
		//로그인 확인 된 경우
		if(draftTeam!=null) {
			result = true;
		}
		
		return result;
	}
	
	//소켓 연결시 접속자 확인용
	private WebSocketMessage setLoginSession(WebSocketMessage<?> webSocketMessage) {
		return null;
	}
	
	//소켓 연결해제시 접속자 처리용
	private WebSocketMessage removeLoginSession(WebSocketMessage<?> webSocketMessage) {
		return null;
	}
	
	private void sendToAllSocket(WebSocketMessage<?> webSocketMessage) throws Exception {
		List<WebSocketSession> socketList = DraftHandler.getSocketList();
		for(WebSocketSession session:socketList) {
			session.sendMessage(webSocketMessage);
		}
		
	}

	@Override
	public boolean logout(DraftTeam draftTeam) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean biddability(DraftBid draftBid) {
		boolean result = false;
		
		//비드한 팀이 선수 자리가 있는지
		boolean isRoasterLeft = isTeamAbleToAddPlayer(draftBid);
		
		
		
		//금액이 제안 가능한 금액인지
		
		//기존 제시 금액보다 1원이상 높은지
		boolean isHighestPrice = false;
		
		
		
		if(isHighestPrice) {
			result = true;
		}
		
		return result;
	}
	
	private boolean isTeamAbleToAddPlayer(DraftBid draftBid) {
		boolean isRoasterLeft = false;
		int maxPlayerCount = draftInfo.getPlayerPerTeam();
		
		//
		List<Order> orders = null;
		//Order order = new;
		
		Sort sort = Sort.by(orders);
		List<DraftBid> approvalBidList = draftBidRepository.findBySeasonAndApprovalAndBidTeam(draftBid.getSeason(), true, draftBid.getBidTeam(), sort);
		int approvedCount = 0;
		
		try{
			approvedCount = approvalBidList.size();
		}catch (Exception e) {
			//널 익셉션 = 아직 승인된 애 없음 그냥 0으로 가면 됨 처리 필요 X
		}
		
		if(maxPlayerCount > approvedCount) {
			isRoasterLeft = true;
		}
		
		return isRoasterLeft;
	}
	
	private boolean checkBidApporval(DraftBid draftBid) {	
		boolean result = false;
		
		//비드 가능한지 비더빌리티로 확인하고
		
		//이미 승인 된적이 없는지 DB에서 다시한번 체크
		//시즌, playerId로 approval = true 있는지 검색 
		boolean isNotApprovedYet = false;
		
		if(isNotApprovedYet) {
			result = true;
		}
		
		return result;
	}

	@Override
	public void bid(DraftBid draftBid) {
		boolean biddable = biddability(draftBid);
		
		
		//비드 가능한 상황에서 온 비드면
		if(biddable) {
			//db에 저장하고
			draftBidRepository.save(draftBid);
			
			//카운트 리셋
			
		}
		
		
		
		
	}
	
	@Override
	public void bidTimer() {
		System.out.println("10초 뒤 시작예정");
		timeRemain = timeGuide;
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask(){
		    @Override
		    public void run() {
		    //TODO Auto-generated method stub
		    	draftBidRepository.findFirstBySeasonOrderByIdDesc(season, null);
				if(timeRemain >= 0){ //count값이 5보다 작거나 같을때까지 수행
					System.out.println("[카운트다운 : "+timeRemain+"]");
					timeRemain--; //실행횟수 증가 
				}
				
				else{
					timer.cancel(); //타이머 종료
					System.out.println("[카운트다운 : 종료]");
				}
		    }	
		};
		timer.schedule(task, 10000, 1000); //실행 Task, 1초뒤 실행, 1초마다 반복
	}
	
	
	private void remainTimeRenewal(int time) {
		timeRemain = time;
	} 
	
	
	private int nominateOrder() {
		return  0;
	}
	
	//노미네이트 대기하다가
	//비드로 넘어감
	//다 끝나면 타이머 멈추면 다 끝날듯?
	
	//순서 찾는거 중간에 다하고 나가는 애들 감안해야됨
	
	List<OfficialPlayerRating> playerRest(int version){
		Sort sort = Sort.by(Direction.ASC, "cost");
		
		List<OfficialPlayerRating> allPlayer = officialPlayerRatingRepository.findByVersion(version, sort);
		return allPlayer;
	}
	
	
}
