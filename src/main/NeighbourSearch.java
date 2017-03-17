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

    /**
     *
     * @param k
     * @param current
     */
    public static Neighbourhood triesToAddItems(Knapsack k, Neighbourhood current){
        Neighbourhood best = current;
        ArrayList<Item> nonUsed = new ArrayList<Item>(current.getItemsNotUsed());
        Collections.sort(nonUsed);

        Knapsack tmp;
        LinkedList<Item> convertLL;
        Neighbourhood tmpNeighbourhood;
        ArrayList<Item> tmpNonUsed;
        int start;

        for(int i = 0; i < nonUsed.size(); i++){
            tmp = k.getCopy();
            tmpNonUsed = (ArrayList<Item>) nonUsed.clone();
            start = i;
            while(tmp.addItem(nonUsed.get(start))){
                tmpNonUsed.remove(start);
                start = i+1%nonUsed.size();
            }

            convertLL = new LinkedList<>(tmpNonUsed);
            tmpNeighbourhood = current.getCopy();
            tmpNeighbourhood.getKnapsacks().remove(tmpNeighbourhood.getKnapsack(tmp.getID()));
            tmpNeighbourhood.getKnapsacks().add(tmp);
            tmpNeighbourhood.setItemsNotUsed(convertLL);

            if(tmpNeighbourhood.getValue() > best.getValue()){
                best = tmpNeighbourhood;
                //is a break here bad? when i has sorted before?
            }
        }

        return best;
    }
}
