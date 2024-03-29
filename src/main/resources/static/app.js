var stompClient = null;
let id = "0";
let username = "";
let direction = "";
let cards = [];

/*
  Connect to the server at the /crazy8-websocket endpoint. The SockJS library automatically adds /sockjs to the path.
  The connection is made using the STOMP protocol.
  The connection is asynchronous and when it completes, the callback function is called.
  The callback function is passed the connection frame.
 */
function connect() {
  return new Promise((resolve, reject) => {
    var socket = new SockJS("/crazy8-websocket");
    stompClient = Stomp.over(socket);
    console.log("Attempting connection...");
    stompClient.connect(
      {},
      function (frame) {
        console.log("Connected: " + frame);
        resolve();
      },
      function (error) {
        reject(error);
      }
    );
  });
}

/*
  addScore() is called at end of round and game to display the score of each player.
  It takes in the id of the player and their score.
  It creates a list item and appends it to the scoreList.
*/
function addScore(id, score) {
  let s = document.createElement("LI");
  s.setAttribute("id", "p" + id);
  s.innerHTML = "Player " + id + ": " + score;
  document.getElementById("scoreList").appendChild(s);
}

/*
  addCard() is called when a player draws a card.
  It takes in the card that was drawn and creates an input element with the card as the image.
  It then appends the card to the hand div.
*/
function addCard(c) {
  let card = document.createElement("INPUT");
  card.setAttribute("src", "/cards/" + c + ".svg");
  card.setAttribute("type", "image");
  card.setAttribute("id", c);
  card.setAttribute("class", "card");
  card.setAttribute("onclick", "sendCard('" + c + "')");
  document.getElementById("hand").appendChild(card);
}
/*
  addCards() is called at the start of the game to add the cards to the hand div.
  It creates an input element for each card and appends it to the hand div.
*/
function addCards() {
  for (let i = 0; i < cards.length; i++) {
    let card = document.createElement("INPUT");
    card.setAttribute("src", "/cards/" + cards[i] + ".svg");
    card.setAttribute("type", "image");
    card.setAttribute("id", cards[i]);
    card.setAttribute("class", "card");
    card.setAttribute("onclick", "sendCard('" + cards[i] + "')");
    card.setAttribute("disabled", "");
    document.getElementById("hand").appendChild(card);
  }
}

/*
  sendCard() is called when a player clicks on a card.
  It takes in the card that was clicked and sends it to the server.
*/
function sendCard(card) {
  console.log(card);
  stompClient.send(
    "/app/playCard",
    {},
    JSON.stringify({ name: id + " " + card })
  );
}

/*
  parseStartCards() is called at the start of the game to parse the cards that are sent from the server.
  It takes in the cards that are sent from the server and splits them into an array.
*/
function parseStartCards(msg) {
  cards = msg.split(" ");
  cards.shift();
}

/*
  sendName() is called when the user clicks the username button.
  It takes in the username and sends it to the server.
*/
function sendName() {
  document.getElementById("usernameBtn").style.visibility = "hidden";
  console.log("test1");
  stompClient.send("/app/connect", {}, JSON.stringify({ name: "" }));
}

/*
  parseMessage() is called when a message is received from the server.
  It takes in the message and splits it into an array.
*/
function parseMessage(message) {
  let m = message.split(" ");
  console.log(m);
  return m;
}

/*
  startGame() is called when the user clicks the start button.
  It sends a message to the server to start the game.
*/
function startGame() {
  document.getElementById("startBtn").style.visibility = "hidden";
  stompClient.send("/app/start", {}, JSON.stringify({ name: "" }));
}

/*
  drawCard() is called when the user clicks the draw button.
  It sends a message to the server to draw a card.
*/
function drawCard() {
  stompClient.send("/app/drawCard", {}, JSON.stringify({ name: id }));
}

/*
  changeSuit() is called when the user clicks on a suit.
  It takes in the suit that was clicked and sends it to the server.
*/
function changeSuit(suit) {
  document.getElementById("spade").style.visibility = "hidden";
  document.getElementById("heart").style.visibility = "hidden";
  document.getElementById("club").style.visibility = "hidden";
  document.getElementById("diamond").style.visibility = "hidden";
  stompClient.send("/app/changeSuit", {}, JSON.stringify({ name: suit }));
}

window.onload = () => {
  connectToServer()
    .then(() => {
      console.log("Connection to server established!");
    })
    .catch((error) => {
      console.error("Connection error: ", error);
    });
};

/*
  connectToServer() is called when the page loads.
  It attempts to connect to the server.
  If it is successful, it sets up the subscriptions.
*/
async function connectToServer() {
  try {
    await connect();
    document.getElementById("usernameBtn").style.visibility = "visible";
    setupSubscriptions();
  } catch (error) {
    console.error("An error occurred during connection:", error);
  }
}

