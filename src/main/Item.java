package main;

/**
 * Created by A.Appelqvist on 3/14/17.
 */
public class Item implements Comparable<Item>{
    private int mvalue;
    private int mweight;
    private float mbenefit;

    public Item(int value, int weight){
        this.mvalue = value;
        this.mweight = weight;
        if(this.mweight == 0){
            this.mbenefit = Float.MAX_VALUE;
        }else if(this.mvalue == 0){
            this.mbenefit = -1 * mweight;
        }else {
            this.mbenefit = (float) value / weight;
        }
    }

    public int getWeight(){
        return this.mweight;
    }

    public int getValue(){
        return this.mvalue;
    }

    public float getBenefit(){
        return this.mbenefit;
    }

    @Override
    public int compareTo(Item item) {
        if(item.getBenefit() == mbenefit){
            return item.getWeight() - this.getWeight();
        }
        else{
            return Math.round(item.getBenefit()-this.mbenefit);
        }
    }

    @Override
    public String toString() {
        return "[W:"+this.mweight+",V:"+this.mvalue+",B:"+this.mbenefit +"]";
    }
}
