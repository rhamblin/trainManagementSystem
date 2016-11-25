/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

import Train.*;

/**
 *
 * @author rhamblin
 */

import Train.*;
import TrainManagementSystem.TrainManagementSystem;
public class TrainDriver extends User {

    /*
     * OVERVIEW: ......must fill....
     */
    
    private String password;
    private Train currentTrainBeingDriven;
    private TrainManagementSystem tms;
    private int id; 
    public static int numberOfTrainDrivers = 0;
    
    public TrainDriver(TrainManagementSystem tms, String name, String password) {
        super(name);
        this.password = password;
        this.tms = tms;
        id = numberOfTrainDrivers;
        numberOfTrainDrivers++;
        
    }

    public TrainDriver(TrainManagementSystem tms, Train currentTrainBeingDriven, String name, String password,int id) {
        super(name);
        this.password = password;
        this.currentTrainBeingDriven = currentTrainBeingDriven;
        this.tms = tms;
        id = numberOfTrainDrivers;
        numberOfTrainDrivers++;
        
    }
    
    @Override
    public String getName (){
        return super.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public void CommandLineUserDisplay() {
        /*EFEECTS:     
        *DISPLAY A COOMAND LINE INTERFACE WHICH WILL PRESENT USER 
        * WITH OPTIONS TO BOARD A TRAIN OF TO CHANGE STATE
        */
        
        
        
    }
    
    public boolean boardTrain(Train t){
        /*
         * EFFECTS: This method asigns this as the train's, t, driver if and only if
         *          the train doesn't have one already. When they are boarded a train is 
         *          considerd at pick up location.
         * this.currentTrainBeingDriven.setState(new AtPickUpLocation() );
         */
       return false;
        
        
    }
          /*
         * EFFECTS: This method changes the state of the train
         * 
         * MODIFIES: 
         * 
         * 
         */
        
    public void changeStateOfTrain(State s) {
  
        this.currentTrainBeingDriven.setState(s);
    }

    @Override
    public String toString() {
        return id + " " + super.getName() + " " + password + " " + currentTrainBeingDriven + " TrainDriver";
    }
    
    
    
    
}
