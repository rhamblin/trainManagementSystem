/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

import Train.AtPickUpLocation;
import Train.Train;
import TrainManagementSystem.TrainManagementSystem;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author rhamblin
 */
public class Passenger extends User {

   

    /*
     * OVERVIEW: ........must fill.......
     */
     private TrainManagementSystem tms;

    public Passenger(TrainManagementSystem tms, String name) {
        super(name);
        this.tms = tms;
    }
    
    @Override
    public void CommandLineUserDisplay() {
        /*EFEECTS:     
        *DISPLAY A COOMAND LINE INTERFACE WHICH WILL PRESENT USER 
        * WITH OPTIONS TO BOARD A TRAIN OF TO CHANGE STATE
        */
        
        ArrayList<Train> trains = tms.getTrainsInSystem();
        if(trains == null) return;
        Scanner scanner = new Scanner(System.in);
        String name;
        int choice,count =0;
        
        System.out.print("\nSelect your train: \n");
             
             for(Train t: trains) {
                if(t.getState() instanceof AtPickUpLocation) {
                    System.out.println(t.simplifiedToString());
                    count++;
                }
            }
            
             if (count == 0) { System.out.println("No trains found"); return;}
             
             //validate choice
             do{
                System.out.print("\nEnter choice: ");
                choice  = scanner.nextInt(); 
            } while(!isValidChoice(choice));
         
            if(!tms.getTrain(choice).boardPassenger())
                System.out.println("Train is at full capacity. Please Select another Train");
            else{
                 System.out.println("Welcome Aboard!");
            }
        
    }
     private boolean isValidChoice(int choice) {
        ArrayList<Train> trains = tms.getTrainsInSystem();
        
        for(Train t: trains) {
                if(t.getState() instanceof AtPickUpLocation && t.getId() == choice) {
                    return true;
                }
            }
        return false;
    }
    public boolean boardTrain(Train t) {
         /*
         * EFFECTS: This method boards a passenger to the train
         */
        return t.boardPassenger();
    }
    
    
    
}
