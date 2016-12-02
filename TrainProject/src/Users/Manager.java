package Users;

import Train.*;
import TrainManagementSystem.CommandLineInterface;
import TrainManagementSystem.FileLogger;
import TrainManagementSystem.TrainManagementSystem;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *OVERVIEW: This immutable abstraction represents a Manager who can manage the overall system.
 * AF(c) = a user, c, with a name and password of super.getName() and c.password. 
 * Rep Invariant : password != null
 */

public class Manager extends User {

    private TrainManagementSystem tms;

    /**
     * Effects:Creates a manager object
     * Modifies: this
     * @param tms
     * @param name
     * @param password 
     */
    public Manager(TrainManagementSystem tms, String name, String password) {
        super(name);
        if (tms == null | name.equals("") | password.equals("")) {
            throw new IllegalArgumentException();
        }
        this.tms = tms;
//        this.password = password;
    }

    /**
     * Effects: Displays a console based interface for the user to carry out manager duties
     */
    @Override
    public void CommandLineUserDisplay() {

        String sLoc, eLoc, name, pass;
        FileLogger fl = new FileLogger();
        int capacity;
        int choice, count = 0;
        boolean wrongInfo;
        Scanner scanner = new Scanner(System.in);

        System.out.println("\t\tWelcome manager!\nSelect an option below:");
        System.out.println("\tAdd a train to the system - 1 ");
        System.out.println("\tAdd a driver to the system - 2 ");
        System.out.println("\tRemove a train from the system - 3 ");
        System.out.println("\tRemove a driver from the system - 4 ");
        System.out.println("\tDisplay a list of all trains - 5");

        do {
            System.out.print("\nEnter choice: ");
            choice = scanner.nextInt();
        } while (choice < 1 | choice > 5);

        switch (choice) {
            case 1:
                do {
                    System.out.print("\nEnter Train's Start Location: ");
                    sLoc = scanner.next();
                    System.out.print("\nEnter Train's End Location: ");
                    eLoc = scanner.next();
                    System.out.print("\nEnter Train's Capacity: ");
                    capacity = scanner.nextInt();
                    System.out.println();

                    if (sLoc.equals(" ") || eLoc.equals(" ") || capacity <= 0) {
                        System.out.println("Incorrect information");
                    }
                } while (sLoc.equals(" ") || eLoc.equals(" ") || capacity <= 0);

                Train tr = new Train(capacity, sLoc, eLoc);

                if (!fl.isInFile(FileLogger.trainsInSystemFileName, tr.toString())) {
                    fl.writeToFile(FileLogger.trainsInSystemFileName, tr.toString());
                } else {
                    System.out.println("Train already exists");
                }

                break;

            case 2:

                do {
                    System.out.print("\nEnter Driver's Name(only one word allowed. either use first name or a username: ");
                    name = scanner.next();
                    System.out.print("\nEnter Driver's Password: ");
                    pass = scanner.next();

                    System.out.println();
                    wrongInfo = false;

                    if (name.equals(" ") || pass.equals(" ")) {
                        System.out.println("Incorrect information");
                        wrongInfo = true;
                    }
                    if (fl.isInFile(FileLogger.usersFileName, name + " " + pass)) {
                        System.out.println("User already exist");
                        wrongInfo = true;
                        return;
                    }
                } while (wrongInfo);

                //create driver
                TrainDriver td = new TrainDriver(tms, name, pass);
                
                if (!fl.isInFile(FileLogger.usersFileName, td.toString())) {
                    fl.writeToFile(FileLogger.usersFileName, "" + td.toString());

                }

                break;
            case 3:

                System.out.print("\nSelect your train: \n");

                for (Train t : tms.getTrainsInSystem()) {

                    System.out.println(t.simplifiedToString());
                    count++;
                }

                if (count == 0) {
                    System.out.println("No trains found");
                    return;
                }

                //validate choice
                do {
                    System.out.print("\nEnter the first number in the line select that train: ");
                    choice = scanner.nextInt();
                } while (!isValidChoice(choice));

                tr = tms.getTrain(choice);

                fl.removeEntireLineFromFile(FileLogger.trainsInSystemFileName, tr.toString());

                break;
            case 4:
                //deleting a driver
                TrainDriver traindriver = null;
                System.out.print("\nSelect your Driver: \n");

                for (TrainDriver t : tms.getTrain_drivers()) {

                    String[] line = t.toString().split(" ");
                    System.out.println(line[1] + "(To delete enter:\t"+line[0] + "\t)\n");
                    count++;
                }

                if (count == 0) {
                    System.out.println("No drivers found");
                    return;
                }

                //validate choice
                do {
                    System.out.print("\nEnter choice: ");
                    choice = scanner.nextInt();
                } while (!isValidChoiceForTrainDrivers(choice));

                traindriver = tms.getTrainDriverByID(choice);

                fl.removeEntireLineFromFile(FileLogger.usersFileName, traindriver.toString());

                break;
            case 5:
                System.out.println("\nFormat:\n{id} {current driver} {capacity of train} {Passengers Onboard}"
                        + " {Start Location} {End location} {Current Status of train}\n");
                tms.displayAllTrainsInSystem();
                break;

            default:
                System.out.println("Invalid Choice");
                this.CommandLineUserDisplay();
                break;
        }

        CommandLineInterface cli = new CommandLineInterface();
        System.out.println();
        cli.presentMenu();
        cli.getCurrentUser().CommandLineUserDisplay();

    }
  
/**
 * Effects: Sets a train driver, driver, as the driver of train
 * @param driver
 * @param train
 * @return 
 */
    public boolean addDriverToTrain(TrainDriver driver, Train train) {

        return train.boardDriver(driver);

    }
    
    /**
     * Effects: checks that the input is a valid train choice
     * 
     * @param choice
     * @return 
     */
     private boolean isValidChoice(int choice) {
        ArrayList<Train> trains = tms.getTrainsInSystem();

        for (Train t : trains) {
            if (t.getState() instanceof AtPickUpLocation && t.getId() == choice) {
                return true;
            }
        }
        return false;
    }

     /**
     * Effects: checks that the input is a valid train choice
     * 
     * @param choice
     * @return 
     */
    private boolean isValidChoiceForTrainDrivers(int choice) {
        ArrayList<TrainDriver> drivers = tms.getTrain_drivers();

        for (TrainDriver t : drivers) {
            if (t.getId() == choice) {
                return true;
            }
        }
        return false;
    }

}
