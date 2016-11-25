/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TrainManagementSystem;

import Train.Train;
import Users.Manager;
import Users.TrainDriver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rhamblin
 */
public class TrainManagementSystem {
    private FileLogger fileLogger;
    private ArrayList<Train> trainsInSystem;
    private ArrayList<Train> trainsCurrentlyOnRoad;
    private ArrayList<TrainDriver> train_drivers ;

    public TrainManagementSystem() {
        this.trainsCurrentlyOnRoad = new ArrayList<>();
        this.train_drivers = new ArrayList<>();
        this.trainsInSystem = new ArrayList<>();
        this.fileLogger = new FileLogger();
        
        fillTrains();
        fillUsers();
        
//        System.out.print(this.train_drivers.get(0).getName());
    }
    
    public void addTrainToSystem( Train t ){
        if(t!=null)
        this.trainsInSystem.add(t);
        else throw new NullPointerException();
    }
    
    public boolean removeTrainFromSystem( Train t ){
        if (t!=null) { 
            if(this.trainsInSystem.contains(t)) {
                this.trainsInSystem.remove(t);
                return true;
            }
        }
       
        else throw new NullPointerException();
        
        return false;
    }
    
    public void displayAllTrainsInSystem() {
        System.out.println(toString()) ; 
    }
    
    public boolean login (String name, String pass) {
        String content = name + " "+ pass;
        
        String role;
        
        if(this.fileLogger.isInFile(FileLogger.usersFileName, content))
            return true;
            
        
        else return false;
    }

    public ArrayList<Train> getTrainsInSystem() {
        return trainsInSystem;
    }

    public ArrayList<TrainDriver> getTrain_drivers() {
        return train_drivers;
    }

    
    public Train getTrain(int id) {
        Train result = null;
        for (Train t: trainsInSystem) {
            if(t.getId() == id)
                result = t;
        }
        
        return result;
    }
    
     public TrainDriver getTrainDriver(int id) {
        TrainDriver result = null;
        for (TrainDriver t: this.train_drivers) {
            if(t.getId() == id)
                result = t;
        }
        
        return result;
    }

    public FileLogger getFileLogger() {
        return fileLogger;
    }
    
    @Override
    public String toString() {
        String result = "";
        
        for(Train t: this.trainsInSystem)
            result += t.toString() + "\n";
        
        return result;
    }

    private void fillTrains() {
        FileReader fr  = null;
        String line, split[];
        try {
            File file  = new File(FileLogger.trainsInSystemFileName);
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            
            while( (line = br.readLine()) != null){
                if(line.equals("d")) continue;
                split = line.split(" ");
                //fix how it makes a train -- add in all instance variables
                //public Train( int id, int capacity, int currentPassengersAboard, String driver_name, String startLocation, String endLocation) {
                this.trainsInSystem.add(new Train(Integer.parseInt(split[0]), Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[1], split[4], split[5]));
           //    this.trainsInSystem.add(new Train(Integer.parseInt(split[2]), split[4], split[5] ));
                if(!split[6].equals("AtPickUpLoctation") | !split[6].equals("AtDestination")); 
                 this.trainsCurrentlyOnRoad.add(new Train(Integer.parseInt(split[0]), Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[1], split[4], split[5]));
              
                //this.trainsCurrentlyOnRoad.add(new Train(Integer.parseInt(split[2]), split[4], split[5] ));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TrainManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TrainManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(TrainManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }

    private void fillUsers() {
       
        FileReader fr  = null;
        String line, split[];
        try {
            File file  = new File(FileLogger.usersFileName);
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            
            while( (line = br.readLine()) != null){
              
                split = line.split(" ");
                if(line.contains("TrainDriver"))
                 this.train_drivers.add(new TrainDriver(this, split[1], split[2]));
            }
                    
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TrainManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TrainManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(TrainManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  }
    
    
}
