package com.macao;


public class MainCardStore {

    private Card mainCardStore;


    public void addCard(Card card) {

        mainCardStore = card;
    }

    public String getSuite() {
        return mainCardStore.getSuit();
    }

    public String getType() {
        return mainCardStore.getType();
    }
}


