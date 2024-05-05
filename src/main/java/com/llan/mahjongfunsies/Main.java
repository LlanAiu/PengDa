package com.llan.mahjongfunsies;

import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Hand;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.Suit.TIAO, 1));
        hand.addCard(new Card(Card.Suit.TIAO, 1));
        hand.addCard(new Card(Card.Suit.TIAO, 1));
        hand.addCard(new Card(Card.Suit.TIAO, 4));
        hand.addCard(new Card(Card.Suit.TIAO, 5));
        hand.addCard(new Card(Card.Suit.TIAO, 6));
        hand.addCard(new Card(Card.Suit.TIAO, 2));
        hand.addCard(new Card(Card.Suit.TIAO, 2));
        System.out.println(hand.isWinning());

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}