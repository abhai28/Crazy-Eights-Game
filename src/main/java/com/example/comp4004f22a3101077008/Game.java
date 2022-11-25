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
        String [] m = c.split("");
        String suit = m[1];
        String rank = m[0];
        String res = "";
        Player p = players.get(id-1);
        if(check8(rank)){
            if(topCard.getRank().equals(rank)||topCard.getSuit().equals(suit)){
                for(int i=0;i<p.handSize();i++){
                    if(p.getCard(i).getSuit().equals(suit)&&p.getCard(i).getRank().equals(rank)){
                        topCard = players.get(id-1).cards.remove(i);
                    }
                }
                res = "8 Played";
            }
            else{
                res = "not played";
            }
        }
        else if(checkAces(rank)){
            if(topCard.getRank().equals(rank)||topCard.getSuit().equals(suit)){
                for(int i=0;i<p.handSize();i++){
                    if(p.getCard(i).getSuit().equals(suit)&&p.getCard(i).getRank().equals(rank)){
                        topCard = players.get(id-1).cards.remove(i);
                    }
                }
                res = "A Played";
            }
            else{
                res = "not played";
            }
        }
        else if(checkQueen(rank)){
            if(topCard.getRank().equals(rank)||topCard.getSuit().equals(suit)){
                for(int i=0;i<p.handSize();i++){
                    if(p.getCard(i).getSuit().equals(suit)&&p.getCard(i).getRank().equals(rank)){
                        topCard = players.get(id-1).cards.remove(i);
                    }
                }
                res = "Q Played";
            }
            else{
                res = "not played";
            }
        }
        else if(check2(rank)){
            if(topCard.getRank().equals(rank)||topCard.getSuit().equals(suit)){
                for(int i=0;i<p.handSize();i++){
                    if(p.getCard(i).getSuit().equals(suit)&&p.getCard(i).getRank().equals(rank)){
                        topCard = players.get(id-1).cards.remove(i);
                    }
                }
                res = "2 Played";
            }
            else{
                res = "not played";
            }
        }
        else{
            if(topCard.getRank().equals(rank)||topCard.getSuit().equals(suit)){
                for(int i=0;i<p.handSize();i++){
                    if(p.getCard(i).getSuit().equals(suit)&&p.getCard(i).getRank().equals(rank)){
                        topCard = players.get(id-1).cards.remove(i);
                    }
                }
                res = "Played";
            }
            else{
                res = "not played";
            }
        }
        return res;
    }

    public void calculateScore(){
        for(Player p : players){
            if(p.handSize()==0){
                p.setScore(0);
            }
            else{
                int score = 0;
                for(Card c : p.cards){
                    if(c.getRank().equals("Q")||c.getRank().equals("K")||c.getRank().equals("J")||c.getRank().equals("10")){
                        score += 10;
                    }
                    else if(c.getRank().equals("8")){
                        score += 50;
                    }
                    else{
                        switch (c.getRank()) {
                            case "A" -> score += 1;
                            case "2" -> score += 2;
                            case "3" -> score += 3;
                            case "4" -> score += 4;
                            case "5" -> score += 5;
                            case "6" -> score += 6;
                            case "7" -> score += 7;
                            case "9" -> score += 9;
                        }
                    }
                }
                p.setScore(score);
            }
        }
    }
}
