package com.llan.mahjongfunsies.util;

public interface Subject {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();

    void setChanged();
}
