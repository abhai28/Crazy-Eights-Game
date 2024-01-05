package com.example.comp4004f22a3101077008;
/*
 * This is the DrawMessage class. It is responsible for creating a DrawMessage object.
 * It has three attributes: content, id, and card.
 * It has two constructors: one with no parameters and one with three parameters.
 * It has three methods: getContent(), getId(), and getCard().
 * The content attribute is used to store the content of the message.
 * The id attribute is used to store the id of the user who the message corresponds to.
 * The card attribute is used to store the card drawn.
 * The getContent() method is used to return the content of the message.
 * The getId() method is used to return the id of the user who the message corresponds to.
 * The getCard() method is used to return the card drawn.
 * The DrawMessage object is used to send a message to the client which includes the card drawn.
 */
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
    String nextRound;
    String winner;
    String turn;
    public DrawMessage() {
    }

    public DrawMessage(String content,String id,String card) {
        this.content = content;
        this.id = id;
        this.card = card;
    }
    public DrawMessage(String content, String id, String card, String turn){
        this.content = content;
        this.id = id;
        this.card = card;
        this.turn = turn;
    }
    public DrawMessage(String content,String id1,String score1,String id2,String score2,String id3, String score3,String id4,String score4, String nextRound) {
        this.content = content;
        this.id1 = id1;
        this.score1 = score1;
        this.id2 = id2;
        this.score2 = score2;
        this.id3 = id3;
        this.score3 = score3;
        this.id4 = id4;
        this.score4 = score4;
        this.nextRound = nextRound;
    }

    public DrawMessage(String content,String id1,String score1,String id2,String score2,String id3, String score3,String id4,String score4, String nextRound, String winner) {
        this.content = content;
        this.id1 = id1;
        this.score1 = score1;
        this.id2 = id2;
        this.score2 = score2;
        this.id3 = id3;
        this.score3 = score3;
        this.id4 = id4;
        this.score4 = score4;
        this.nextRound = nextRound;
        this.winner = winner;
    }
    public String getContent() {
        return content;
    }
    public String getId(){return id;}
    public String getCard(){return card;}
    public String getScore1(){return this.score1;}
    public String getScore2(){return this.score2;}
    public String getScore3(){return this.score3;}
    public String getScore4(){return this.score4;}
    public String getNextRound(){return this.nextRound;}
    public String getWinner(){return this.winner;}
    public String getTurn(){return this.turn;}
}
