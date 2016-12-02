/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Train.Delayed;
import Train.State;
import Train.Train;
import TrainManagementSystem.FileLogger;
import TrainManagementSystem.TrainManagementSystem;
import Users.Manager;
import Users.TrainDriver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 *
 * @author Riko
 */
public class Test extends TestCase {
    
    public Test(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void test1() {
        FileReader fr = null;
         String filename = "src/test.txt", expected = null ;
         boolean result ;
         
         BufferedReader br  = null;
      try {
            br = new BufferedReader(new FileReader(new File( filename)));
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        return;
        }
      
       
        try {
            expected  = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
               
            FileLogger fl = new FileLogger();
            result = fl.isInFile(filename, expected);
          
           
        assertEquals(true,result);
    }
   
    
  public void test2() {
        FileReader fr = null;
        FileLogger fl = new FileLogger();
         String filename = "src/test.txt", expected = null , result = null;
        
         
         BufferedReader br  = null;
      try {
            br = new BufferedReader(new FileReader(new File( filename)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
      String original = null;
       try {
           original = br.readLine();
           expected = original + "d";
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
           
        }
     
      
         try {
           
            fr = new FileReader(new File(filename));
            
            fl.rewriteContentOfFile(filename, original, expected);
            
            //BufferedReader br = new BufferedReader(fr);
            
          //  result =;//br.readLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        assertTrue( fl.isInFile(filename, expected) );
    //    assertEquals(expected,result);
    }
  
  public void test3(){
      
      String filename = "src/test.txt";
      FileLogger fl = new FileLogger();
      BufferedReader br  = null;
      try {
            br = new BufferedReader(new FileReader(new File( filename)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
      
      
      String toBeRemoved = null;
        try {
            toBeRemoved = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fl.removeEntireLineFromFile(filename, toBeRemoved);
        assertFalse( fl.isInFile(filename, toBeRemoved));
  }
   
  public void test4(){
      boolean expected = false;
      try{
      Manager m  = new Manager(null, "joe", "gg");
      }catch(IllegalArgumentException e) {
          expected = true;
      }
      
   assertTrue(expected);    
      
  }
  
   public void test5(){
      boolean expected = false;
      String str = "f";
      try{
          
      Manager m  = new Manager(
              TrainManagementSystem.getInstance(), str,str);
      }catch(IllegalArgumentException e) {
          expected = true;
      }
      
   assertFalse(expected);    
      
  }
   
   public void test6(){
       Train t = new Train(1, "Toronto", "Ottawa");
       
       t.boardPassenger();
       
       assertFalse(t.boardPassenger());
       
   }
   
   public void test7(){
      assertTrue(State.generateState("Delayed") instanceof Delayed);
 }
   
   
   public void test8(){
       Train t = new Train(1, "Toronto", "Ottawa");
       State s = t.getState();
       try{
       t.setState(new Delayed());}
       catch(Exception e){}
       assertEquals(s.toString(),t.getState().toString());
   }
   
   public void test9( ){
   Train t = new Train(1, "Toronto", "Ottawa");
       assertEquals(t.getCapacity(),1);
   }
   public void test10(){
   Train t = new Train(1, "Toronto", "Ottawa");
       assertEquals(t.getCurrentPassengersAboard(),0);}
}
