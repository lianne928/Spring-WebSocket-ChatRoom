window.onload = function(){
	let myDrawer = document.getElementById("myDrawer");
	let webSocket = new WebSocket("ws://10.0.101.187:8080/ws");
	let isConnect = false;

	webSocket.onopen = function(){
		isConnect = true;
	}
	webSocket.onmessage = function(event){
		if (isConnect){
			let mesgObj = JSON.parse(event.data);
			if (mesgObj.isClear){
				clear()
			}else {
				if (mesgObj.isNewLine){
					newLine(mesgObj.x, mesgObj.y);
				}else{
					drawLine(mesgObj.x, mesgObj.y);
				}
			}
		}

	}
	webSocket.onclose = function(){
		isConnect = false;
	}
	webSocket.onerror = function(event){
		console.log("onError:" + event);
	}
	//--------------------------------

	let ctx = myDrawer.getContext("2d");

	function clear(){
		ctx.clearRect(0,0,myDrawer.width, myDrawer.height);
	}
	function newLine(x,y){
		ctx.beginPath();
		ctx.lineWidth = 4;
		ctx.moveTo(x, y);
	}
	function drawLine(x,y){
		ctx.lineTo(x, y);
		ctx.stroke();
	}
}