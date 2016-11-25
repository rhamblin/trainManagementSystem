/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Train;

import TrainManagementSystem.FileLogger;
import Users.*;

/**
 *
 * @author rhamblin
 */
public class Train {
    
    /*
     * OVERVIEW:
     */
    
    //Atrributes
    private State currentState;
    private int id;
    private int capacity;
    private int currentPassengersAboard;
    private String driver_name;
    private String startLocation;
    private String endLocation;
    
    static int numberOfTrainsInSystem=0;

    public Train(int capacity, String startLocation, String endLocation) {
        this.capacity = capacity;
        this.startLocation = startLocation;
        this.endLocation = endLocation;

        this.currentState = new AtPickUpLocation();
        this.currentState.logEventToFile(this);
        this.addSelfToFile();
        this.id = numberOfTrainsInSystem;
        numberOfTrainsInSystem++;
    }

    public Train(int capacity, String startLocation, String endLocation, String driver) {
        this.capacity = capacity;
        this.driver_name = driver;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.currentState = new AtPickUpLocation();
        this.currentState.logEventToFile(this);
        numberOfTrainsInSystem++;
    }

    public Train(int id, int capacity, int currentPassengersAboard, String driver_name, String startLocation, String endLocation) {
        this.currentState = new AtPickUpLocation();
        this.currentState.logEventToFile(this);
        this.id = id;
        this.capacity = capacity;
        this.currentPassengersAboard = currentPassengersAboard;
        this.driver_name = driver_name;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        numberOfTrainsInSystem++;
    }
      
    //Behaviours
    public int getId() {
         /*
         * EFFECTS:
         * 
         * MODIFIES:
         * 
         * REQUIRES:
         */
        return id;
    }
    
    public boolean boardPassenger(){
        
        FileLogger fl = new FileLogger();
        /*
         * EFFECTS:
         * 
         * MODIFIES:
         * 
         * REQUIRES:
         */
        String oldTrain = this.toString();
        if (this.currentPassengersAboard == this.capacity)
            return false;
        else {
            this.currentPassengersAboard++;
            fl.rewriteContentOfFile(FileLogger.trainsInSystemFileName, oldTrain, this.toString());
            return true;
        }
    }
    
    public boolean boardDriver(TrainDriver t){
        /*
         * EFFECTS:
         * 
         * MODIFIES:
         * 
         * REQUIRES:
         */
        if (t instanceof TrainDriver &&  this.driver_name.equals(" ") ) {
            this.driver_name = t.getName();
            return true;
        }
        
        else return false;
    }
    
    public void setState(State s) {
         /*
         * EFFECTS:
         * 
         * MODIFIES:
         * 
         * REQUIRES:
         */
        this.currentState = s;
        s.logEventToFile(this);
//FileLogger fl = new FileLogger();
        ;
    }
    
    public State getState(){
         /*
         * EFFECTS:
         * 
         * MODIFIES:
         * 
         * REQUIRES:
         */
        return this.currentState;
    }

    public String simplifiedToString(){
        return this.id + " "+ this.startLocation + " "+this.endLocation ;
    }
    @Override
    public String toString() {
        return id + " " + driver_name + " " + capacity + " " + currentPassengersAboard + " " + startLocation + " " + endLocation + " " + currentState.toString() ;
    }

    private void addSelfToFile() {
    //this method writes to file its information   
    }
    
    
    
}
