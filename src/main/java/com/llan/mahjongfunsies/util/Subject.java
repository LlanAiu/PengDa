package com.llan.mahjongfunsies.util;

import java.util.ArrayList;
import java.util.List;

public interface Subject {
    List<Observer> observers = new ArrayList<>();

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();

    void setChanged();
}
