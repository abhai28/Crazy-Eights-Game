var stompClient = null;
let id = "0";
let username = "";

let cards = []

function connect(){
    var socket = new SockJS('/crazy8-websocket');
    stompClient = Stomp.over(socket);
    console.log('hello');
    stompClient.connect({},function (frame){
       console.log('Connected: '+frame);
       stompClient.subscribe('/topic/message', function (greeting){
           let msg = parseMessage(JSON.parse(greeting.body).content)
           console.log(msg[0])
           console.log("hello2")
           if(msg.length ===2){
               if(id === "0"){
                   id = msg[1]
               }
               if(msg[1] === "4" && id ==="1"){
                   document.getElementById("startBtn").style.visibility="visible";
               }
               console.log("id: "+id)
           }
           else{
               if(msg[0]==="Start"){
                   let topBtn = document.createElement("IMG");
                   topBtn.setAttribute("src","/cards/"+msg[2]+".svg")
                   topBtn.setAttribute("class","card")
                   document.getElementById("topCardCol").appendChild(topBtn)
                   parseStartCards(msg);
                   addCards()
               }
           }
       })
    });
}


function addCards (){
    for(let i=0;i<cards.length;i++){
        let card = document.createElement("INPUT")
        card.setAttribute("src","/cards/"+cards[i]+".svg")
        card.setAttribute("type","image")
        card.setAttribute("id",cards[i]);
        card.setAttribute("class","card")
        document.getElementById("hand").appendChild(card)
    }
}

function parseStartCards(msg){
    for(let i=0;i<msg.length;i++){
        if(msg[i]===id){
            for(let j=i+1;j<=i+5;j++){
                cards.push(msg[j])
            }
        }
    }
}

function sendName(){
    document.getElementById("usernameBtn").style.visibility="hidden";
   stompClient.send("/app/connect", {}, JSON.stringify({'name':""}));
}

function parseMessage(message){
    let m = message.split(" ");
    console.log(m)
    return m;
}

function startGame(){
    document.getElementById("startBtn").style.visibility="hidden"
    stompClient.send("/app/start",{},JSON.stringify({'name':""}))
}

window.onload = connect();

$(function(){
    $("#usernameBtn").click(function(){sendName();});
    $("#startBtn").click(function (){startGame();})
})