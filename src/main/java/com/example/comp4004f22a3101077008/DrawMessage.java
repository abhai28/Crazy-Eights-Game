package com.example.comp4004f22a3101077008;

public class DrawMessage {
    private String content;
    private String id;
    private String card;

    String id1;
    String score1;
    String id2;
    String score2;
    String id3;
    String score3;
    String id4;
    String score4;

    public DrawMessage() {
    }

    public DrawMessage(String content,String id,String card) {
        this.content = content;
        this.id = id;
        this.card = card;
    }

    public DrawMessage(String content,String id1,String score1,String id2,String score2,String id3, String score3,String id4,String score4) {
        this.content = content;
        this.id1 = id1;
        this.score1 = score1;
        this.id2 = id2;
        this.score2 = score2;
        this.id3 = id3;
        this.score3 = score3;
        this.id4 = id4;
        this.score4 = score4;
    }

    public String getContent() {
        return content;
    }
    public String getId(){return id;}
    public String getCard(){return card;}
}
