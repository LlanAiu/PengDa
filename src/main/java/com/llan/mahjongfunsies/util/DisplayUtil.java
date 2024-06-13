package com.llan.mahjongfunsies.util;

import com.llan.mahjongfunsies.controllers.GameController;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.ui.Board;
import com.llan.mahjongfunsies.ui.DisplayConstants;
import com.llan.mahjongfunsies.ui.IndexedPane;
import com.llan.mahjongfunsies.ui.InputHandler;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DisplayUtil {
    public enum Orientation {
        UP(180),
        DOWN(0),
        LEFT(90),
        RIGHT(-90);

        final int rotationDegrees;

        Orientation(int rotationDegrees){
            this.rotationDegrees = rotationDegrees;
        }

        public int getRotation(){
            return rotationDegrees;
        }
    }
    private static final String location = System.getProperty("user.dir") + "\\src\\main\\resources\\com\\llan\\mahjongfunsies\\assets\\";

    public static ImageView getCardBackground(boolean hidden, Orientation orientation){
        ImageView viewer = new ImageView();
        Image image;
        String file = (hidden) ? "Back.png" : "Front.png";
        try {
            InputStream stream = new FileInputStream(location + file);
            image = new Image(stream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        viewer.setImage(image);
        viewer.setFitWidth(DisplayConstants.CARD_WIDTH);
        viewer.setPreserveRatio(true);
        viewer.setRotate(orientation.getRotation());
        return viewer;
    }

    public static ImageView getCardImage(Card card, Orientation orientation){
        ImageView viewer = new ImageView();
        Image image;
        try {
            InputStream stream = new FileInputStream(location + card.suit().toString() + "_" + card.value() + ".png");
            image = new Image(stream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        viewer.setImage(image);
        viewer.setFitWidth(DisplayConstants.CARD_WIDTH);
        viewer.setPreserveRatio(true);
        viewer.setRotate(orientation.getRotation());
        return viewer;
    }

    public static Node displayCard(Card card, Orientation orientation, boolean displayOverride, int cardIndex, boolean selected){
        boolean shouldShow = !card.isHidden() || displayOverride;
        ImageView background = getCardBackground(!shouldShow, orientation);
        ImageView cardView = getCardImage(card, orientation);
        Pane tile = new IndexedPane(cardIndex);
        double offset = 0;
        tile.getChildren().add(background);
        if(shouldShow){
            tile.getChildren().add(cardView);
        }
        if(card.isPartOfSet() || !card.isHidden()){
            offset = DisplayConstants.SET_OFFSET;
        } else if(selected){
            offset = DisplayConstants.SELECTED_OFFSET;
        }
        if(orientation == Orientation.LEFT || orientation == Orientation.RIGHT){
            tile.setPrefSize(DisplayConstants.CARD_LENGTH, DisplayConstants.CARD_WIDTH);
            double delta = (DisplayConstants.CARD_WIDTH - DisplayConstants.CARD_LENGTH) / 2.0;
            double shift = (orientation == Orientation.RIGHT) ? offset : -offset;
            tile.getChildren().stream().forEach(node -> {
                node.relocate(-delta, delta);
                node.relocate(shift, 0);
            });
        } else {
            tile.setPrefSize(DisplayConstants.CARD_WIDTH, DisplayConstants.CARD_LENGTH);
            if(offset != 0){
               double shift = (orientation == Orientation.DOWN) ? offset : -offset;
               tile.getChildren().forEach(node -> node.relocate(0, shift));
            }
        }
        tile.setOnMouseClicked(mouseEvent -> {
            if (shouldShow) {
                System.out.println(card + "; Player Index: " + GameController.getInstance().getPlayerByOrientation(orientation).getIndex());
                InputHandler.getInstance().onCardPressed(((IndexedPane) tile).getIndex(), card, GameController.getInstance().getPlayerByOrientation(orientation).getIndex());
                Board.getInstance().setSelected(orientation, cardIndex);
            }
        });
        return tile;
    }

}
