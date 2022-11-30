package com.example.comp4004f22a3101077008;

public class PlayMessage {
    private String content;
    private String id;
    private String card;
    private String turn;
    private String direction;
    public PlayMessage() {
    }
    public PlayMessage(String content, String id, String card){
        this.content = content;
        this.id = id;
        this.card = card;
    }
    public PlayMessage(String content, String id, String card, String turn,String direction) {
        this.content = content;
        this.id = id;
        this.card = card;
        this.turn = turn;
        this.direction = direction;
    }
    public PlayMessage(String content, String id, String card, String turn){
        this.content = content;
        this.id = id;
        this.card = card;
        this.turn = turn;
    }

    public String getContent() {
        return content;
    }
    public String getId (){return id;}
    public String getCard(){return card;}
    public String getTurn(){return turn;}
    public String getDirection(){return direction;}
}
