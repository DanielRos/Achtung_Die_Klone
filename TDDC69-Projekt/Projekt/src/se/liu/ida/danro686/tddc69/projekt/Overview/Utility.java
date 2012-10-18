package se.liu.ida.danro686.tddc69.projekt.Overview;

public class Utility {

    public static int random(int nr){
        return (int)(Math.random() * nr);
    }

    public static double random(double from, double to){
        return (Math.random() * (to-from)) + from;
    }
}
