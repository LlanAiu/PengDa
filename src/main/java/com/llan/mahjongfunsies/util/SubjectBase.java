package com.llan.mahjongfunsies.util;

import java.util.ArrayList;
import java.util.List;

public abstract class SubjectBase implements Subject{

    private static List<SubjectBase> subjects = new ArrayList<>();
    private List<Observer> observers;
    private boolean changed;

    public SubjectBase(){
        subjects.add(this);
        changed = false;
        observers = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        if(changed) {
            for(Observer observer : observers){
                observer.update(this);
            }
            changed = false;
        }
    }

    @Override
    public void setChanged() {
        changed = true;
    }

    public static void periodicAll(){
        for(SubjectBase subjects : subjects){
            subjects.notifyObservers();
        }
    }
}
