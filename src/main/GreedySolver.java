package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by A.Appelqvist on 3/14/17.
 */
public class GreedySolver {
    private ArrayList<Knapsack> mknapsacks;
    private LinkedList<Item> mitems;

    public GreedySolver(ArrayList<Knapsack> knapsacks, LinkedList<Item> items){
        this.mknapsacks = knapsacks;
        this.mitems = items;
    }

    public void start(){
        Collections.sort(mitems);
        System.out.println("Efter sort: "+mitems);
    }
}
