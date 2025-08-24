var host_url_full = window.location.href;

host_url_full = host_url_full.split('/');

var socket_url = "ws://"+host_url_full[2]+"/socket";

var info_uri = window.location.pathname;

info_uri = info_uri.split('/')

var gm_info = new Object();
gm_info.season = info_uri[2];
gm_info.teamName = info_uri[3];
gm_info.password = info_uri[4];

var test_data = new Object();

var draft_chat = new Object();
var draft_bid = new Object();

var websocket;


gm_login()

function gm_login(){
	console.log(gm_info);
		var login_url = '/draft/login';
		var data = gm_info;
		fetch(login_url, 
			{
			  method: 'POST', // or 'PUT'
			  body: JSON.stringify(data),
			  headers:{
			    'Content-Type': 'application/json'
			  }
			}
		)
		.then(res => res.json())
		.then(response => loginCheck(response))
		.catch(error => console.error('Error:', error));
	
}

function loginCheck(response){
	
	//로그인 실패시
	if(response==false){
		alert('주소를 다시 확인해 주세요');
	}
	//로그인 성공시
	if(response==true){
		//웹 소켓 연결
		socket_connect(gm_info)
	}
}

function socket_connect(gm_info){
	//gm_info
	websocket = new WebSocket(socket_url);

	//웹소켓 오픈 이벤트
	websocket.onopen = function (evt) {
		
	    console.log("열림"+evt);
	    websocket.send(JSON.stringify(gm_info));
	};

	//웹소켓 메시지 이벤트
	websocket.onmessage = function (evt) {
		 console.log("뭔가옴"+evt);
		 console.log(evt);
	};
	//웹소켓 에러 이벤트
	websocket.onerror = function (evt) {
		 console.log("에러남"+evt);
		 console.log(evt);
		 console.log(evt.code);
		 console.log(JSON.stringify(evt));
		 
	};
}

//var websocket = new WebSocket(socket_url);
