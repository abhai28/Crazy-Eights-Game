package com.example.comp4004f22a3101077008;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    GameLogic game = new GameLogic();
    GameData gd = new NonRiggedData();
    int numPlayers = 0;
    @MessageMapping("/connect")
    @SendTo("/topic/message")
    public Message connect(ConnectionMessage message) throws Exception {
        numPlayers++;
        if(numPlayers>4){
            return new Message("game started");
        }
        else{
            Thread.sleep(500);
            Player p = new Player(numPlayers);
            System.out.println(message.getMessage());
            gd.addPlayer(p);
            System.out.println(gd.getPlayers().size());
            return new Message("id " + p.getID());
        }
    }

    @MessageMapping("/start")
    @SendTo("/topic/message")
    public Message start(ConnectionMessage message) throws Exception{
        game.startGame(gd.getTopCard(),gd.getCards(),gd.getPlayers());
        StringBuilder msg = new StringBuilder();
        msg.append("Start ");
        msg.append("TopCard ");
        msg.append(gd.getTopCard().getRank()).append(gd.getTopCard().getSuit());
        for(int i=0;i<numPlayers;i++){
            msg.append(" ").append(gd.getPlayers().get(i).getID());
            msg.append(" Cards:");
            for(Card c : gd.getPlayers().get(i).cards){
                msg.append(" ").append(c.getRank()).append(c.getSuit());
            }
        }
        return new Message(msg.toString());

    }
}
