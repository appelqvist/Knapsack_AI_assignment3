package main;

import java.util.LinkedList;

/**
 * Created by A.Appelqvist on 3/14/17.
 */
public class Knapsack {
    private int mid;
    private LinkedList<Item> mitems;
    private int mweightLimit;
    private int mvalue;
    private int mweight;

    public Knapsack(int id, int weightLimit){
        this.mid = id;
        this.mweightLimit = weightLimit;
    }

    @Override
    public String toString() {
        return "["+mid+":"+"WL:"+mweightLimit+", W:"+mweight+", V:"+mvalue+"]";
    }
}
