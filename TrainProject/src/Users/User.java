package Users;

    /**
     * OVERVEIW: This  immutable abstraction represents a user of the system
     * AF(c) = a user, u whose name is u.name
     * RepInvariant: u.name!=null
     */

public abstract  class User {

    //attributes
    private String name;

    //constructor
    public User(String name) {
            
        this.name = name;
        
        if(!repOK()) throw new IllegalArgumentException();
    }

    //behaviours
    /**
     * 
     * @return this.name
     */
    public String getName() {
        return name;
    }

    /**
     * EFFECTS: returns the users name
     * @return  this.name
     */
    @Override
    public String toString() {
        return "User{" + "name=" + name + '}';
    }
    
    
    /**
     * Effect: returns true if there is no name 
     * @return  true if rep invariant holds false otherwise
     */
    public boolean repOK(){
        if (this.name == null) {
            
        } else {
            return true;
        }
        return false;
    }
    
    /**
     * Effects: Present a menu to the user via console to allow them to carry out the functions of that class based
     *                  on the use cases.
     */
    public abstract void CommandLineUserDisplay();
}
