/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

/**
 *
 * @author rhamblin
 */
import Train.*;
import TrainManagementSystem.CommandLineInterface;
import TrainManagementSystem.FileLogger;
import TrainManagementSystem.TrainManagementSystem;
import java.util.ArrayList;
import java.util.Scanner;
public class Manager extends User{
    private TrainManagementSystem tms;
    
    public Manager(TrainManagementSystem tms, String name, String password) {
        super(name);
        this.tms = tms;
//        this.password = password;
    }

    @Override
    public void CommandLineUserDisplay() {
         /*EFEECTS:     
        *DISPLAY A COOMAND LINE INTERFACE WHICH WILL PRESENT USER 
        * WITH OPTIONS TO BOARD A TRAIN OF TO CHANGE STATE
        */
        String sLoc, eLoc, name, pass;
        FileLogger fl = new FileLogger();
        int capacity;
        int choice, count = 0;
        boolean wrongInfo;
        Scanner scanner = new Scanner (System.in);
        
         System.out.println("\t\tWelcome manager!\nSelect an option below:");
         System.out.println("\tAdd a train to the system - 1 ");
         System.out.println("\tAdd a driver to the system - 2 ");
         System.out.println("\tRemove a train from the system - 3 ");
         System.out.println("\tRemove a driver from the system - 4 ");
         System.out.println("\tDisplay a list of all trains - 5");
         System.out.println("\tAdd a driver to a train - 6");
         
         do{
            System.out.print("\nEnter choice: ");
            choice  = scanner.nextInt();
          } while(choice<1 | choice>6);
          
         switch(choice){
             case 1: 
                  do{
                  System.out.print("\nEnter Train's Start Location: ");
                  sLoc = scanner.next();        
                  System.out.print("\nEnter Train's End Location: ");
                  eLoc = scanner.next();
                  System.out.print("\nEnter Train's Capacity: ");
                  capacity = scanner.nextInt();
                  System.out.println(); 
                  
                  if(sLoc.equals(" ") || eLoc.equals(" ") || capacity<=0){
                      System.out.println("Incorrect information");
                  }
                  } while(sLoc.equals(" ") || eLoc.equals(" ") || capacity<=0);
                  
                  
                  Train tr = new Train(capacity, sLoc, eLoc);
                  
                  if(!fl.isInFile(FileLogger.trainsInSystemFileName, tr.toString())){
                      fl.writeToFile(FileLogger.trainsInSystemFileName, tr.toString());
                  }
                  else System.out.println("Train already exists");
                  
                 break;
                 
             case 2: 
                 do{
                  System.out.print("\nEnter Driver's Name: ");
                  name = scanner.next();        
                  System.out.print("\nEnter Driver's Password: ");
                  pass = scanner.next();
                  
                  System.out.println(); 
                  wrongInfo = false;
                  if(name.equals(" ") || pass.equals(" ") ){
                      System.out.println("Incorrect information");
                      wrongInfo = true;
                  }
                  if(fl.isInFile(FileLogger.usersFileName, name + " " + pass)) {
                        System.out.println("User already exist");
                      wrongInfo = true;
                  }
                  } while(wrongInfo);
                  
                 //create driver
                 TrainDriver td = new TrainDriver(tms, name, pass);
                //  String driverString = name+" "+pass+" TrainDriver";
                  if(!fl.isInFile(FileLogger.usersFileName, td.toString())){
                      fl.writeToFile(FileLogger.usersFileName, td.toString());
                  }
                  else { System.out.println("Driver already exists"); TrainDriver.numberOfTrainDrivers--;}
                 break;
             case 3:

                 System.out.print("\nSelect your train: \n");

                 for (Train t : tms.getTrainsInSystem()) {
                    //   String[] split = t.simplifiedToString().split(" ");
                       
                     System.out.println(t.simplifiedToString());
                     count++;
                 }

                 if (count == 0) {
                     System.out.println("No trains found");
                     return;
                 }

                 //validate choice
                 do {
                     System.out.print("\nEnter choice: ");
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

                     System.out.println(t.toString() + "\n");
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

                 traindriver = tms.getTrainDriver(choice);

                 fl.removeEntireLineFromFile(FileLogger.usersFileName, traindriver.toString());

                 break;
             case 5: 
                 tms.displayAllTrainsInSystem();
                 break;
             case 6: 
                 TrainDriver driver = selectDriverFromGUI();
                 Train train = selectTrainFromGUI();
                 
                 train.boardDriver(driver);
                 
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
    
    public void addTrainToSystem(Train t) {
        tms.addTrainToSystem(t);
        
    }
    
    public boolean removeTrainFromSystem(Train t) {
      return tms.removeTrainFromSystem(t);
    }

    public void displayListOfTrainsToConsole() {
        tms.displayAllTrainsInSystem();
        
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
     
      private boolean isValidChoiceForTrainDrivers(int choice) {
        ArrayList<TrainDriver> drivers = tms.getTrain_drivers();
        
        for(TrainDriver t: drivers) {
                if( t.getId() == choice) {
                    return true;
                }
            }
        return false;
    }
      
    //MUST COMPLETE
    public boolean addNewTrainDriverToSystem (TrainDriver driver){
        return false;
    }
    
    public boolean addDriverToTrain(TrainDriver driver, Train train) {
        
        return train.boardDriver(driver);
        
    }

    private TrainDriver selectDriverFromGUI() {
        
          int count = 0, choice;
        Scanner scanner = new Scanner ( System.in);
        
       System.out.print("\nSelect your Driver: \n");

                 for (TrainDriver t : tms.getTrain_drivers()) {

                     System.out.println(t.toString() + "\n");
                     count++;
                 }

                 if (count == 0) {
                     System.out.println("No drivers found");
                     return null;
                 }

                 //validate choice
                 do {
                     System.out.print("\nEnter choice: ");
                     choice = scanner.nextInt();
                 } while (!isValidChoiceForTrainDrivers(choice));

                 return tms.getTrainDriver(choice);
    }

    private Train selectTrainFromGUI() {
        int count = 0, choice;
        Scanner scanner = new Scanner ( System.in);
      System.out.print("\nSelect your train: \n");

                 for (Train t : tms.getTrainsInSystem()) {

                     System.out.println(t.simplifiedToString());
                     count++;
                 }

                 if (count == 0) {
                     System.out.println("No trains found");
                     return null;
                 }

                 //validate choice
                 do {
                     System.out.print("\nEnter choice: ");
                     choice = scanner.nextInt();
                 } while (!isValidChoice(choice));

                 return tms.getTrain(choice);
    }
}
