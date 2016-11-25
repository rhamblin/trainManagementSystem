/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

/**
 *
 * @author rhamblin
 */
public abstract  class User {
    /*
     * OVERVEIW:
     */
    private String name;

    
    public User(String name) {
        if(name.equals(" ")) 
            throw new NullPointerException();
        
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public abstract void CommandLineUserDisplay();
    
    
}
