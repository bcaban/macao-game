package com.macao;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Card extends Node {
    private String type;
    private String suit;
    private ImageView image;

    public Card(String type, String suit, ImageView image) {
        this.type = type;
        this.suit = suit;
        this.image = image;
        this.image.setFitHeight(120);
        this.image.setFitWidth(115);
        setOnMouseClicked(event -> System.out.println("Clicked card"));
    }

    public Card(String type, String suit) {
        this.type = type;
        this.suit = suit;
    }

    @Override
    protected NGNode impl_createPeer() {
        return null;
    }

    @Override
    public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
        return null;
    }

    @Override
    protected boolean impl_computeContains(double localX, double localY) {
        return false;
    }

    @Override
    public String toString() {
        return this.type + " " + this.suit;
    }

    @Override
    public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
        return null;
    }

    public String getType() {
        return type;
    }

    public String getSuit() {
        return suit;
    }


    public ImageView getImage() {
        return image;
    }

    public Card getCard() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(type, card.type) &&
                Objects.equals(suit, card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, suit);
    }
}
