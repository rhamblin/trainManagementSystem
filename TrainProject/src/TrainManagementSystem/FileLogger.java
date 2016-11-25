/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TrainManagementSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rhamblin
 */
public class FileLogger {
    /*
     * OVERVIEW: This abstraction is responsible for writing and reading from 
     * a file
     */
    
    static final public String trainEventsLogFileName = "src/TrainEventLog.txt";
    static final public String usersFileName = "src/ListOfUsers.txt";
    static final public String trainsInSystemFileName = "src/trainsInSystem.txt";
       
    //makes file objects with paths and put in stance variables 
    public FileLogger() {
        this.createAllFiles();
       
           
       
        
    }
    
    private void createAllFiles(){
         File file =  new File(FileLogger.trainEventsLogFileName);
         File file2 =  new File(FileLogger.trainsInSystemFileName);
         File file3 =  new File(FileLogger.usersFileName);
       
        
        //if file doesnt exist then create it
        if(!file.exists()) {
            
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(!file2.exists()) {
            
            try {
                file2.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(!file3.exists()) {
            
            try {
                file3.createNewFile();
                this.writeToFile(usersFileName, "admin admin Manager");
            } catch (IOException ex) {
                Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //make this method more general
    public boolean isInFile(String fileName, String content) {
        /*
        *
        */
        
      
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            //open file 
            String currentLine;
            
            //while there are lines in the file
            while((currentLine = br.readLine()) != null)
                 if ( currentLine.contains(content))         return true;
         
            
         
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public String roleOf(String fileName, String content) {
        String[] info;
        String[] credentials = content.split(" ");
        try (BufferedReader br = new BufferedReader(new FileReader (fileName))){
           
            String currentLine;
            while((currentLine = br.readLine()) != null ){
           
                info = currentLine.split(" ");
                if(credentials[0].equals(info[0]) && credentials[1].equals(info[1]))
                    return info[2];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public void writeToFile (String filename, String s)  {
        File file =  new File(filename);
       
        try{
           
            FileWriter fileW = new FileWriter(file.getAbsoluteFile(),true);                        
            BufferedWriter buffw = new BufferedWriter(fileW);            
            buffw.write(s+"\n");
            buffw.close();
            
        }catch (FileNotFoundException ex){
            
        }
        catch(Exception e){

        }
        
    }
  
    public boolean rewriteContentOfFile(String filename, String originalContent, String newContent){
        
      String oldFileName = filename;
     
      BufferedReader br = null;
      BufferedWriter bw = null;
      
       ArrayList<String> lines = new ArrayList<>();
      try {
          File  f1 = new File(oldFileName);
          br = new BufferedReader(new FileReader(f1));
 
         String line;
         while ((line = br.readLine()) != null) {
            if (line.contains(originalContent))
               line = line.replace(originalContent, newContent);
            if(line.isEmpty()) continue;
            lines.add(line);
            
         }
         br.close();
         
         FileWriter fw = new FileWriter(f1);
          try (BufferedWriter out = new BufferedWriter(fw)) {
              for(String s : lines)
                  out.write(s+"\n");
              out.flush();
              out.close();
          }
         
      } catch (Exception e) {
         return false;
      } finally {
         try {
            if(br != null)
               br.close();
         } catch (IOException e) {
            //
         }
         try {
        
            if(bw != null)
               bw.close();
         } catch (IOException e) {
            //
         }
         
    }
    return true;
}
    
   public boolean removeEntireLineFromFile (String filename, String content){
        this.rewriteContentOfFile(filename, content, "");
    
        
        
        return true;
    }
}