package main;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Start class
 * Created by A.Appelqvist.
 */
public class Main {
    public static void main(String[] args) {
        new Main().startApplication();
    }

    private Neighbourhood startGreedy(ArrayList<Knapsack> knapsacks, LinkedList<Item> items){
        GreedySolver gs = new GreedySolver(knapsacks, (LinkedList<Item>)items.clone());
        gs.startGreedy();
        return gs.getSolutionAsNeighbourhood();
    }

    private Neighbourhood startNeighbourSearch(ArrayList<Knapsack> knapsacks, LinkedList<Item> items){
        Neighbourhood currentNeighbourhood = startGreedy(knapsacks, items);
        Neighbourhood improvedSolutionNeighbourhood = NeighbourSearch.improve(currentNeighbourhood);
        return improvedSolutionNeighbourhood;
    }

    private void printResult(Neighbourhood n, ArrayList<Item> all){
        ArrayList<Item> itemsinNeighbourhood = n.getAllItems();

        boolean ok = true;
        for(Item i : itemsinNeighbourhood){
            if(!all.remove(i)){
                ok = false;
            }
        }
        if(!all.isEmpty()){
            ok = false;
        }

        System.out.println(n+"\nTotal value: "+n.getValue());
        System.out.println("Validate: "+ok);
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
                    System.out.println("Greedy");
                    printResult(startGreedy(knapsacks, items), new ArrayList<>(items));
                } else if (answer == 2) {
                    System.out.println("Neighbour");
                    printResult(startNeighbourSearch(knapsacks,items), new ArrayList<>(items));
                }
            }
        }
    }
}
