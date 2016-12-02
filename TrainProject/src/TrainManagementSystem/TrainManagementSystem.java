package TrainManagementSystem;

import Train.Train;
import Users.TrainDriver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrainManagementSystem {

    private static TrainManagementSystem tms;
    private FileLogger fileLogger;
    private ArrayList<Train> trainsInSystem;
    private ArrayList<TrainDriver> train_drivers;

    private TrainManagementSystem() {

        this.train_drivers = new ArrayList<>();
        this.trainsInSystem = new ArrayList<>();
        this.fileLogger = new FileLogger();

        fillTrains();
        fillUsers();

    }

    public static TrainManagementSystem getInstance() {
        if (tms == null) {
            return new TrainManagementSystem();
        } else {
            return tms;
        }
    }

    public void displayAllTrainsInSystem() {
        System.out.println(toString());
    }

    public boolean login(String name, String pass) {
        String content = name + " " + pass;

        return this.fileLogger.isInFile(FileLogger.usersFileName, content);
    }

    public ArrayList<Train> getTrainsInSystem() {
        return trainsInSystem;
    }

    public ArrayList<TrainDriver> getTrain_drivers() {
        return train_drivers;
    }

    public Train getTrain(int id) {
        Train result = null;

        for (Train t : trainsInSystem) {
            if (t.getId() == id) {
                result = t;
            }
        }

        return result;
    }

    public TrainDriver getTrainDriverByID(int id) {
        TrainDriver result = null;
        for (TrainDriver t : this.train_drivers) {
            if (t.getId() == id) {
                result = t;
            }
        }

        return result;
    }

    public TrainDriver getTrainDriverByName(String name) {
        TrainDriver result = null;
        for (TrainDriver t : this.train_drivers) {

            if (t.getName().equals(name)) {

                result = t;
            }
        }

        return result;
    }

    /**
     * @param choice Effects: This is a helper method that checks whether an id
     * matches an actual train. Returns true if it does, false if otherwise
     * @return
     */
    public boolean isValidChoiceForTrainId(int choice) {
        ArrayList<Train> trains = this.trainsInSystem;

        for (Train t : trains) {
            if (t.getId() == choice) {

                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String result = "";

        for (Train t : this.trainsInSystem) {
            result += t.toString() + "\n";
        }

        return result;
    }

    private void fillTrains() {
        FileReader fr = null;
        String line, split[];
        try {
            File file = new File(FileLogger.trainsInSystemFileName);
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                split = line.split(" ");

//API of constructor: public Train( int id, int capacity, int currentPassengersAboard, String driver_name, String startLocation, String endLocation) 
                this.trainsInSystem.add(new Train(Integer.parseInt(split[0]), Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[1], split[4], split[5], split[6]));

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

        FileReader fr = null;
        String line, split[];
        try {
            File file = new File(FileLogger.usersFileName);
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {

                split = line.split(" ");

                if (line.contains("TrainDriver")) {

                    if (split[3].equals("null")) {
                        this.train_drivers.add(new TrainDriver(this, null, split[1], split[2],
                                Integer.parseInt(split[0])));
                    } else {

                        this.train_drivers.add(new TrainDriver(this, this.getTrain(Integer.parseInt(split[3])), split[1], split[2],
                                Integer.parseInt(split[0])));
                    }

                }

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

}