/*
  setupSubscriptions() is called when the connection to the server is established.
  It sets up the subscriptions to the topics that the client will be listening to.
*/
function setupSubscriptions() {
  stompClient.subscribe("/topic/message", function (greeting) {
    let msg = JSON.parse(greeting.body);
    console.log("message", msg);

    if (msg.content === "id") {
      if (id === "0") {
        id = msg.id;
        let x = document.createElement("LABEL");
        let t = document.createTextNode("Player: " + id);
        x.setAttribute("id", "playerID");
        x.appendChild(t);
        document.getElementById("nav").appendChild(x);
      }
      if (msg.id === "4" && id === "1") {
        document.getElementById("startBtn").style.visibility = "visible";
      }
    } else if (msg.content === "Start") {
      let topCard = document.createElement("IMG");
      topCard.setAttribute("src", "/cards/" + msg.topCard + ".svg");
      topCard.setAttribute("class", "topCard");
      topCard.setAttribute("id", msg.topCard);
      document.getElementById("topCardCol").appendChild(topCard);
      $("#scoreList").html("");
      let s = document.createElement("LI");
      s.innerHTML = "Score:";
      document.getElementById("scoreList").appendChild(s);
      addScore("1", msg.score1);
      addScore("2", msg.score2);
      addScore("3", msg.score3);
      addScore("4", msg.score4);

      if (id === "1") {
        parseStartCards(msg.card1);
      } else if (id === "2") {
        parseStartCards(msg.card2);
      } else if (id === "3") {
        parseStartCards(msg.card3);
      } else if (id === "4") {
        parseStartCards(msg.card4);
      }
      addCards();
      if (msg.currentPlayer === id) {
        $("#hand :input").attr("disabled", false);
        document.getElementById("draw").disabled = false;
      }
      document.getElementById("draw").style.visibility = "visible";
      direction = msg.direction;
      let x = document.createElement("LABEL");
      let t = document.createTextNode(direction);
      x.setAttribute("id", "direction");
      x.appendChild(t);
      document.getElementById("nav").appendChild(x);
      if (document.getElementById("turnID") !== null) {
        const element = document.getElementById("turnID");
        element.remove();
      }
      let m = document.createElement("LABEL");
      let k = document.createTextNode("Turn: " + msg.currentPlayer);
      m.setAttribute("id", "turnID");
      m.appendChild(k);
      document.getElementById("nav").appendChild(m);
    } else if (msg.content === "draw") {
      if (msg.id === id) {
        addCard(msg.card);
      }
    } else if (msg.content === "ChangeSuit") {
      let ca = "/cards/" + msg.suit + ".jpg";
      $(".topCard").attr({
        src: ca,
        id: msg.suit,
      });

      if (msg.turn === id) {
        $("#hand :input").attr("disabled", false);
        $("#draw").attr("disabled", false);
      }
      $("#turnID").html("Turn: " + msg.turn);
    } else if (msg.content === "Played") {
      if (msg.id === id) {
        parseStartCards(msg.card);
        $("#hand").html("");
        addCards();
        $("#hand :input").attr("disabled", true);
        $("#draw").attr("disabled", true);
      }
      if (msg.turn === id) {
        $("#hand :input").attr("disabled", false);
        $("#draw").attr("disabled", false);
        console.log("turn");
      }
      $("#turnID").html("Turn: " + msg.turn);
      let c = "/cards/" + msg.topCard + ".svg";
      $(".topCard").attr({
        src: c,
        id: msg.topCard,
      });
    } else if (msg.content === "8Played") {
      if (msg.id === id) {
        parseStartCards(msg.card);
        $("#hand").html("");
        addCards();
        $("#hand :input").attr("disabled", true);
        $("#draw").attr("disabled", true);
        document.getElementById("spade").style.visibility = "visible";
        document.getElementById("heart").style.visibility = "visible";
        document.getElementById("club").style.visibility = "visible";
        document.getElementById("diamond").style.visibility = "visible";
      }
      let c = "/cards/" + msg.topCard + ".svg";
      $(".topCard").attr({
        src: c,
        id: msg.topCard,
      });
    } else if (msg.content === "APlayed") {
      if (msg.id === id) {
        parseStartCards(msg.card);
        $("#hand").html("");
        addCards();
        $("#hand :input").attr("disabled", true);
        $("#draw").attr("disabled", true);
      }
      if (msg.turn === id) {
        $("#hand :input").attr("disabled", false);
        $("#draw").attr("disabled", false);
      }

      let c = "/cards/" + msg.topCard + ".svg";
      $(".topCard").attr({
        src: c,
        id: msg.topCard,
      });
      $("#turnID").html("Turn: " + msg.turn);
      $("#direction").html(msg.direction);
    } else if (msg.content === "Round Over") {
      $("#hand").html("");
      $("#topCardCol").html("");
      $("#scoreList").html("");
      $("#draw").css("visibility", "hidden");
      $("#direction").remove();
      let s = document.createElement("LI");
      s.innerHTML = "Score:";
      document.getElementById("scoreList").appendChild(s);
      addScore("1", msg.score1);
      addScore("2", msg.score2);
      addScore("3", msg.score3);
      addScore("4", msg.score4);
      if (msg.nextRound === id) {
        $("#startBtn").css("visibility", "visible");
      }
      $("#turnID").html("Turn: " + msg.nextRound);
    } else if (msg.content === "Game Over") {
      $("#hand").html("");
      $("#topCardCol").html("");
      $("#scoreList").html("");
      $("#draw").css("visibility", "hidden");
      $("#direction").remove();
      let s = document.createElement("LI");
      s.innerHTML = "Score:";
      document.getElementById("scoreList").appendChild(s);
      addScore("1", msg.score1);
      addScore("2", msg.score2);
      addScore("3", msg.score3);
      addScore("4", msg.score4);
      let winTag = document.createElement("h1");
      let textNode = document.createTextNode("WINNER IS PLAYER " + msg.winner);
      winTag.appendChild(textNode);
      winTag.setAttribute("id", "winMSG");
      winTag.setAttribute("class", "display-1");
      document.getElementById("hand").appendChild(winTag);
    } else if (msg.content === "Playable") {
      if (msg.id === id) {
        $("#draw").attr("disabled", true);
        addCard(msg.card);
      }
    } else if (msg.content === "Not Playable") {
      if (msg.id === id) {
        addCard(msg.card);
        $("#hand :input").attr("disabled", true);
        $("#draw").attr("disabled", true);
      }
      if (msg.turn === id) {
        $("#hand :input").attr("disabled", false);
        $("#draw").attr("disabled", false);
      }
      $("#turnID").html("Turn: " + msg.turn);
    } else if (msg.content === "2 Played Draw") {
      if (msg.id === id) {
        parseStartCards(msg.card);
        $("#hand").html("");
        addCards();
        $("#hand :input").attr("disabled", true);
        $("#draw").attr("disabled", true);
      }
      if (msg.turn === id) {
        $("#hand :input").attr("disabled", false);
        $("#draw").attr("disabled", false);
      }
      if (msg.extraDrawID === id) {
        parseStartCards(msg.nextCard);
        $("#hand").html("");
        addCards();
      }
      $("#turnID").html("Turn: " + msg.turn);
      let c = "/cards/" + msg.topCard + ".svg";
      $(".topCard").attr({
        src: c,
        id: msg.topCard,
      });
    } else if (msg.content === "1Played") {
      if (msg.id === id) {
        parseStartCards(msg.card);
        $("#hand").html("");
        addCards();
        $("#hand :input").attr("disabled", false);
      }
      let c = "/cards/" + msg.topCard + ".svg";
      $(".topCard").attr({
        src: c,
        id: msg.topCard,
      });
    } else if (msg.content === "NotPlayed") {
      if (msg.id === id) {
        alert("Invalid Selection");
      }
    } else if (msg.content === "2 Played 1") {
      if (msg.id === id) {
        parseStartCards(msg.card);
        $("#hand").html("");
        addCards();
        $("#hand :input").attr("disabled", true);
        $("#draw").attr("disabled", true);
      }
      if (msg.turn === id) {
        parseStartCards(msg.nextCard);
        $("#hand").html("");
        addCards();
        $("#hand :input").attr("disabled", false);
        $("#draw").attr("disabled", false);
      }
      $("#turnID").html("Turn: " + msg.turn);
      let c = "/cards/" + msg.topCard + ".svg";
      $(".topCard").attr({
        src: c,
        id: msg.topCard,
      });
    } else if (msg.content === "noMoreDraw") {
      if (msg.id === id) {
        let handInputs = document
          .getElementById("hand")
          .getElementsByTagName("input");
        for (let i = 0; i < handInputs.length; i++) {
          handInputs[i].disabled = true;
        }

        $("#draw").attr("disabled", true);

        addCard(msg.card);
      }
    }
  });
}

$(function () {
  $("#usernameBtn").click(function () {
    sendName();
  });
  $("#startBtn").click(function () {
    startGame();
  });
  $("#draw").click(function () {
    drawCard();
  });
  $("#spade").click(function () {
    changeSuit("S");
  });
  $("#heart").click(function () {
    changeSuit("H");
  });
  $("#club").click(function () {
    changeSuit("C");
  });
  $("#diamond").click(function () {
    changeSuit("D");
  });
});
