package main;

import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A Neighbourhood
 * Created by A.Appelqvist.
 */
public class Neighbourhood {
    private ArrayList<Knapsack> mknapsacks;
    private LinkedList<Item> mitemsNotUsed;

    public Neighbourhood(ArrayList<Knapsack> knapsacks, LinkedList<Item> itemsNotUsed){
        mknapsacks = new ArrayList<>();
        for(Knapsack k : knapsacks){
            mknapsacks.add(k.getCopy());
        }
        mitemsNotUsed = (LinkedList<Item>)itemsNotUsed.clone();
    }

    public int getValue(){
        int sum = 0;
        for(Knapsack k : mknapsacks){
            sum += k.getValue();
        }
        return sum;
    }

    public void addItemNotUses(Item item){
        mitemsNotUsed.addFirst(item);
    }

    public LinkedList<Item> getItemsNotUsed(){
        return mitemsNotUsed;
    }

    public Knapsack getKnapsack(int id) {
        for(Knapsack k : mknapsacks){
            if(k.getID() == id){
                return k;
            }
        }
        System.err.println("getKnapsack()");
        return null;
    }

    public ArrayList<Knapsack> getKnapsacks(){
        return mknapsacks;
    }

    public Neighbourhood getCopy(){
        return new Neighbourhood(mknapsacks, mitemsNotUsed);
    }

    public ArrayList<Item> getAllItems(){
        ArrayList<Item> all = new ArrayList<>();
        for(Item i : mitemsNotUsed){
            all.add(i);
        }
        for(Knapsack k : mknapsacks){
            for(Item i : k.getItems()){
                all.add(i);
            }
        }
        return all;
    }

    @Override
    public String toString() {
        return "KNAPSACKS:\n" + mknapsacks + "\n";
    }
}
