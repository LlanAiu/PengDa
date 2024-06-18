package com.llan.mahjongfunsies.mahjong.environment;

public interface Status {
    void periodic();
    boolean shouldTransition();
    void onTransition();
}
