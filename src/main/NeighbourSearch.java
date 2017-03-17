package main;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Just static methods that uses to find a better solution.
 * with help of Neighboursearch
 * Created by A.Appelqvist.
 */
public class NeighbourSearch {

    /**
     * Search for a better solution
     * @param solution
     * @return
     */
    public static Neighbourhood improve(Neighbourhood solution){
        Neighbourhood bestNeighbor = solution;
        Neighbourhood current = solution;
        do {
            if(current.getValue() > bestNeighbor.getValue() ) {
                bestNeighbor = current;
            }
            current = rotate(bestNeighbor.getCopy());
        } while(current.getValue() > bestNeighbor.getValue());
        return bestNeighbor;
    }

    /**
     * Trying to rotate items between the different knapsacks
     * @param current
     * @return
     */
    public static Neighbourhood rotate(Neighbourhood current){
        ArrayList<Knapsack> knapsacks = current.getKnapsacks();
        Neighbourhood best = current.getCopy();
        Neighbourhood tmp;
        for(Knapsack from : knapsacks){
            for(Knapsack to : knapsacks){
                if(!from.equals(to)){
                    for(Item fromItem : (LinkedList<Item>)from.getItems().clone()){
                        tmp = triesToRotateItems(from, to, fromItem, current);
                        if(tmp != current){ //Has been a change, found local maximum
                            if(tmp.getValue() > best.getValue()){
                                best = tmp;
                            }
                        }
                        for(Item remove : (LinkedList<Item>)to.getItems().clone()){
                            Knapsack cp = to.getCopy();
                            cp.removeItem(remove);
                            tmp = triesToRotateItems(from, cp, fromItem, current);
                            if (tmp.getValue() > best.getValue()) {
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

    /**
     * Tries to move a item from a knapsack to a specific knapsack
     * @param from
     * @param to
     * @param i
     * @param current
     * @return The best neighbour
     */
    public static Neighbourhood triesToRotateItems(Knapsack from, Knapsack to, Item i, Neighbourhood current){
        Neighbourhood best = current.getCopy();
        Neighbourhood tmp = best;
        Knapsack toCp = to.getCopy();

        if (toCp.addItem(i)) {
            // If adding item to other sack was successful, remove it from the first sack
            Knapsack fromCp = from.getCopy();
            fromCp.removeItem(i);
            tmp.getKnapsacks().remove(tmp.getKnapsack(fromCp.getID()));
            tmp.getKnapsacks().remove(tmp.getKnapsack(toCp.getID()));
            tmp.getKnapsacks().add(fromCp);
            tmp.getKnapsacks().add(toCp);

            tmp = triesToAddItems(fromCp, tmp);

            if(tmp.getValue() > best.getValue()){
                best = tmp;
            }
        }
        return best;
    }

    /**
     * Find the best combination of items that can fit the space=totWeight. Returns as a ArrayList with
     * all the items.
     * @param totWeight
     * @param c
     * @param currentWeight
     * @param currentValue
     * @param current
     * @param best
     * @param bestValue
     * @param all
     * @return
     */
    public static ArrayList<Item> addNonIncluded(int totWeight, int c, int currentWeight,
                                                 int currentValue, ArrayList<Item> current,
                                                 ArrayList<Item> best, int bestValue, ArrayList<Item> all){
        if(currentValue > bestValue){
            bestValue = currentValue;
            best = (ArrayList<Item>)current.clone();
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
     * Tries to add items from nonincluded to a knapsack
     * @param k
     * @param current
     * return best neighbourhood
     */
    public static Neighbourhood triesToAddItems(Knapsack k, Neighbourhood current){
        Neighbourhood best = current.getCopy();
        Neighbourhood tmp = current.getCopy();
        Knapsack kCp = k.getCopy();
        LinkedList<Item> all = (LinkedList<Item>) current.getItemsNotUsed().clone();
        int weightLeft = kCp.getWeightLeft();
        ArrayList<Item> items = addNonIncluded(weightLeft, 0, 0, 0,
                                                new ArrayList<>(), new ArrayList<>(), 0,
                                                new ArrayList<>(all));

        if(items.size() > 0){
            //Found something
            LinkedList<Item> itemsleft = tmp.getItemsNotUsed();
            for(Item item : items){
                kCp.addItem(item);
                itemsleft.remove(item);
            }

            tmp.getKnapsacks().remove(tmp.getKnapsack(kCp.getID()));
            tmp.getKnapsacks().add(kCp);

            if(tmp.getValue() > best.getValue()){
                best = tmp;
            }
        }
        return best;
    }
}
