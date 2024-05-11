package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.util.DisplayUtil;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class Board {

    private BorderPane pane;
    private Label text = new Label("Hello");

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
            GridPane grid = new GridPane();
            if(orientation == DisplayUtil.Orientation.UP || orientation == DisplayUtil.Orientation.DOWN){
                for(int i = 0; i < Constants.STARTING_CARDS; i++){
                    grid.addColumn(i, DisplayUtil.getCardImage(orientation));
                }
                grid.setHgap(DisplayConstants.gridGap);
            } else {
                for(int i = 0; i < Constants.STARTING_CARDS; i++){
                    grid.addRow(i, DisplayUtil.getCardImage(orientation));
                }
                grid.setVgap(DisplayConstants.gridGap);
            }
            grid.setGridLinesVisible(true);
            switch(orientation){
                case UP -> pane.setTop(grid);
                case DOWN -> pane.setBottom(grid);
                case LEFT -> pane.setLeft(grid);
                case RIGHT -> pane.setRight(grid);
            }
        }
        pane.setPrefSize(1000, 500);
    }

    public Parent getRoot(){
        return pane;
    }



}
