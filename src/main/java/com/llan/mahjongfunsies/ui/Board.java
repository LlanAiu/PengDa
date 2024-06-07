package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.controllers.GameController;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.mahjong.players.Human;
import com.llan.mahjongfunsies.util.DisplayUtil;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.*;

import java.util.Arrays;
import java.util.List;

public class Board {

    private BorderPane pane;

    private int humanIndex;
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
            hands[i] = createUI(orientation, i);
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

    public void updatePostMoves(List<Command> moves){
        ((HumanUI) hands[humanIndex]).updatePostMoves(moves);
    }

    public void resetPostMoves(){
        ((HumanUI) hands[humanIndex]).hidePostMoves();
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
            ((HumanUI) hands[humanIndex]).setSelectedIndex(cardIndex);
        }
    }

    public void resetSelected(){
        ((HumanUI) hands[humanIndex]).resetSelected();
        selectedIndex = -1;
    }

    public void periodic(){

    }

    private void setInPane(HandUI hand){
        switch (hand.getOrientation()){
            case DOWN:
                HBox box = new HBox();
                box.getChildren().add(hand.getNode());
                box.getChildren().add(humanPostMove.getNode());
                pane.setBottom(box);
                BorderPane.setMargin(box, DisplayConstants.bottomInsets);
                BorderPane.setAlignment(box, Pos.BOTTOM_CENTER);
                break;
            case LEFT:
                pane.setLeft(hand.getNode());
                BorderPane.setMargin(hand.getNode(), DisplayConstants.leftInsets);
                BorderPane.setAlignment(hand.getNode(), Pos.CENTER_LEFT);
                break;
            case UP:
                pane.setTop(hand.getNode());
                BorderPane.setMargin(hand.getNode(), DisplayConstants.topInsets);
                BorderPane.setAlignment(hand.getNode(), Pos.TOP_CENTER);
                break;
            default:
                pane.setRight(hand.getNode());
                BorderPane.setMargin(hand.getNode(), DisplayConstants.rightInsets);
                BorderPane.setAlignment(hand.getNode(), Pos.CENTER_RIGHT);
                break;
        }
    }

    private HandUI createUI(DisplayUtil.Orientation orientation, int index){
        if(GameController.getInstance().getPlayerByOrientation(orientation) instanceof Human){
            humanIndex = index;
            return new HumanUI(orientation);
        } else {
            return new ComputerUI(orientation);
        }
    }
}
