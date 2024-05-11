package com.llan.mahjongfunsies.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DisplayUtil {
    public enum Orientation {
        UP(0),
        DOWN(180),
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

    public static ImageView getCardImage(Orientation orientation){
        ImageView viewer = new ImageView();
        Image image;
        try {
            InputStream stream = new FileInputStream(location + "WAN_1.png");
            image = new Image(stream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        viewer.setImage(image);
        if(orientation == Orientation.UP || orientation == Orientation.DOWN){
            viewer.setFitWidth(50);
        } else {
            viewer.setFitHeight(50);
        }
        viewer.setPreserveRatio(true);
        viewer.setRotate(orientation.getRotation());
        return viewer;
    }
}
