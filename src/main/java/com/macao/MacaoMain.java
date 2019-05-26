package com.macao;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class MacaoMain extends Application {

    private Deck deck = new Deck();
    private Hand player = new Hand();
    private Hand computer = new Hand();

    private MainCardStore mainCard = new MainCardStore();

    private boolean playerTurn;
    private Card newCard = null;

    private FlowPane playerCards = new FlowPane(Orientation.HORIZONTAL);
    private FlowPane computerCards = new FlowPane(Orientation.HORIZONTAL);
    private FlowPane mainCards = new FlowPane(Orientation.HORIZONTAL);


    private Label computerLabel = new Label("Computer Cards");
    private Label playerLabel = new Label("Your Cards");

    private Label deckSizeLabel = new Label();
    private Label status = new Label();
    private Image imageback = new Image("file:src/main/resources/Background/table.jpg");

    private Button endMoveBtn = new Button();
    private Button takeCardBtn = new Button();
    private Button newDealBtn = new Button();

    public void putPlayerCard(ImageView imgViewClicked, Card card) {
        playerCards.getChildren().remove(imgViewClicked);
        player.removeCard(card);

        mainCards.getChildren().clear();
        mainCards.getChildren().add(imgViewClicked);
        mainCard.addCard(card);
        endMoveBtn.setDisable(false);
        playerTurn = false;
        takeCardBtn.setDisable(true);
    }

    public void chooseHandCard(Hand hand, FlowPane pane, FlowPane mainCardPane) {

        Card card = hand.getCardWithSuitOrType(mainCard.getSuite(), mainCard.getType());

        if (card == null) {
            Card takeCard = drawCard(hand, pane, true);
            if (takeCard.getType().equals(mainCard.getType()) || takeCard.getSuit().equals(mainCard.getSuite())) {
                pane.getChildren().remove(pane.getChildren().size() - 1);
                hand.removeCard(takeCard);

                ImageView cardImg1 = takeCard.getImage();
                mainCardPane.getChildren().clear();
                mainCardPane.getChildren().add(cardImg1);
                mainCard.addCard(takeCard);
            }
            return;
        }

        pane.getChildren().remove(pane.getChildren().size() - 1);
        hand.removeCard(card);

        ImageView cardImg1 = card.getImage();
        mainCardPane.getChildren().clear();
        mainCardPane.getChildren().add(cardImg1);
        mainCard.addCard(card);
    }

    public Card drawCard(Hand hand, FlowPane pane, boolean isComputer) {

        Card card = deck.dealCard();
        ImageView img = card.getImage();

        if (isComputer) {
            img = new ImageView("file:src/main/resources/CardBack/cardBack_red4.png");
            img.setFitHeight(120);
            img.setFitWidth(115);
        }
        pane.getChildren().add(img);
        hand.addCard(card);

        return card;
    }

    public Card drawCard(Hand hand, FlowPane pane) {
        return drawCard(hand, pane, false);
    }

    public void drawMainCard(MainCardStore mainCardStore, FlowPane pane) {
        Card mainCards = deck.dealMainCard();
        ImageView img = mainCards.getImage();
        pane.getChildren().add(img);
        mainCardStore.addCard(mainCards);
        deck.removeMainCardFromDeck(mainCards);
    }

    public void newDeck() {
        deck = new Deck();
        deck.shuffle();
        System.out.println("Talia przetasowana");
    }


    public void cardInDeckSize() {
        int deckSize = deck.getDeckSize();
        StringBuilder total = new StringBuilder();
        total.append(deckSize);
        deckSizeLabel.setText("Karty w talii: " + total.toString());
    }

    public void newHand() {
        newDeck();

        playerCards.getChildren().clear();
        computerCards.getChildren().clear();
        player.removeAllCards();
        computer.removeAllCards();

        mainCards.getChildren().clear();

        playerTurn = true;

        drawCard(player, playerCards);
        drawCard(player, playerCards);
        drawCard(player, playerCards);
        drawCard(player, playerCards);
        drawCard(player, playerCards);
        drawCard(computer, computerCards, true);
        drawCard(computer, computerCards, true);
        drawCard(computer, computerCards, true);
        drawCard(computer, computerCards, true);
        drawCard(computer, computerCards, true);


        drawMainCard(mainCard, mainCards);

        cardInDeckSize();

        endMoveBtn.setDisable(true);
        newDealBtn.setDisable(true);

        status.setText("Twój ruch");
    }

    public static void main(String args[]) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        computerLabel.setFont(new Font("Arial", 24));
        computerLabel.setTextFill(Color.web("#FFF"));
        // playerCards.setStyle()
        //         opacity

        playerLabel.setFont(new Font("Arial", 24));
        playerLabel.setTextFill(Color.web("#FFF"));

        deckSizeLabel.setFont(new Font("Arial", 24));
        deckSizeLabel.setTextFill(Color.web("#FFF"));

        status.setTextFill(Color.web("#FFF"));
        status.setFont(new Font("Arial", 24));


        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        playerCards.setOnMouseClicked(event -> {
            if (!(event.getTarget() instanceof ImageView)) {
                return;
            }
            ImageView clicked = (ImageView) event.getTarget();
            String image = clicked.getImage().impl_getUrl();
            String result = image.substring(image.lastIndexOf("/") + 1, image.lastIndexOf("."));

            String[] splitCards = result.split("\\.");
            String type = splitCards[0];
            String suit = splitCards[1];
            System.out.println(type);
            System.out.println(suit);
            Card clickedCard = new Card(type, suit);

            if (newCard != null && (mainCard.getType().equals(newCard.getType()) || mainCard.getSuite().equals(newCard.getSuit()))
                    && clickedCard.equals(newCard)) {

                putPlayerCard(clicked, newCard);

            } else if (newCard == null) {

                if (mainCard.getType().equals(type) || mainCard.getSuite().equals(suit) && playerTurn) {

                    putPlayerCard(clicked, clickedCard);

                } else {
                    status.setText("Połóź odpowiednią kartę" + "\n" + "lub zakończ ruch!");
                }
            }
        });


        takeCardBtn.setText("Take Card");
        takeCardBtn.setOnAction((e) -> {
            newCard = drawCard(player, playerCards);

            cardInDeckSize();

            takeCardBtn.setDisable(true);
            endMoveBtn.setDisable(false);
        });

        endMoveBtn.setText("End Movement");
        endMoveBtn.setOnAction((e) -> {
            newCard = null;
            playerTurn = false;

            //OKREŚLANIE ZWYCIĘZCY

            int playerHandSize = player.getSizeOfHandCards();
            int computerHandSize = computer.getSizeOfHandCards();
            int deckSize = deck.getDeckSize();

            if (playerHandSize == 0 || deckSize == 0 && playerHandSize < computerHandSize) {
                System.out.println("Wygrałeś");
                status.setText("Wygrałeś");
                takeCardBtn.setDisable(true);
                endMoveBtn.setDisable(true);
                newDealBtn.setDisable(false);
                return;

            } else if (computerHandSize == 0 || deckSize == 0 && computerHandSize < playerHandSize) {
                System.out.println("Przegrałeś");
                status.setText("Przegrałeś");
                takeCardBtn.setDisable(true);
                endMoveBtn.setDisable(true);
                newDealBtn.setDisable(false);
                return;
            }


            chooseHandCard(computer, computerCards, mainCards);

            cardInDeckSize();

            playerTurn = true;
            takeCardBtn.setDisable(false);
            endMoveBtn.setDisable(true);

            status.setText("Twój ruch");
        });


        newDealBtn.setText("New Deal");
        newDealBtn.setOnAction((e) -> {
            newHand();

            takeCardBtn.setDisable(false);
            endMoveBtn.setDisable(true);
        });


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        grid.setHgap(5.5);
        grid.setVgap(5.5);
        grid.setGridLinesVisible(false);

        int numberOfColumns = 5;
        for (int i = 0; i < numberOfColumns; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0);
            grid.getColumnConstraints().add(column);
        }

        int numberOfColumns1 = 10;
        for (int i = 0; i < numberOfColumns1; i++) {
            RowConstraints column = new RowConstraints();
            column.setPercentHeight(100.0);
            grid.getRowConstraints().add(column);
        }

        grid.add(computerCards, 0, 1, 5, 5);
        grid.add(computerLabel, 0, 0);

        grid.add(mainCards, 2, 4, 1, 5);

        grid.add(playerCards, 0, 6, 5, 5);
        grid.add(playerLabel, 0, 5);
        grid.add(status, 3, 4);
        grid.add(deckSizeLabel, 3, 5);
        grid.add(takeCardBtn, 0, 9);
        grid.add(endMoveBtn, 1, 9);
        grid.add(newDealBtn, 4, 9);
        grid.setBackground(background);


        Scene scene = new Scene(grid, 1350, 900);

        primaryStage.setTitle("Macao");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        newHand();
    }
}
