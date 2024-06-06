package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.controllers.GameController;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.util.DisplayUtil;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.*;

import java.util.Arrays;
import java.util.List;

public class Board {

    private BorderPane pane;

    private HandUI[] hands = new HandUI[Constants.NUM_PLAYERS];
    private PostMoveUI humanPostMove = new PostMoveUI();
    private DiscardUI discard;

    private DisplayUtil.Orientation selectedOrientation;
    private int selectedIndex = -1;

    private static Board instance;

    public static Board getInstance(){
        if(instance == null){
            instance = new Board();
        }
        return instance;
    }

    private Board(){
        pane = new BorderPane();
        discard = DiscardUI.getInstance();
        for(int i = 0; i < Constants.NUM_PLAYERS; i++){
            DisplayUtil.Orientation orientation;
            switch (i){
                case 0 -> orientation = DisplayUtil.Orientation.DOWN;
                case 1 -> orientation = DisplayUtil.Orientation.LEFT;
                case 2 -> orientation = DisplayUtil.Orientation.UP;
                default -> orientation = DisplayUtil.Orientation.RIGHT;
            }
            hands[i] = new HandUI(orientation);
            setInPane(hands[i]);
        }
        pane.setCenter(discard.getNode());
        BorderPane.setAlignment(discard.getNode(), Pos.CENTER);
    }

    public Parent getRoot(){
        return pane;
    }

    public void clearBoard(){
        for(HandUI hand : hands){
            hand.clearGrid();
        }
        discard.clear();
    }

    //dummy method for now just to test logic
    public void updatePostMoves(List<Command> moves){
        humanPostMove.updateVisibilities(moves);
    }

    public void resetPostMoves(){
        humanPostMove.hideAll();
    }

    public void displayState(){
        this.clearBoard();
        Arrays.stream(hands).forEach(handUI -> handUI.displayHand());
        discard.displayDiscard();
    }

    public void setSelected(DisplayUtil.Orientation orient, int cardIndex){
        if(orient != selectedOrientation || cardIndex != selectedIndex){
            selectedOrientation = orient;
            selectedIndex = cardIndex;
            hands[GameController.getInstance().getCurrentTurnIndex()].setSelectedIndex(selectedIndex);
        }
    }

    public void resetSelected(){
        for(HandUI hand : hands){
            hand.resetSelected();
        }
        selectedIndex = -1;
    }

    public void periodic(){

    }

    private void setInPane(HandUI hand){
        switch (hand.getOrientation()){
            case DOWN:
                HBox box = new HBox();
                box.getChildren().add(hand.getGrid());
                box.getChildren().add(humanPostMove.getNode());
                pane.setBottom(box);
                BorderPane.setMargin(box, DisplayConstants.bottomInsets);
                BorderPane.setAlignment(box, Pos.BOTTOM_CENTER);
                break;
            case LEFT:
                pane.setLeft(hand.getGrid());
                BorderPane.setMargin(hand.getGrid(), DisplayConstants.leftInsets);
                BorderPane.setAlignment(hand.getGrid(), Pos.CENTER_LEFT);
                break;
            case UP:
                pane.setTop(hand.getGrid());
                BorderPane.setMargin(hand.getGrid(), DisplayConstants.topInsets);
                BorderPane.setAlignment(hand.getGrid(), Pos.TOP_CENTER);
                break;
            default:
                pane.setRight(hand.getGrid());
                BorderPane.setMargin(hand.getGrid(), DisplayConstants.rightInsets);
                BorderPane.setAlignment(hand.getGrid(), Pos.CENTER_RIGHT);
                break;
        }
    }
}
