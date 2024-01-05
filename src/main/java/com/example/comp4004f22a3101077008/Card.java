package com.example.comp4004f22a3101077008;

/*
 * This is the Card class. It is responsible for creating a card object.
 * It has two attributes: suit and rank.
 * It has two constructors: one with no parameters and one with two parameters.
 * It has two methods: getSuit() and getRank().
 */
public class Card {
    private String suit;
    private String rank;
    public Card(){
        this.suit = "";
        this.rank = "";
    }
    public Card (String s, String r){
        this.suit = s;
        this.rank = r;
    }
    public String getSuit(){
        return this.suit;
    }
    public String getRank(){
        return this.rank;
    }
    
}
