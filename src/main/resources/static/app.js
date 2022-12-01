var stompClient = null;
let id = "0";
let username = "";
let direction = "";
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
               topCard.setAttribute("id",msg.topCard)
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
               if(msg.currentPlayer===id){
                   $("#hand :input").attr("disabled",false);
                   $("draw").attr("disabled",false);
               }
               document.getElementById("draw").style.visibility = "visible";
               direction = msg.direction;
               let x = document.createElement("LABEL");
               let t = document.createTextNode(direction);
               x.setAttribute("id","direction")
               x.appendChild(t);
               document.getElementById("nav").appendChild(x);
           }
           else if(msg.content ==="draw"){
               if(msg.id===id){
                   addCard(msg.card);
               }
           }
           else if(msg.content==="ChangeSuit"){
               let c = $(".card");
               c.setAttribute("src","/cards/"+msg.suit+".jpg")
               c.setAttribute("id",msg.suit)
               if(msg.turn === "id"){
                   $("#hand :input").attr("disabled",false);
                   $("draw").attr("disabled",false);
               }
           }
           else if(msg.content === "Played"){
               if(msg.id===id){
                   parseStartCards(msg.card)
                   $("#hand").innerHTML = ''
                   addCards()
                   $("#hand :input").attr("disabled",true);
                   $("draw").attr("disabled",true);
               }
               if(msg.turn===id){
                   $("#hand :input").attr("disabled",true);
                   $("draw").attr("disabled",true);
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
        card.setAttribute('disabled','');
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

function changeSuit(suit) {
    document.getElementById("spade").style.visibility="hidden"
    document.getElementById("heart").style.visibility="hidden"
    document.getElementById("club").style.visibility="hidden"
    document.getElementById("diamond").style.visibility="hidden"
    stompClient.send("/app/changeSuit",{},JSON.stringify({'name':suit}))
}

$(function(){
    $("#usernameBtn").click(function(){sendName();});
    $("#startBtn").click(function (){startGame();});
    $("#draw").click(function (){drawCard()})
    $("spade").click(function (){changeSuit("S")})
    $("heart").click(function (){changeSuit("H")})
    $("club").click(function (){changeSuit("C")})
    $("diamond").click(function (){changeSuit("D")})
})