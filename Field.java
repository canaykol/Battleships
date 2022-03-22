import java.io.Serializable;
public class Field implements Serializable
{
    // instance variables - replace the example below with your own
    private final Position location;
    private boolean hasShip = false;
    private boolean hasBeenFired = false;
        
    static final long serialVersionUID = -7588980448693010399L;
    /**
     * Constructs a Field from specified x and y coordinates.
     */
    public Field(int xCoor, int yCoor)
    { 
        location = new Position(xCoor, yCoor);
    }
    
    /**
     * Constructs a Field from a specified Position
     */
    public Field(Position p)
    { 
        location = p;
    }
    
    /**
     * Makes hasShip true.
     */
    public void addShip(){
        
        hasShip = true;
    }
    
    /**
     * Makes hasBeenFired true.
     * @returns true if there is also a ship placed in the field.
     */
    public boolean fire(){
        hasBeenFired = true;
        return hasShip && hasBeenFired;        
    }
    
    /**
     * @returns hasShip.
     */
    public boolean hasShip(){
        return hasShip;
    }
    
    /**
     * @returns hasBeenFired.
     */
    public boolean hasBeenFired(){
        return hasBeenFired;
    }
    /**
     * @returns the location of the Field as a Position object.
     */ 
    public Position getLocation(){
        return location;
    }
    
    /**
     * @returns a textual representation of the object in a string.
     */
    public String toString(){
        return location.toString()+" hasShip: "+hasShip+" hasBeenFired: "+hasBeenFired;
    }
}
