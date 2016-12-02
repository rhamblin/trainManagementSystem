package Train;

import TrainManagementSystem.FileLogger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class State {
    /*
     * OVERVIEW: This class is an abstraction of a State of a train
     */

    /*
     * EFFECTS: This method writes a line to the file responsible for keeping a record
     *          of the state changes.
     */
    public State() {

    }

    public static State generateState(int choice) {
        switch (choice) {
            case 1:
                return new Enroute();
            case 2:
                return new Delayed();
            case 3:
                return new AtPickUpLocation();
            case 4:
                return new AtDestination();
            default:
                return null;
        }
    }
        /**
         *
         * @param choice
         * @return
         */
    public static State generateState(String choice) {
        switch (choice) {
            case "Enroute":
                return new Enroute();
            case "Delayed":
                return new Delayed();
            case "AtPickUpLocation":
                return new AtPickUpLocation();
            case "AtDestination":
                return new AtDestination();
            default:
                return null;
        }

    }

    public void logEventToFile(Train t) {
        FileLogger fl = new FileLogger();
        String str = t.toString();
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        //  if(!fl.isInFile(FileLogger.trainEventsLogFileName, str))
        fl.writeToFile(FileLogger.trainEventsLogFileName, str + sdf.format(new Date()));

      //  if(!fl.isInFile(FileLogger.trainsInSystemFileName, str))
        //  fl.writeToFile(FileLogger.trainEventsLogFileName, str);
    }

    public abstract String toString();

}
