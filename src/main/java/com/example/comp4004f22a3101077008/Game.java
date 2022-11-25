package com.example.comp4004f22a3101077008;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Game {
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Card> cards = new ArrayList<>();
    Card topCard;
    public void startGame(){
        populateDeck();
        shuffleDeck();
        topCard = cards.remove(0);
        while(topCard.getRank().equals("8")){
            Card tmp = topCard;
            cards.add(tmp);
            topCard = cards.remove(0);
        }
        dealCards();
    }
    public void populateDeck() {
        String [] suit = {"S","C","D","H"};
        String [] rank = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        for (String s : suit) {
            for (String value : rank) {
                Card c = new Card(s, value);
                cards.add(c);
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public boolean dealCards() {
        if(cards.size() <(players.size()*5)){
            return false;
        }
        else{
            for(Player p : players){
                for(int i=0;i<5;i++){
                    p.addCard(cards.remove(0));
                }
            }
            return true;
        }
    }
    public boolean drawCard(int id){
        if(cards.size()==0){
            return false;
        }
        else{
            players.get(id-1).addCard(cards.remove(0));
            return true;
        }
    }
    public boolean checkQueen(String r){
        return r.equals("Q");
    }
    public boolean checkAces(String r){
        return r.equals("A");
    }
    public boolean check8(String r){
        return r.equals("8");
    }
    public boolean check2(String r){
        return r.equals("2");
    }

    public String playCard(int id, String c) {
        return "";
    }
}
