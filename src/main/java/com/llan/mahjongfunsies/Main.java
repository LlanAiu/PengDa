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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Funsies!");
        stage.setScene(scene);
        stage.show();

        Hand test = new Hand();
        test.addCard(Card.of(Card.Suit.TIAO, 5));
        test.addCard(Card.of(Card.Suit.TIAO, 6));
        test.addCard(Card.of(Card.Suit.TIAO, 7));
        test.addCard(Card.of(Card.Suit.TIAO, 7));
        test.addCard(Card.of(Card.Suit.TIAO, 8));
        test.addCard(Card.of(Card.Suit.TIAO, 9));
        test.addCard(Card.of(Card.Suit.TONG, 2));
        test.addCard(Card.of(Card.Suit.TONG, 2));
        test.addCard(Card.of(Card.Suit.TONG, 8));
        test.addCard(Card.of(Card.Suit.TONG, 8));
        test.addCard(Card.of(Card.Suit.TONG, 8));
//        test.addCard(Card.of(Card.Suit.WAN, 3));
        test.addCard(Card.of(Card.Suit.WAN, 3));
        test.addCard(Card.of(Card.Suit.WAN, 3));

        System.out.println(test.isOneAway().get());
        System.out.println(test.isWinning());
    }

    public static void main(String[] args) {
        launch();
    }
}