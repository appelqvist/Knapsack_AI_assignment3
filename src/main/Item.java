package main;

/**
 * Created by A.Appelqvist on 3/14/17.
 */
public class Item implements Comparable<Item>{
    private int mvalue;
    private int mweight;
    private float mprofitVal;

    public Item(int value, int weight){
        this.mvalue = value;
        this.mweight = weight;
        if(this.mweight == 0){
            this.mprofitVal = Float.MAX_VALUE;
        }else if(this.mvalue == 0){
            this.mprofitVal = -1 * mweight;
        }else {
            this.mprofitVal = (float) value / weight;
        }
    }

    public int getWeight(){
        return this.mweight;
    }

    public int getValue(){
        return this.mvalue;
    }

    public float getProfitVal(){
        return this.mprofitVal;
    }

    @Override
    public int compareTo(Item item) {
        return Math.round(item.getProfitVal()-this.mprofitVal);
    }

    @Override
    public String toString() {
        return "[W:"+this.mweight+",V:"+this.mvalue+",P:"+this.mprofitVal+"]";
    }
}
