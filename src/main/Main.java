package main;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by A.Appelqvist on 3/13/17.
 */
public class Main {
    public static void main(String[] args) {
        new Main().startApplication();
        //new Main().test();
    }

    private Neighbourhood startGreedy(ArrayList<Knapsack> knapsacks, LinkedList<Item> items){
        GreedySolver gs = new GreedySolver(knapsacks, items);
        gs.startGreedy();
        return gs.getSolutionAsNeighbourhood();
    }

    private Neighbourhood startNeighbourSearch(ArrayList<Knapsack> knapsacks, LinkedList<Item> items){
        Neighbourhood currentNeighbourhood = startGreedy(knapsacks, items);
        Neighbourhood improvedSolutionNeighbourhood = NeighbourSearch.improve(currentNeighbourhood,currentNeighbourhood, true);
        return improvedSolutionNeighbourhood;
    }

    private void printResult(Neighbourhood n){
        System.out.println(n);
    }

    private void test(){
        LinkedList<Item> a = new LinkedList<>();
        a.add(new Item(6,4));
        a.add(new Item(4,2));
        a.add(new Item(1,1));
        a.add(new Item(3,3));
        a.add(new Item(2,7));
        a.add(new Item(2,4));

        Knapsack k = new Knapsack(0,7);
        k.addItem(new Item(2,2));
        Knapsack k2 = new Knapsack(1,6);
        k2.addItem(new Item(5,6));

        ArrayList<Knapsack> knapsacks = new ArrayList<>();
        knapsacks.add(k);
        knapsacks.add(k2);

        Neighbourhood nh = new Neighbourhood(knapsacks, a);
        nh = NeighbourSearch.triesToAddItems(k,nh);
        System.out.println(nh.getKnapsacks());
        System.out.println(nh.getItemsNotUsed());
    }

    private void startApplication() {
        while (true) {
            int answer = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "How do you want to solve the Knapsack-Program:\n" +
                            "0:Exit \n" +
                            "1: greedy\n" +
                            "2: greedy + neighboursearch"));
            if (answer == 0) {
                System.exit(0);
            } else {
                ArrayList<Knapsack> knapsacks = new ArrayList<>();
                LinkedList<Item> items = new LinkedList<>();
                String line;
                int weight, value;
                try {
                    BufferedReader br = new BufferedReader(new FileReader("src/main/sample_input.txt"));
                    int nbrOfKnapsacks = Integer.parseInt(br.readLine());
                    for (int i = 0; i < nbrOfKnapsacks; i++) {
                        knapsacks.add(new Knapsack(i, Integer.parseInt(br.readLine())));
                    }
                    while (null != (line = br.readLine())) {
                        weight = Integer.parseInt(line.split(",")[0]);
                        value = Integer.parseInt(line.split(",")[1]);
                        items.addFirst(new Item(value, weight));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (answer == 1) {
                    printResult(startGreedy(knapsacks, items));
                } else if (answer == 2) {
                    startNeighbourSearch(knapsacks,items);
                }
            }
        }
    }
}
