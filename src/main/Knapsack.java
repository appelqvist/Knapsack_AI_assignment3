package main;

import java.util.LinkedList;

/**
 * Created by A.Appelqvist on 3/14/17.
 */
public class Knapsack implements Comparable<Knapsack>{
    private int mid;
    private LinkedList<Item> mitems;
    private int mweightLimit;
    private int mvalue;
    private int mweight;

    public Knapsack(int id, int weightLimit){
        this.mid = id;
        this.mweightLimit = weightLimit;
    }

    public int getWeightLimit() {
        return this.mweightLimit;
    }

    public int getValue() {
        return this.mvalue;
    }

    public int getWeight() {
        return this.mweight;
    }

    public int getWeightLeft(){
        return this.mweightLimit - this.mweight;
    }

    @Override
    public String toString() {
        return "["+mid+":"+"WL:"+mweightLimit+", W:"+mweight+", V:"+mvalue+"]";
    }

    @Override
    public int compareTo(Knapsack knapsack) {
        return getWeightLeft() - knapsack.getWeightLeft();
    }
}
