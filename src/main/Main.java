package main;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by A.Appelqvist on 3/13/17.
 */
public class Main {
    public static void main(String[] args) { new Main().startApplication(); }

    public void startApplication(){
        int answer = Integer.parseInt(JOptionPane.showInputDialog(null ,"How do you want to solve the Knapsack-Program:\n0:Exit \n1: Greedy (not optimal)"));
        if(answer == 0){
            System.out.println("END");
        }else {
            ArrayList<Knapsack> knapsacks = new ArrayList<>();
            LinkedList<Item> items = new LinkedList<>();
            String line = "";
            int weight, value;
            try {
                BufferedReader br = new BufferedReader(new FileReader("src/main/sample_input.txt"));
                int nbrOfKnapsacks = Integer.parseInt(br.readLine());
                for(int i = 0; i < nbrOfKnapsacks; i++){
                    knapsacks.add(new Knapsack(i, Integer.parseInt(br.readLine())));
                }
                while (null != (line = br.readLine())){
                    weight = Integer.parseInt(line.split(",")[0]);
                    value = Integer.parseInt(line.split(",")[1]);
                    items.addFirst(new Item(value, weight));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Knapsacks: "+knapsacks.toString());
            System.out.println("Items: "+items.toString());

            GreedySolver gs = new GreedySolver(knapsacks, items);
            gs.start();
        }
    }
}
