package com.example.comp4004f22a3101077008;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

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
            return new Message("game started","");
        }
        else{
            Thread.sleep(500);
            Player p = new Player(numPlayers);
            System.out.println(message.getMessage());
            gd.addPlayer(p);
            System.out.println(gd.getPlayers().size());
            return new Message("id",String.valueOf(p.getID()));
        }
    }

    @MessageMapping("/start")
    @SendTo("/topic/message")
    public StartMessage start(ConnectionMessage message) throws Exception{
        gd.setTopCard(game.startGame(gd.getCards(),gd.getPlayers()));
        ArrayList<String> cards = new ArrayList<>();
        for(int i=0;i<numPlayers;i++){
            StringBuilder car = new StringBuilder();
            for(Card c : gd.getPlayers().get(i).cards){
                car.append(" ").append(c.getRank()).append(c.getSuit());
            }
            cards.add(car.toString());
        }
        return new StartMessage("Start",gd.getTopCard().getRank()+gd.getTopCard().getSuit(),"1", cards.get(0), "2",cards.get(1),"3",cards.get(2),"4",cards.get(3),String.valueOf(gd.getPlayers().get(0).getScore()),String.valueOf(gd.getPlayers().get(1).getScore()),String.valueOf(gd.getPlayers().get(2).getScore()),String.valueOf(gd.getPlayers().get(3).getScore()));
    }

    @MessageMapping("/playCard")
    @SendTo("/topic/message")
    public Message playCard (ConnectionMessage message) throws Exception{
        String [] msg = message.getMessage().split(" ");
        int id = Integer.parseInt(msg[0]);
        String [] car = msg[1].split("");
        String res = game.playCard(gd.getPlayers().get(id-1), msg[1],gd.getTopCard());
        int ci = gd.getPlayers().get(id-1).getCardIndex(car[0],car[1]);
        StringBuilder resMsg = new StringBuilder();
        switch(res){
            case"8 Played"->{
                gd.setTopCard(gd.getPlayers().get(id-1).cards.remove(ci));
                resMsg.append("Played ").append(gd.getTopCard().getRank()).append(gd.getTopCard().getSuit()).append(" ").append(id).append(" ").append(res).append(" ").append("Cards");
                for(Card c : gd.getPlayers().get(id-1).cards){
                    resMsg.append(" ").append(c.getRank()).append(c.getSuit());
                }
            }
        }
        return new Message("","");
    }

    @MessageMapping("/drawCard")
    @SendTo("/topic/message")
    public DrawMessage drawCard(ConnectionMessage message) throws Exception{
        int id = Integer.parseInt(message.getMessage());
        boolean gameState = game.drawCard(gd.getCards(),gd.getPlayers().get(id-1));
        if(gameState){
            Player p = gd.getPlayers().get(id-1);
            String card = p.getCard(p.handSize()-1).getRank()+ p.getCard(p.handSize()-1).getSuit();
            return new DrawMessage("draw",message.getMessage(),card);
        }
        else{
            game.calculateScore(gd.getPlayers());
            return new DrawMessage("Game Over","1",String.valueOf(gd.getPlayers().get(0).getScore()),"2",String.valueOf(gd.getPlayers().get(1).getScore()),"3",String.valueOf(gd.getPlayers().get(2).getScore()),"4",String.valueOf(gd.getPlayers().get(3).getScore()));
        }
    }
}
