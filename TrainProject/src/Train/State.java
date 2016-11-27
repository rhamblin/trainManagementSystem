/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Train;

import TrainManagementSystem.FileLogger;

/**
 *
 * @author rhamblin
 */
public abstract class State {
    /*
     * OVERVIEW: This class is an abstraction of a State of a train
     */
    
    /*
     * EFFECTS: This method writes a line to the file responsible for keeping a record
     *          of the state changes.
     */
    public State(){

    }
    
    public void logEventToFile(Train t){
        FileLogger fl = new FileLogger();
        String str = t.toString() + " " + this.toString();
        if(!fl.isInFile(FileLogger.trainEventsLogFileName, str))
       fl.writeToFile(FileLogger.trainEventsLogFileName, str);
    }
    
    public abstract String toString();
    
}
