package Users;

import Train.*;
import TrainManagementSystem.FileLogger;
import TrainManagementSystem.TrainManagementSystem;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
     *OVERVIEW: This mutable abstraction represents a Train Driver who can change the state of a Train.
     * AF(c) = a user, c, with a name and password of super.getName() and c.password. this user drives a train
       c.currentTrainBeingGiven and has an internal id of c.id.
     * Rep Invariant : password != null
     */
public class TrainDriver extends User {

    
    //instance variables
    private String password;
    private Train currentTrainBeingDriven;
    private TrainManagementSystem tms;
    private int id;
    public static int numberOfTrainDrivers = 0;

    /**
     * Effects: Creates a TrainDriver object 
     * Requires: None of the inputs be a reference to a null object
     * Modifies: this
     *
     * @param tms
     * @param name
     * @param password
     */
    public TrainDriver(TrainManagementSystem tms, String name, String password) {
        
        super(name);

        this.password = password;
        
        this.tms = tms;
        id = numberOfTrainDrivers;
        numberOfTrainDrivers++;
 
    }

    /**
     * Effects: Creates a TrainDriver object 
     * Requires: None of the inputs be a reference to a null object
     * Modifies: this
     * @param currentTrainBeingDriven
     * @param id 
     * Effects: Creates a TrainDriver object 
     * Requires: None of the inputs be a reference to a null object
     *
     * @param name
     * @param password
     */
    public TrainDriver(String password, Train currentTrainBeingDriven, int id, String name) {
        super(name);
        this.password = password;
        this.currentTrainBeingDriven = currentTrainBeingDriven;
        this.id = id;
        numberOfTrainDrivers++;
    }

    /**
     * Effects: Creates a TrainDriver object 
     * Requires: None of the inputs be a reference to a null object
     *  Modifies: this
     *
     * @param tms
     * @param name
     * @param password
     */
    public TrainDriver(TrainManagementSystem tms, Train currentTrainBeingDriven, String name, String password, int id) {
        super(name);
        this.password = password;
        this.currentTrainBeingDriven = currentTrainBeingDriven;
        this.tms = tms;
        this.id = id;
        numberOfTrainDrivers++;

    }

    /**
     * Effects: Returns the name of this object
     *
     * @return
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * Effects: Returns the id of this object
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Effects: Changes this.id to id Modifies: this
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Effects: changes the state of this Modifies: this
     */
    public void setCurrentTrainBeingDriven(Train currentTrainBeingDriven) {
        this.currentTrainBeingDriven = currentTrainBeingDriven;
    }

    /**
     * Effects: provides an interface for user
     */
    @Override
    public void CommandLineUserDisplay() {

        ArrayList<Train> trains = tms.getTrainsInSystem();

        if (trains == null) {
            return;
        }
        Scanner scanner = new Scanner(System.in);
        String name;
        String choice = null;
        int count = 0;

        try {
            if (this.currentTrainBeingDriven.getDriver_name().equals(this.getName())) {

                this.changeStateGUI();
                return;
            }
        } catch (Exception e) {
        }
        System.out.print("\nSelect your train: \n");

        for (Train t : trains) {

            displayInModifiedFormat(t);
            count++;

        }

        if (count == 0) {
            System.out.println("No trains found");
            return;
        }
        int c;
        //validate choice
        do {

            System.out.print("\nEnter \"exit\" to cancel.\nSelect your train to change state\nEnter choice: ");
            choice = scanner.nextLine();

            if (choice.equals("exit")) {
                return;
            }

            try {
                c = Integer.parseInt(choice);

            } catch (NumberFormatException e) {
                System.out.println("Invalid entry!");
                this.CommandLineUserDisplay();
                return;
            }

            if (!tms.isValidChoiceForTrainId(c)) {
                System.out.println("Invalid entry!");
                this.CommandLineUserDisplay();
                return;
            }

        } while (!tms.isValidChoiceForTrainId(c));

        String str = tms.getTrain(c).toString();

        //this would mean that the selected train has this driver as its driver
        if (tms.getTrain(c).getDriver_name().equals(this.getName())) {

            changeStateGUI();
        }
        String str2 = this.toString();
        if (!tms.getTrain(c).boardDriver(this)) {
            System.out.println("\nFailed to board train. Please Select another Train");
            this.CommandLineUserDisplay();
        } else {
            System.out.println("\nWelcome Aboard " + this.getName() + " !");
        
        }

    }

    /**
     * Effects: Displays to console a train in format: "From: " +
     * t.getStartLocation() + " To: " + t.getEndLocation() + " Seats Left: " +
     * (t.getCapacity() - t.getCurrentPassengersAboard()) Requires: t != null
     *
     *
     */
    private void displayInModifiedFormat(Train t) {

        if (t == null) {
            throw new NullPointerException();
        }

        System.out.println(
                "From:" + t.getStartLocation() + " \tTo:" + t.getEndLocation()
                + "\tCurrent Driver: " + t.getDriver_name()
                + "\t----> To choose this train enter: " + t.getId()
        );
    }

    /**
     * EFFECTS: This method changes the state of the train
     *
     * MODIFIES: this
     *
     *
     */
    public void changeStateOfTrain(State s) {

        this.currentTrainBeingDriven.setState(s);
    }

    /**
     * Effects: Returns a string rep representation of a train driver
     *
     * @return super.getName()
     */
    @Override
    public String toString() {
        String str;
        if (this.currentTrainBeingDriven == null) {
            str = "null";
        } else {
            str = Integer.toString(currentTrainBeingDriven.getId());
        }
        return id + " " + super.getName() + " " + password + " " + str + " TrainDriver";
    }

    /**
     * Effects: Provides an interface for the user to change the state of a
     * train
     */
    private void changeStateGUI() {
       
        State currentState = this.currentTrainBeingDriven.getState();
        State choosenState = null;
        int choice = 0;
        boolean repeat;

        do {
            repeat = false;
            System.out.println("\nChange state from " + this.currentTrainBeingDriven.getState().toString() + " to:"
                    + "\n\t1 - Enroute\n\t2 - Delayed\n\t3 - At Pick Up Location"
                    + "\n\t4 - At Destination ");
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println("\nEnter choice: ");
                choice = scanner.nextInt();

                if (choice < 1 | choice > 4) {
                    repeat = true;
                }
            } catch (InputMismatchException e) {

                repeat = true;

            }
        } while (repeat);

        choosenState = State.generateState(choice);

        if (currentState.toString().equals(choosenState.toString())) {
            System.out.println("You are already " + currentState.toString());
            this.CommandLineUserDisplay();
            return;
        } else {

            this.currentTrainBeingDriven.setState(choosenState);
        }

    }

     /**
     * Effects: Returns the password of this object
     *
     * @return this.password
     */
    public String getPassword() {
        return password;
    }
}
