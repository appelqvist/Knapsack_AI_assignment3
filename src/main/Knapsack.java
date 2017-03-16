package main;

import java.util.LinkedList;

/**
 * Created by A.Appelqvist on 3/14/17.
 */
public class Knapsack implements Comparable<Knapsack>{
    private int mid;
    private LinkedList<Item> mitems;
    private int mweightLimit;
    private int mvalue = 0;
    private int mweight = 0;

    public Knapsack(int id, int weightLimit){
        this.mid = id;
        this.mweightLimit = weightLimit;
        mitems = new LinkedList<>();
    }

    public boolean addItem(Item item){
        if(item.getWeight() <= getWeightLeft()){
            mitems.add(item);
            mweight += item.getWeight();
            mvalue += item.getValue();
            return true;
        }
        return false;
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
        String out = "\n****************************************************************************************************************************************\n" +
                "Knapsack: "+mid +", [WL, :"+mweightLimit+"], [W:"+mweight+"], [V:"+mvalue+"], ITEMS: ("+mitems.toString()+")\n" +
                "****************************************************************************************************************************************\n";

        return out;
    }

    @Override
    public int compareTo(Knapsack knapsack) {
        return knapsack.getWeightLeft() - getWeightLeft();
    }
}
