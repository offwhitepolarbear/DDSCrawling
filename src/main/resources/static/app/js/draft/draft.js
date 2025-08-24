


draft_chat.textMessage = 'ㅇㅋㅇㅋ소ㅓㅋ셋';
draft_bid.bidPrice = 10000;

test_data.draftChat = draft_chat;
test_data.draftBid = draft_bid;
setInterval(interval_function,5000);
function interval_function(){
	websocket.send(JSON.stringify(test_data));
	//websocket.send("ddzz44");
}