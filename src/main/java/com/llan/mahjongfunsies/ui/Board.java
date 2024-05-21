package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.environment.GameState;
import com.llan.mahjongfunsies.util.DisplayUtil;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class Board {

    private BorderPane pane;

    private Label debugText;

    private static Board instance;

    public static Board getInstance(){
        if(instance == null){
            instance = new Board();
        }
        return instance;
    }

    private Board(){
        pane = new BorderPane();
        debugText = new Label();
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
        pane.setCenter(debugText);
    }

    public Parent getRoot(){
        return pane;
    }

    public void displayState(GameState state){
        System.out.println(state.turnIndex());
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
                Node card = DisplayUtil.displayCard(cards[j], orient, i == state.turnIndex());
                if(orient == DisplayUtil.Orientation.UP || orient == DisplayUtil.Orientation.DOWN){
                    hand.add(card, j, 0);
                } else {
                    hand.add(card, 0, j);
                }
                GridPane.setHalignment(card, HPos.CENTER);
                GridPane.setValignment(card, VPos.CENTER);
            }
        }
    }

    public void periodic(){

    }
}
