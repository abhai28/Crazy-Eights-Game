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
           let msg = JSON.parse(greeting.body)
           if(msg.content === "id"){
               if(id==="0"){
                   id = msg.id;
                   let x = document.createElement("LABEL");
                   let t = document.createTextNode("Player: "+id);
                   x.appendChild(t);
                   document.getElementById("nav").appendChild(x);
               }
               if(msg.id === "4" && id ==="1"){
                   document.getElementById("startBtn").style.visibility="visible";
               }
           }
           else if(msg.content === "Start"){
               let topCard = document.createElement("IMG");
               topCard.setAttribute("src","/cards/"+msg.topCard+".svg")
               topCard.setAttribute("class","card")
               document.getElementById("topCardCol").appendChild(topCard)
               addScore("1",msg.score1)
               addScore("2",msg.score2)
               addScore("3",msg.score3)
               addScore("4",msg.score4)
               if(id==="1"){
                   parseStartCards(msg.card1)
               }
               else if(id==="2"){
                   parseStartCards(msg.card2)
               }
               else if(id==="3"){
                   parseStartCards(msg.card3)
               }
               else if(id==="4"){
                   parseStartCards(msg.card4)
               }
               console.log(cards)
               addCards()
               document.getElementById("draw").style.visibility = "visible";
           }
           else if(msg.content ==="draw"){
               if(msg.id===id){
                   addCard(msg.card);
               }
           }
          /* if(msg.length ===2){
               if(id === "0"){
                   id = msg[1]
                   let x = document.createElement("LABEL");
                   let t = document.createTextNode("Player: "+id);
                   x.appendChild(t);
                   document.getElementById("nav").appendChild(x);
               }
               if(msg[1] === "4" && id ==="1"){
                   document.getElementById("startBtn").style.visibility="visible";
               }
               console.log("id: "+id)
           }
           else{
               if(msg[0]==="Start"){
                   let topCard = document.createElement("IMG");
                   topCard.setAttribute("src","/cards/"+msg[2]+".svg")
                   topCard.setAttribute("class","card")
                   document.getElementById("topCardCol").appendChild(topCard)
                   parseScore(msg);
                   for(let j=0;j<score.length;j++){
                       let s = document.createElement("LI");
                       s.setAttribute("id","p"+(j+1))
                       s.innerHTML = "Player "+(j+1)+": "+score[j];
                       document.getElementById("scoreList").appendChild(s)
                   }
                   parseStartCards(msg);
                   addCards()
                   document.getElementById("draw").style.visibility = "visible";
               }
           }*/
       })
    });
}

function addScore(id, score){
    let s = document.createElement("LI");
    s.setAttribute("id","p"+id)
    s.innerHTML = "Player "+id+": "+score;
    document.getElementById("scoreList").appendChild(s)
}
function addCard(c){
    let card = document.createElement("INPUT")
    card.setAttribute("src","/cards/"+c+".svg")
    card.setAttribute("type","image")
    card.setAttribute("id",c);
    card.setAttribute("class","card")
    card.setAttribute("onclick","sendCard('"+c+"')")
    document.getElementById("hand").appendChild(card)
}
function addCards (){
    for(let i=0;i<cards.length;i++){
        let card = document.createElement("INPUT")
        card.setAttribute("src","/cards/"+cards[i]+".svg")
        card.setAttribute("type","image")
        card.setAttribute("id",cards[i]);
        card.setAttribute("class","card")
        card.setAttribute("onclick","sendCard('"+cards[i]+"')")
        document.getElementById("hand").appendChild(card)
    }
}

function sendCard(card){
    console.log(card)
    stompClient.send("/app/playCard", {}, JSON.stringify({'name':id+" "+card}));
}

function parseStartCards(msg){
    cards = msg.split(" ")
    cards.shift()
}

function parseScore(msg){
    for(let i=0;i<msg.length;i++){
        if(msg[i]==="Score"){
            score[0] = msg[i+1]
            score[1] = msg[i+2]
            score[2] = msg[i+3]
            score[3] = msg[i+4]
            break;
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

function drawCard(){
    stompClient.send("/app/drawCard",{},JSON.stringify({'name':id}))
}

window.onload = connect();

$(function(){
    $("#usernameBtn").click(function(){sendName();});
    $("#startBtn").click(function (){startGame();});
    $("#draw").click(function (){drawCard()})
})