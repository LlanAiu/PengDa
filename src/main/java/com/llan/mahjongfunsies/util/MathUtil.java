package com.llan.mahjongfunsies.util;

public class MathUtil {

    public static double clamp(double value, double min, double max){
        return Math.min(Math.max(value, min), max);
    }

    //exclusive between
    public static boolean between(int value, int min, int max){
        return min < value && value < max;
    }

    public static int randInt(int min, int max){
        return (int) Math.round(min + (max - min) * Math.random());
    }

    public static double random(double min, double max){
        return min + (max - min) * Math.random();
    }

    public static double round(double value, int decimals){
        return Math.round(Math.pow(10, decimals) * value) / Math.pow(10, decimals);
    }
}
