package TrainManagementSystem;

import Users.*;
import Train.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CommandLineInterface {

    //attributes
    private User currentUser;
    private TrainManagementSystem tms;

    //behaviours
    public CommandLineInterface() {

        currentUser = null;
        tms = TrainManagementSystem.getInstance();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void presentMenu() {
        ArrayList<Train> trains = tms.getTrainsInSystem();
        if (trains == null) {
            return;
        }
        Scanner scanner = new Scanner(System.in);
        String name, choice;
        int c;

        System.out.println("\t\tWelcome To The XYZ Service Station");
        System.out.println("Are you a:");
        System.out.println("\tTrain Driver - 1\n\tPassenger - 2\n\tManager - 3");
        try {
            do {
                System.out.print("\nEnter choice: ");
                choice = scanner.nextLine();

                if (!choice.equals("1") & !choice.equals("2") & !choice.equals("3")) {
                    c = 5;
                    System.out.println("Invalid choice");
                    this.presentMenu();
                    return;
                } else {
                    c = Integer.parseInt(choice);
                }

            } while (c < 1 | c > 3);
        } catch (InputMismatchException e) {
            
            System.out.println("Invalid choice");

            this.presentMenu();
            return;
        }
        if (c == 1 | c == 3) {
            proceedToLogin();
        } else {
            //THEY ARE A USER
            System.out.print("\nEnter your name to board train: ");
            name = scanner.next();

            this.currentUser = new Passenger(tms, name);
        }

    }

    private void proceedToLogin() {
        Scanner scanner = new Scanner(System.in);
        String name, pass, role;

        System.out.print("\nEnter your login name: ");
        name = scanner.next();
        System.out.print("\nEnter your password: ");
        pass = scanner.next();
        System.out.println();

        try {
            if (tms.login(name, pass)) {
                //this mean they are a user we need to get their role
                FileLogger fl = new FileLogger();
                role = fl.roleOf(FileLogger.usersFileName, name + " " + pass);
                  
                switch (role) {
                    case "Manager":
                        currentUser = new Manager(tms, name, pass);
                        break;
                    case "TrainDriver":
                        for(TrainDriver t : this.tms.getTrain_drivers())
                            if(t.getName().equals(name) & t.getPassword().equals(pass))
                        currentUser =  t;
                        break;
                }

            } else {
                System.out.println("No user exists with those credentials.");
                this.presentMenu();
                return;
            }
        } catch (NullPointerException e) {
            System.out.println("No user exists with those credentials.");
        }

    }

    //main
    public static void main(String args[]) {
        CommandLineInterface cli = new CommandLineInterface();
        String line = "2";
        Scanner scanner = new Scanner(System.in);

        while (!line.equals("1")) {

            System.out.println();
            cli.presentMenu();

            try {
                cli.getCurrentUser().CommandLineUserDisplay();
            } catch (Exception e) {
            }

            System.out.print("\nEnter 1 to exit application. Anything other entry will bring you to the main menu: ");
            line = scanner.nextLine();
        }
    }

}
