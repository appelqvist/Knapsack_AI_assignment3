package main;

import sun.awt.image.ImageWatched;

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
            return improve(rotate(foundNeighbour), best, false);
        }
    }

    public static Neighbourhood rotate(Neighbourhood current){
        ArrayList<Knapsack> knapsacks = current.getKnapsacks();
        Neighbourhood best = current.getCopy();
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
        Neighbourhood tmp;
        Knapsack toCp = to.getCopy();

        if (toCp.addItem(i)) {
            // If adding item to other sack was successful, remove it from the first sack
            Knapsack fromCp = from.getCopy();
            fromCp.removeItem(i);

            // Try adding a non-included item to the first sack
            tmp = triesToAddItems(fromCp, current);

            if(tmp.getValue() > best.getValue()){
                best = tmp;
            }
        }
        return best;
    }

    public static ArrayList<Item> addNonIncluded(int totWeight, int c, int currentWeight,
                                                 int currentValue, ArrayList<Item> current,
                                                 ArrayList<Item> best, int bestValue, ArrayList<Item> all){
        if(currentValue > bestValue){
            bestValue = currentValue;
            best = (ArrayList<Item>)current.clone();
            System.out.println("nytt bästa!");
        }

        //Gått genom alla items
        if(c >= all.size()){
            return best;
        }

        //Finns fler att gå genom (all)
        Item item = all.get(c);
        if(totWeight >= currentWeight + item.getWeight()){
            ArrayList<Item> currentClone = (ArrayList<Item>)current.clone();
            ArrayList<Item> allClone = (ArrayList<Item>)all.clone();
            currentClone.add(item);
            allClone.remove(item);
            return addNonIncluded(totWeight, 0, currentWeight + item.getWeight(),currentValue + item.getValue(),
                    currentClone, best, bestValue, allClone);
        }else{
             return addNonIncluded(totWeight, c+1, currentWeight, currentValue, current, best, bestValue, all);
        }
    }


    /**
     *
     * @param k
     * @param current
     */
    public static Neighbourhood triesToAddItems(Knapsack k, Neighbourhood current){
        Neighbourhood best = current.getCopy();
        Neighbourhood tmp = current.getCopy();
        LinkedList<Item> all = (LinkedList<Item>) current.getItemsNotUsed().clone();

        int weightLeft = k.getWeightLeft();
        ArrayList<Item> items = addNonIncluded(weightLeft, 0, 0, 0,
                                                new ArrayList<>(), new ArrayList<>(), 0,
                                                new ArrayList<>(all));

        if(items.size() > 0){
            //Found something
            LinkedList<Item> itemsleft = tmp.getItemsNotUsed();
            for(Item item : items){
                k.addItem(item);
                itemsleft.remove(item);
            }
            tmp.getKnapsacks().remove(tmp.getKnapsack(k.getID()));
            tmp.getKnapsacks().add(k);

            if(tmp.getValue() > best.getValue()){
                best = tmp;
            }
        }
        return best;
    }
}
