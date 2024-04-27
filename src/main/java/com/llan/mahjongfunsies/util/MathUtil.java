package com.llan.mahjongfunsies.util;

public class MathUtil {

    public static double clamp(double value, double min, double max){
        return Math.min(Math.max(value, min), max);
    }

    public static int randInt(int min, int max){
        return (int) Math.round(min + (max - min) * Math.random());
    }
}
