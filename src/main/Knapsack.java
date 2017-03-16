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

    public boolean removeItem(Item item){
        if(mitems.remove(item)){
            mvalue -= item.getValue();
            mweight -= item.getWeight();
            return true;
        }else{
            return false;
        }
    }

    public int getID(){ return this.mid; }

    public int getWeightLimit() {
        return this.mweightLimit;
    }

    public int getValue() {
        return this.mvalue;
    }

    public int getWeight() {
        return this.mweight;
    }

    public LinkedList<Item> getItems() { return this.mitems; }

    public int getWeightLeft(){
        return this.mweightLimit - this.mweight;
    }

    public Knapsack getCopy(){
        Knapsack copy = new Knapsack(mid, mweightLimit);
        for(Item i : mitems){
            if(!copy.addItem(i)){
                System.err.print("COPY FAILED");
                System.exit(0);
            }
        }
        return copy;
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

    @Override
    public boolean equals(Object o) {
        if(o instanceof Knapsack){
            return ((Knapsack) o).getID() == mid;
        }
        return super.equals(o);
    }
}
