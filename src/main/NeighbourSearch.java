package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by A.Appelqvist on 3/16/17.
 */
public class NeighbourSearch {

    public static Neighbourhood improve(Neighbourhood current, Neighbourhood best, boolean first){
        if(current.getValue() < best.getValue() || first){
            return best;
        }else{
            Neighbourhood foundNeighbour = rotate(current.getCopy());
            return improve(rotate(foundNeighbour), current, false);
        }
    }

    public static Neighbourhood rotate(Neighbourhood current){
        ArrayList<Knapsack> knapsacks = current.getKnapsacks();
        Neighbourhood best = current;
        Neighbourhood tmp;
        for(Knapsack from : knapsacks){
            for(Knapsack to : knapsacks){
                if(!from.equals(to)){
                    for(Item fromItem : from.getItems()){
                        tmp = triesToRotateItems(from, to, fromItem, current);
                        if(tmp != current){ //Has been a change, found local maximum
                            if(tmp.getValue() > best.getValue()){
                                best = tmp;
                            }
                        }

                        for(Item remove : to.getItems()){
                            Knapsack cp = to.getCopy();
                            cp.removeItem(remove);
                            tmp = triesToRotateItems(from, to, fromItem, current);
                            if (tmp.getValue() > tmp.getValue()) {
                                //Also needs to add the item we removed to not included.
                                best = tmp;
                                best.addItemNotUses(remove);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return best;
    }

    public static Neighbourhood triesToRotateItems(Knapsack from, Knapsack to, Item i, Neighbourhood current){
        Neighbourhood best = current;
        Knapsack toCp = to.getCopy();

        return best;
    }

    public static void triesToAddItems(Knapsack k, Neighbourhood current){
        Neighbourhood best = current;
        Knapsack tmp;
        LinkedList<Item> nonUsed = current.getItemsNotUsed();
        //Collections.sort(nonUsed);  //No need, maybe to use later
        for(Item item : nonUsed){
            tmp = k.getCopy();
            if(tmp.addItem(item)){

            }
        }
    }
}
