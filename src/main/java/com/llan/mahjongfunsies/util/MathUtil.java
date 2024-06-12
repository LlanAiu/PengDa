package com.llan.mahjongfunsies.util;

public class MathUtil {

    //exclusive between
    public static boolean between(int value, int min, int max){
        return min < value && value < max;
    }

    public static int randInt(int min, int max){
        return (int) Math.round(min + (max - min) * Math.random());
    }
}
