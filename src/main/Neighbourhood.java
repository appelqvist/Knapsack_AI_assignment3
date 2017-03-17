package main;

import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by A.Appelqvist on 3/16/17.
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

    public void setItemsNotUsed(LinkedList<Item> items){
        this.mitemsNotUsed = items;
    }

    public Knapsack getKnapsack(int id) {
        for(Knapsack k : mknapsacks){
            if(k.getID() == id){
                return k;
            }
        }
        System.err.println("getKnapsack()");
        return null; //PANG!
    }

    public ArrayList<Knapsack> getKnapsacks(){
        return mknapsacks;
    }

    public Neighbourhood getCopy(){
        return new Neighbourhood(mknapsacks, mitemsNotUsed);
    }
}
