package com.example.comp4004f22a3101077008;
/*
 * This is the ChangeSuitMessage class. It is responsible for creating a ChangeSuitMessage object.
 * It has three attributes: content, suit, and turn.
 * It has two constructors: one with no parameters and one with three parameters.
 * It has three methods: getContent(), getSuit(), and getTurn().
 * The content attribute is used to store the content of the message.
 * The suit attribute is used to store the suit of the message.
 * The turn attribute is used to store the turn of the message.
 * The getContent() method is used to return the content of the message.
 * The getSuit() method is used to return the suit of the message.
 * The getTurn() method is used to return the turn of the message.
 * The ChangeSuitMessage object is used to send a message to the client to change the suit of the game.
 * The content of the message is "changeSuit".
 * The suit of the message is the suit that the player wants to change to.
 * The turn of the message is the turn of the player.
 */
public class ChangeSuitMessage {
    private String content;
    private String suit;
    private String turn;
    public ChangeSuitMessage() {
    }

    public ChangeSuitMessage(String content, String suit, String turn) {
        this.content = content;
        this.suit = suit;
        this.turn = turn;
    }

    public String getContent() {
        return content;
    }
    public String getSuit (){return suit;}
    public String getTurn(){return turn;}
}
