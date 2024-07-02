package com.llan.mahjongfunsies.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;

public final class DisplayConstants {
    public static final int CARD_WIDTH = 40;
    public static final int CARD_LENGTH = 54;

    public static final int GRID_GAP = 2;

    public static final Insets TOP_INSETS = new Insets(5, 75, 0, 75);
    public static final Insets BOTTOM_INSETS = new Insets(0, 75, 5, 75);
    public static final Insets LEFT_INSETS = new Insets(20, 0, 20, 5);
    public static final Insets RIGHT_INSETS = new Insets(20, 10, 20, 0);

    public static final double SELECTED_OFFSET = -5;
    public static final double SET_OFFSET = -8;

    public static final int FRAME_RATE_MILLIS = 50;
    public static final int POST_WAIT_MILLIS = 2500;
    //automatically floored
    public static final int MAX_POLLED_FRAMES = POST_WAIT_MILLIS / FRAME_RATE_MILLIS;

    public static final Scene NORMAL_SCENE = new Scene(Board.getInstance().getRoot());
    public static final Scene TRAINING_SCENE = new Scene(TrainingScreen.getInstance().getRoot());
}
