package com.llan.mahjongfunsies.mahjong.environment;

public interface State {
    void periodic();
    boolean shouldTransition();
    void onTransition();
}
