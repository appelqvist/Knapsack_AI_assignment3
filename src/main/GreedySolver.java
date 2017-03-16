package main;

import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by A.Appelqvist on 3/14/17.
 */

public class GreedySolver {
    private ArrayList<Knapsack> mknapsacks;
    private LinkedList<Item> mitems;
    private LinkedList<Item> mitemsNotUsed;

    public GreedySolver(ArrayList<Knapsack> knapsacks, LinkedList<Item> items){
        this.mknapsacks = knapsacks;
        this.mitems = items;
        mitemsNotUsed = new LinkedList<>();
    }

    public ArrayList<Knapsack> startGreedy(){
        Collections.sort(mitems);
        fillGreedy(mitems);
        System.out.println(mknapsacks);
        System.out.println("Not used:"+ mitemsNotUsed);
        return mknapsacks;
    }

    /**
     * Going through every items in the list and if some of
     * the knapsacks has space put it in. Otherwise skip.
     * @param itemsLeft
     */
    private void fillGreedy(LinkedList<Item> itemsLeft){
        if(mitems.isEmpty()) return;
        //Sort the knapsacks so the with most weight left starts.
        //This is for keeping the knapsacks as even as possible
        Collections.sort(mknapsacks);
        Item item = itemsLeft.removeFirst();
        boolean success = false;
        for(Knapsack k : mknapsacks){
            if(k.addItem(item)){
                success = true;
                break;
            }
        }
        if(!success)
            mitemsNotUsed.add(item);
        fillGreedy(itemsLeft);
    }

}
