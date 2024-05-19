package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.environment.GameState;
import com.llan.mahjongfunsies.util.DisplayUtil;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Board {

    private BorderPane pane;

    private static Board instance;

    public static Board getInstance(){
        if(instance == null){
            instance = new Board();
        }
        return instance;
    }

    private Board(){
        pane = new BorderPane();
        for(DisplayUtil.Orientation orientation : DisplayUtil.Orientation.values()){
            GridPane hand = new GridPane();
            if(orientation == DisplayUtil.Orientation.UP || orientation == DisplayUtil.Orientation.DOWN){
                hand.getRowConstraints().add(new RowConstraints(DisplayConstants.gridLength));
                for(int i = 0; i < Constants.STARTING_CARDS; i++){
                    hand.getColumnConstraints().add(new ColumnConstraints(DisplayConstants.gridWidth));
                }
            } else {
                hand.getColumnConstraints().add(new ColumnConstraints(DisplayConstants.gridLength));
                for(int i = 0; i < Constants.STARTING_CARDS; i++){
                    hand.getRowConstraints().add(new RowConstraints(DisplayConstants.gridWidth));
                }
            }
            hand.setGridLinesVisible(true);
            switch(orientation) {
                case UP:
                    BorderPane.setAlignment(hand, Pos.TOP_CENTER);
                    BorderPane.setMargin(hand, DisplayConstants.topInsets);
                    pane.setTop(hand);
                    break;
                case DOWN:
                    BorderPane.setAlignment(hand, Pos.BOTTOM_CENTER);
                    BorderPane.setMargin(hand, DisplayConstants.bottomInsets);
                    pane.setBottom(hand);
                    break;
                case LEFT:
                    BorderPane.setAlignment(hand, Pos.CENTER_LEFT);
                    BorderPane.setMargin(hand, DisplayConstants.leftInsets);
                    pane.setLeft(hand);
                    break;
                case RIGHT:
                    BorderPane.setAlignment(hand, Pos.CENTER_RIGHT);
                    BorderPane.setMargin(hand, DisplayConstants.rightInsets);
                    pane.setRight(hand);
                    break;
            }
        }
    }

    public Parent getRoot(){
        return pane;
    }

    public void displayState(GameState state){
        for(int i = 0; i < state.hands().length; i++){
            GridPane hand;
            Card[] cards = state.hands()[i].hand();
            DisplayUtil.Orientation orient = state.hands()[i].orientation();
            switch (state.hands()[i].orientation()){
                case RIGHT -> hand = (GridPane) pane.getRight();
                case DOWN -> hand = (GridPane) pane.getBottom();
                case LEFT -> hand = (GridPane) pane.getLeft();
                default -> hand = (GridPane) pane.getTop();
            }
            for(int j = 0; j < cards.length; j++){
                ImageView background = DisplayUtil.getCardBackground(false, orient);
                ImageView cardImage = DisplayUtil.getCardImage(cards[j], orient);
                if(orient == DisplayUtil.Orientation.UP || orient == DisplayUtil.Orientation.DOWN){
                    hand.add(background, j, 0);
                    hand.add(cardImage, j, 0);
                } else {
                    hand.add(background, 0, j);
                    hand.add(cardImage, 0, j);
                }
                GridPane.setHalignment(cardImage, HPos.CENTER);
                GridPane.setHalignment(background, HPos.CENTER);
                GridPane.setValignment(cardImage, VPos.CENTER);
                GridPane.setValignment(background, VPos.CENTER);
            }
        }
    }

}
