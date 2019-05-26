package com.macao;

import javafx.scene.image.ImageView;


import java.util.*;

public class Deck {

    private List<Card> deck;
    String[] cardType = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    String[] suit = {"Clubs", "Diamonds", "Hearts", "Spades"};

    public Deck() {
        this.deck = new ArrayList<>();

        for (String suits : suit) {
            for (String cardTypes : cardType) {

                deck.add(new Card(cardTypes, suits, new ImageView("file:src/main/resources/DeckCards/" + cardTypes + "." + suits + ".png")));
            }

        }

    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card takeCard() {
        return deck.get(0);
    }

    public List<Card> getDeck() {
        return deck;
    }

    public int getDeckSize() {
        return deck.size();
    }

    public Card dealCard() {
        Random theGenerator = new Random();
        int i = theGenerator.nextInt(getDeckSize());
        Card card = deck.get(i);
        deck.remove(i);
        return card;
    }

    public Card dealMainCard() {
        Optional<Card> any = deck.stream()
                .filter(card -> card.getType().equals("5") || card.getType().equals("6") || card.getType().equals("7") || card.getType().equals("8") || card.getType().equals("9") || card.getType().equals("10") || card.getType().equals("Jack") || card.getType().equals("Queen"))
                .findAny();
        return any.get();

    }

    public void removeMainCardFromDeck(Card mainCard) {
        deck.remove(mainCard);
    }
}
