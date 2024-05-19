package com.llan.mahjongfunsies.util;

import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.ui.DisplayConstants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        viewer.setFitWidth(DisplayConstants.cardWidth);
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
        viewer.setFitWidth(DisplayConstants.cardWidth);
        viewer.setPreserveRatio(true);
        viewer.setRotate(orientation.getRotation());
        return viewer;
    }
}
