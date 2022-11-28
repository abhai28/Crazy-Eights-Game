var stompClient = null;
let id = "0";
let username = "";

function connect(){
    var socket = new SockJS('/crazy8-websocket');
    stompClient = Stomp.over(socket);
    console.log('hello');
    stompClient.connect({},function (frame){
       console.log('Connected: '+frame);
       stompClient.subscribe('/topic/message', function (greeting){
           let msg = parseMessage(JSON.parse(greeting.body).content)
           if(msg.length ===2){
               if(id === "0"){
                   id = msg[1]
               }
               if(msg[1] === "4" && id ==="1"){
                   document.getElementById("startBtn").style.visibility="visible";
               }
           }
           else{
               if(msg[0]===id){
                    console.log(msg)
               }
           }
       })
    });
}



function sendName(){
    let n = $("#user")
    username = n.val()
   stompClient.send("/app/connect", {}, JSON.stringify({'name':n.val()}));
}

function parseMessage(message){
    return message.split(" ");
}

function startGame(){
    stompClient.send("/app/start",{},JSON.stringify({'name':""}))
}

window.onload = connect();
window.onload =

$(function(){
    $("#usernameBtn").click(function(){sendName();});
    $("#startBtn").click(function (){startGame();})
})