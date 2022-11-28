package com.example.comp4004f22a3101077008;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;

@Controller
public class GameController {
    Game game = new NormalGame();
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
            game.addPlayer(p);
            System.out.println(game.getPlayers().size());
            return new Message("id " + p.getID());
        }
    }

    @MessageMapping("/start")
    @SendTo("/topic/message")
    public Message start(ConnectionMessage message) throws Exception{
        game.startGame();
        StringBuilder msg = new StringBuilder();
        msg.append("Start ");
        msg.append("TopCard ");
        msg.append(game.getTopCard().getRank()).append(game.getTopCard().getSuit());
        for(int i=0;i<numPlayers;i++){
            msg.append(" ").append(game.getPlayers().get(i).getID());
            msg.append(" Cards:");
            for(Card c : game.getPlayers().get(i).cards){
                msg.append(" ").append(c.getRank()).append(c.getSuit());
            }
        }
        return new Message(msg.toString());

    }
}
