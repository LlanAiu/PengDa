package com.llan.mahjongfunsies.util;

public interface Episode {
    void onStart();
    void run();
    void end();
    boolean isFinished();
}
