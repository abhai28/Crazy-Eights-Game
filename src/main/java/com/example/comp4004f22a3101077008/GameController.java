package com.example.comp4004f22a3101077008;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GameController {
    Game game = new NormalGame();
    int numPlayers = 0;
    @MessageMapping("/connect")
    @SendTo("/topic/message")
    public Message greeting(ConnectionMessage message) throws Exception {
        numPlayers++;
        Player p = new Player(numPlayers);
        game.addPlayer(p);
        System.out.println(game.getPlayers().size());
        return new Message(HtmlUtils.htmlEscape(message.getName()+p.getID()));
    }
}
