package Users;

import Train.AtPickUpLocation;
import Train.Train;
import TrainManagementSystem.TrainManagementSystem;
import java.util.ArrayList;
import java.util.Scanner;

 /**
     * OVERVIEW: This immutable abstraction represents a passenger who will be using the system.
     * AF(c) = a person/user, p,  who only wants to board a train. c's name is p.name. 
     * RepInvariant: c.name!= null i.e. same as parent class.
     */

public class Passenger extends User {
   
     private TrainManagementSystem tms;

     /**
      * Effects: Creates a passenger
      * Modifies: this
      * Requires: tms != null and name!=null
      * @param tms
      * @param name 
      */
    public Passenger(TrainManagementSystem tms, String name) {
        super(name);
        this.tms = tms;
    }
    
    /**
     * Effects: This presents an interface for a passenger to interact with the system through console. It presents them 
     *                   with a list of trains and lets them select one.
     */
    @Override
    public void CommandLineUserDisplay() {
           
        ArrayList<Train> trains = tms.getTrainsInSystem();
        if(trains == null) return;
        Scanner scanner = new Scanner(System.in);
        String choice = null;
        int count =0;
        
        System.out.print("\nSelect your train: \n");
             
             for(Train t: trains) {
                if(t.getState() instanceof AtPickUpLocation) {
                    displayInModifiedFormat(t);
                    count++;
                }
            }
            
             if (count == 0) { System.out.println("No trains found"); return;}
               int c;
               
             //validate choice
             do{
                
               
                 System.out.print("\nEnter \"exit\" to cancel.\nEnter choice: ");
                choice  = scanner.nextLine(); 
                
                if(choice.equals("exit")) return;
              
               try{
                   c = Integer.parseInt(choice);
                   
               } catch (NumberFormatException e ){
                   System.out.println("Invalid entry!");
                  this.CommandLineUserDisplay();
                  return;
               }
                
                if(!isValidChoiceForTrainId(c)) {
                   System.out.println("Invalid entry!");
                  this.CommandLineUserDisplay();
                  return;
                }
               
            } while(!isValidChoiceForTrainId(c));
     
            if(!tms.getTrain(c).boardPassenger()){
                System.out.println("\nTrain is at full capacity. Please Select another Train");
               this.CommandLineUserDisplay();}
            else{
                 System.out.println("\nWelcome Aboard!");
                 
                 
            }
        
    }
    
    /**
     * Effects: This is a helper method that checks whether an id matches an actual train.
     *                  Returns true if it does, false if otherwise
     * @return 
     */
     private boolean isValidChoiceForTrainId(int choice) {
        ArrayList<Train> trains = tms.getTrainsInSystem();
        
        for(Train t: trains) {
                if(t.getState() instanceof AtPickUpLocation && t.getId() == choice) {
                   
                    return true;
                }
            } 
        return false;
    }
     
     /**
      * Effects: Boards this passenger to a train and returns true if successful
      * Requires: t!=null
      * @param t
      * @return true is boarding was successful false otherwise 
      */
    public boolean boardTrain(Train t) {
        if(t==null) throw new NullPointerException();
           return t.boardPassenger();
    }

    /**
     * Effects: Displays to console a train in format:
     *  "From: " + t.getStartLocation()  + " To: " + t.getEndLocation() + 
                        " Seats Left: " + (t.getCapacity() - t.getCurrentPassengersAboard())
      * Requires: t != null 
     * 
     * @param t 
     */
    private void displayInModifiedFormat(Train t) {
           
        if(t==null ) throw new NullPointerException();
       
        System.out.println(
                
                "From:" + t.getStartLocation()  + " \tTo:" + t.getEndLocation() + 
                        "\tSeats Left: " + (t.getCapacity() - t.getCurrentPassengersAboard())
                +"\t----> To choose this train enter: " + t.getId()
        );
    }
    
    /**
     * Effects: Returns a string rep representation of a passenger 
     *
      * @return super.getName()
      */
     @Override
    public String toString() {
        return super.getName();
    }
    
}
