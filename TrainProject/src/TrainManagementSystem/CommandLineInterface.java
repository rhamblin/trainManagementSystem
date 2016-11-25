    package TrainManagementSystem;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rhamblin
 */
import Users.*;
import Train.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandLineInterface {
    
    private User currentUser;
    private TrainManagementSystem tms;

    public CommandLineInterface() {
        currentUser = null;
        tms = new TrainManagementSystem();
    }

    public User getCurrentUser() {
        return currentUser;
    }
        
    public void presentMenu(){
        ArrayList<Train> trains = tms.getTrainsInSystem();
        if(trains == null) return;
        Scanner scanner = new Scanner(System.in);
        String name;
        int choice;
        
         System.out.println("\t\tWelcome To The XYZ Service Station");
         System.out.println("Are you a:");
         System.out.println("\tTrain Driver - 1\n\tPassenger - 2\n\tManager - 3");
         
         do{
            System.out.print("\nEnter choice: ");
            choice  = scanner.nextInt(); 
          } while(choice<1 | choice>3);
         
         if(choice == 1 | choice == 3) proceedToLogin();
        
        else {
             //THEY ARE A USER
             System.out.print("\nEnter your name to board train: ");
             name = scanner.next();
            
             this.currentUser = new Passenger(tms,name);
        }
        
    }
    
    public static void main (String[] args){
        CommandLineInterface cli = new CommandLineInterface();
        System.out.println();
        cli.presentMenu();
        cli.getCurrentUser().CommandLineUserDisplay();
                
    }

    private void proceedToLogin() {
        Scanner scanner = new Scanner(System.in);
        String name, pass,role;
        
        System.out.print("\nEnter your login name: ");
        name = scanner.next();        
        System.out.print("\nEnter your password: ");
        pass = scanner.next();
        System.out.println();
       // System.out.println(role);
        try{
        if( tms.login(name, pass) ) {
            //this mean they are a user we need to get their role
            role = tms.getFileLogger().roleOf(FileLogger.usersFileName, name + " " + pass);
            
            switch(role){
                case "Manager" : 
                   currentUser = new Manager(tms,name, pass); 
                   break;
                case "TrainDriver" : 
                    currentUser = new TrainDriver(tms,name,pass);
                    break;
            }
                   
        }
             else System.out.println("No user exists with those credentials.");
        }catch(NullPointerException e) {
             System.out.println("No user exists with those credentials.");
        }
        
    }
    
    
}
