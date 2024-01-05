package com.example.comp4004f22a3101077008;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
/*
 * GameLogic class
 * This class is used to handle the game logic
 * It is used to populate the deck, shuffle the deck, deal cards to players, draw cards, play cards, calculate scores
 * and increment the number of draws
 * It is set as a component so that it can be autowired in other classes. This is done to avoid creating multiple instances of the class.
 * The @Component annotation is used to indicate that the class is a component. It is used to auto-detect and auto-configure beans using classpath scanning.
 */
@Component
public class GameLogic{
    
    public Card startGame(ArrayList<Card> cards,ArrayList<Player> players){
        for(Player p: players){
            p.cards.clear();
        }
        Card topCard = cards.remove(0);
        while(topCard.getRank().equals("8")){
            Card tmp = topCard;
            cards.add(tmp);
            topCard = cards.remove(0);
        }
        dealCards(cards,players);
        return topCard;
    }

    // startDealCards is used to deal cards to players
    public void startDealCards(ArrayList<Card> cards, ArrayList<Player> players, int id){
        players.get(id).cards.clear();
        for(int i=0;i<5;i++){
            players.get(id).addCard(cards.remove(0));
        }
    }
    // startSetTopCard is used to set the top card
    public Card startSetTopCard(ArrayList<Card> cards){
        Card topCard = cards.remove(0);
        // if the top card is an 8, it is added back to the deck and a new card is drawn
        while(topCard.getRank().equals("8")){
            Card tmp = topCard;
            cards.add(tmp);
            topCard = cards.remove(0);
        }
        return topCard;
    }
    // populateDeck is used to populate the deck
    public void populateDeck(ArrayList<Card> cards) {
        String [] suit = {"S","C","D","H"};
        String [] rank = {"A","2","3","4","5","6","7","8","9","T","J","Q","K"};
        for (String s : suit) {
            for (String value : rank) {
                Card c = new Card(s, value);
                cards.add(c);
            }
        }
    }

    // shuffleDeck is used to shuffle the deck
    public void shuffleDeck(ArrayList<Card> cards) {
        Collections.shuffle(cards);
    }

    // dealCards is used to deal cards to players
    public boolean dealCards(ArrayList<Card> cards, ArrayList<Player> players) {
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

    // drawCard is used to draw a card
    public boolean drawCard(ArrayList<Card> cards, Player p){
        if(cards.size()==0){
            return false;
        }
        else{
            p.addCard(cards.remove(0));
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

    // playCard is used to play a card, it checks if the card is valid and returns a string based on the result.
    public String playCard(Player p, String c, Card topCard) {
        String [] m = c.split("");
        String suit = m[1];
        String rank = m[0];
        String res = "";
        if(check8(rank)){
            if(topCard.getRank().equals(rank)||topCard.getSuit().equals(suit)){
                for(int i=0;i<p.handSize();i++){
                    if(p.getCard(i).getSuit().equals(suit)&&p.getCard(i).getRank().equals(rank)){
                        res = "8Played";
                    }
                }
            }
            else{
                res = "notplayed";
            }
        }
        else if(checkAces(rank)){
            if(topCard.getRank().equals(rank)||topCard.getSuit().equals(suit)){
                for(int i=0;i<p.handSize();i++){
                    if(p.getCard(i).getSuit().equals(suit)&&p.getCard(i).getRank().equals(rank)){
                        res = "APlayed";
                    }
                }
            }
            else{
                res = "notplayed";
            }
        }
        else if(checkQueen(rank)){
            if(topCard.getRank().equals(rank)||topCard.getSuit().equals(suit)){
                for(int i=0;i<p.handSize();i++){
                    if(p.getCard(i).getSuit().equals(suit)&&p.getCard(i).getRank().equals(rank)){
                        res = "QPlayed";
                    }
                }
            }
            else{
                res = "notplayed";
            }
        }
        else if(check2(rank)){
            if(topCard.getRank().equals(rank)||topCard.getSuit().equals(suit)){
                for(int i=0;i<p.handSize();i++){
                    if(p.getCard(i).getSuit().equals(suit)&&p.getCard(i).getRank().equals(rank)){
                        res = "2Played";
                    }
                }
            }
            else{
                res = "notplayed";
            }
        }
        else{
            if(topCard.getRank().equals(rank)||topCard.getSuit().equals(suit)){
                for(int i=0;i<p.handSize();i++){
                    if(p.getCard(i).getSuit().equals(suit)&&p.getCard(i).getRank().equals(rank)){
                        res = "Played";
                    }
                }
            }
            else{
                res = "notplayed";
            }
        }
        return res;
    }

    // calculateScore is used to calculate the score of each player
    public void calculateScore(ArrayList<Player> players){
        for(Player p : players){
            if(p.handSize()==0){
                p.setScore(p.getScore());
            }
            else{
                int score = 0;
                for(Card c : p.cards){
                    if(c.getRank().equals("Q")||c.getRank().equals("K")||c.getRank().equals("J")||c.getRank().equals("T")){
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
                p.setScore(p.getScore()+score);
            }
        }
    }

    public void incrementDraw(ArrayList<Player> players, int id){
        players.get(id-1).setNumDraws(players.get(id-1).getNumDraws()+1);
    }
    public void resetDraw(ArrayList<Player> players, int id){
        players.get(id-1).setNumDraws(0);
    }
}
