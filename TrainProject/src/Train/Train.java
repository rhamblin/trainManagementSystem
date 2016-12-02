package Train;

import TrainManagementSystem.FileLogger;
import TrainManagementSystem.TrainManagementSystem;
import Users.*;

/**
 * OVERVIEW: This mutable abstraction represents a single train
 *
 * AF(c) = A train, t, with capacity of t.capacity. Going from t.startLocation
 * to t.EndLocation t.driver_name is the driver and it is carrying
 * t.currentPassengersAboard passengers.
 *
 * Rep Invariant: capacity more than 0 or startLocation is empty or endLocation
 * is empty
 */
public class Train {

    //Atrributes
    private State currentState;
    private int id;
    private int capacity;
    private int currentPassengersAboard;
    private String driver_name;
    private String startLocation;
    private String endLocation;

    static int numberOfTrainsInSystem = 0;

    /**
     *
     * @param capacity
     * @param startLocation
     * @param endLocation
     */
    public Train(int capacity, String startLocation, String endLocation) {
        this.capacity = capacity;
        this.startLocation = startLocation;
        this.endLocation = endLocation;

        this.currentState = new AtPickUpLocation();
        this.currentState.logEventToFile(this);
        //   this.addSelfToFile();
        this.id = numberOfTrainsInSystem;
        numberOfTrainsInSystem++;
    }

    /**
     *
     * @param capacity
     * @param startLocation
     * @param endLocation
     * @param driver
     */
    public Train(int capacity, String startLocation, String endLocation, String driver) {
        this.capacity = capacity;
        this.driver_name = driver;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.currentState = new AtPickUpLocation();
        this.currentState.logEventToFile(this);
        numberOfTrainsInSystem++;
    }

    /**
     *
     * @param id
     * @param capacity
     * @param currentPassengersAboard
     * @param driver_name
     * @param startLocation
     * @param endLocation
     * @param state
     */
    public Train(int id, int capacity, int currentPassengersAboard, String driver_name, String startLocation, String endLocation, String state) {
        this.currentState = State.generateState(state);
        //  this.currentState.logEventToFile(this);
        this.id = id;
        this.capacity = capacity;
        this.currentPassengersAboard = currentPassengersAboard;
        this.driver_name = driver_name;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        numberOfTrainsInSystem++;
    }

    //Behaviours
    /**
     * EFFECTS: Allows a passenger to board a train. Returns true if capacity is
     * not filled and boarding was successful else it returns false.
     *
     *
     * @return
     */
    public boolean boardPassenger() {

        FileLogger fl = new FileLogger();
        /*
         * EFFECTS:
         * 
         * MODIFIES:
         * 
         * REQUIRES:
         */
        String oldTrain = this.toString();
        if (this.currentPassengersAboard == this.capacity) {
            return false;
        } else {
            this.currentPassengersAboard++;
            fl.rewriteContentOfFile(FileLogger.trainsInSystemFileName, oldTrain, this.toString());
            return true;
        }
    }

    /**
     * EFFECTS: Allows a train driver to board a train
     *
     * MODIFIES: t
     *
     * REQUIRES: t an instance of TrainDriver
     *
     * @param t
     * @return
     */
    public boolean boardDriver(TrainDriver t) {

        if (t instanceof TrainDriver && this.driver_name.equals("null")) {//| !this.driver_name.isEmpty())  ) {

            String str = t.toString(), str2 = this.toString();

            t.setCurrentTrainBeingDriven(this);

            this.driver_name = t.getName();
            FileLogger fl = new FileLogger();
            fl.rewriteContentOfFile(FileLogger.trainsInSystemFileName, str2, this.toString());
            fl.rewriteContentOfFile(FileLogger.usersFileName, str, t.toString());

            return true;
        } else {
            System.out.println("This train already has a driver. Check to be sure it isn't already you");
            return false;
        }
    }

    /**
     * EFFECTS: Returns a representation of a train where only the id and
     * start/end location is returned
     *
     * @return
     */
    public String simplifiedToString() {
        return this.id + " " + this.startLocation + " " + this.endLocation;
    }

    @Override

    /**
     * @EFFECTS: Returns a string representation of a train as defined but the
     * abstraction function
     *
     */
    public String toString() {
        return id + " " + driver_name + " " + capacity + " " + currentPassengersAboard + " " + startLocation + " " + endLocation + " " + currentState.toString();
    }

    /**
     *
     * @return
     */
    public boolean repOK() {
        if (this.capacity <= 0 | this.startLocation.equals("") | this.endLocation.equals("")) {
            return false;
        }
        return true;
    }

    //getters  
    /**
     * Effects: returns the id of this
     *
     * @return id
     */
    public int getId() {

        return id;
    }

    /**
     * Effects: returns the capacity of this
     *
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Effects: returns the amount of passenger currently aboard this
     *
     * @return this.currentPassengersAboard
     */
    public int getCurrentPassengersAboard() {
        return currentPassengersAboard;
    }

    /**
     * Effects: returns the driver name of this
     *
     * @return this.driver_name
     *
     */
    public String getDriver_name() {
        return driver_name;
    }

    /**
     * Effects: returns the start location of this
     *
     * @return
     */
    public String getStartLocation() {
        return startLocation;
    }

    /**
     * Effects: returns the end location of this
     *
     * @return
     */
    public String getEndLocation() {
        return endLocation;
    }

    /**
     * Effects: returns the number of trains in the system of this
     *
     * @return
     */
    public static int getNumberOfTrainsInSystem() {
        return numberOfTrainsInSystem;
    }

    /**
     * Effects: Changes the current state of the train to s of this Modifies:
     * this
     *
     * @param s
     */
    public void setState(State s) {

        String trainBeforeChanges = this.toString();
        String t = TrainManagementSystem.getInstance().getTrainDriverByName(this.driver_name).toString();
        TrainDriver td = TrainManagementSystem.getInstance().getTrainDriverByName(this.driver_name);;
        if (s instanceof AtDestination) {
//reset user

            td.setCurrentTrainBeingDriven(null);

//reset train
            String temp = this.startLocation;
            this.startLocation = this.endLocation;
            this.endLocation = temp;
            this.driver_name = "null";
            this.currentPassengersAboard = 0;

            s = new AtPickUpLocation();

        }

        this.currentState = s;

        FileLogger fl = new FileLogger();
        fl.rewriteContentOfFile(FileLogger.trainsInSystemFileName, trainBeforeChanges, this.toString());
        fl.rewriteContentOfFile(FileLogger.usersFileName, t, td.toString());
        this.currentState.logEventToFile(this);
    }

    /**
     * Effects: returns the current rep of this
     *
     * @return
     */
    public State getState() {

        return this.currentState;
    }

}
