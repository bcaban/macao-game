package com.macao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Hand {

    private List<Card> handCards;

    public Hand() {
        this.handCards = new ArrayList<>();
    }

    public void addCard(Card card) {
        handCards.add(card);
    }

    public void removeCard(Card card) {
        handCards.remove(card);
    }

    public Card getCardWithSuitOrType(String suit, String type) {
        Optional<Card> first = handCards.stream()
                .filter(card -> card.getSuit().equals(suit))
                .findFirst();
        if (first.isPresent()) {
            return first.get();
        }

        first = handCards.stream()
                .filter(card -> card.getType().equals(type))
                .findFirst();

        return first.orElse(null);
    }

    public int getSizeOfHandCards() {
        return handCards.size();
    }

    public void removeAllCards() {
        handCards.clear();
    }


}
