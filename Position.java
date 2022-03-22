import java.util.HashSet;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;

import java.io.Serializable;
public class Position implements Comparable<Position>, Serializable
{
    // instance variables - replace the example below with your own
    private int xCoor;
    private int yCoor;
    static final long serialVersionUID = -7588980448693010399L;
    /**
     * Constructor for objects of class Position
     */
    public Position(int x, int y) //for initialising from int values
    {
        xCoor = x;
        yCoor = y;
    }

    /**
     * initialising a position equal to the specified Position p
     */
    public Position(Position p)
    {
        xCoor = p.getX();
        yCoor = p.getY();
    }

    /**
     * get x coordinate
     */
    public int getX() //
    {
        return xCoor;
    }

    /**
     * get y coordinate
     */
    public int getY() //
    {
        return yCoor;
    }

    /**
     * translates the position by given values
     */
    public void move(int dx, int dy) //
    {
        xCoor = xCoor + dx;
        yCoor = yCoor + dy;
    }

    /**
     * Set new location for the position, from specified int values.
     */

    public void setLocation(int x, int y) 
    {
        xCoor = x;
        yCoor = y;
    }

    /**
     * @returns the addition of this Position object to a specified Position object, just like a vector.
     */

    public Position addVector(Position p) 
    {
        return new Position(xCoor + p.getX(), yCoor + p.getY());
    }

    /**
     * 
     * @returns a HashSet of Position of the 8 positions that surround the Position object.
     */

    public HashSet<Position> getSurroundings()
    {
        HashSet<Position> surroundings = new HashSet<Position>();

        surroundings.add(new Position(xCoor-1, yCoor+1));
        surroundings.add(new Position(xCoor  , yCoor+1));
        surroundings.add(new Position(xCoor+1, yCoor+1));
        surroundings.add(new Position(xCoor+1, yCoor  ));
        surroundings.add(new Position(xCoor+1, yCoor-1));
        surroundings.add(new Position(xCoor  , yCoor-1));
        surroundings.add(new Position(xCoor-1, yCoor-1));
        surroundings.add(new Position(xCoor-1, yCoor  ));

        return surroundings;
    }

    /**
     *  @returns a HashSet of Position of the 4 Positions that lie at the right top, left top, left bottom and right bottom, of the object
     */
    public HashSet<Position> getDiagonals() //returns 
    {
        HashSet<Position> diagonals = new HashSet<Position>();

        diagonals.add(new Position(xCoor-1, yCoor+1));
        diagonals.add(new Position(xCoor+1, yCoor+1));
        diagonals.add(new Position(xCoor+1, yCoor-1));
        diagonals.add(new Position(xCoor-1, yCoor-1));

        return diagonals;
    }

    /**
     * @returns a HashSet of Position of the 4 Positions that lie at the right, left, bottom and top, of the object.
     */
    public HashSet<Position> getNeighbours(){
        HashSet<Position> cross = new HashSet<Position>();

        cross.add(new Position(xCoor-1, yCoor));
        cross.add(new Position(xCoor+1, yCoor));
        cross.add(new Position(xCoor, yCoor-1));
        cross.add(new Position(xCoor, yCoor+1));

        return cross;
    }

    @Override
    /**
     * Tests the equivalency of this object to another object.
     */
    public boolean equals(Object o){
        if (o == null){ return false;}
        if (o == this){return true;}
        if (!(o instanceof Position)){return false;}
        Position b = (Position) o;
        if(this.getX() == b.getX() && this.getY() == b.getY()){return true;}
        else{return false;}
    }

    @Override
    /**
     * @returns a unique integer that represents the identity of this Position object.
     */
    public int hashCode(){
        int hash = 1;
        hash = hash + xCoor* 3;
        hash = hash + yCoor * 100;
        return hash;
    }

    @Override
    /**
     * @returns a representation of the object in String.
     */
    public String toString(){
        return "x: "+xCoor+" y: "+yCoor;
    }

    public String getCoordinates(){
        String x = new Character(Character.toChars(xCoor+64)[0]).toString();
        String y = new Integer(yCoor).toString();
        return x+y;
    }

    @Override
    /**
     * Compares Position objects.
     * @returns positive if the object is greater than, zero if equal to or negative if smaller than specified object.
     */
    
    public int compareTo(Position p){
        return this.hashCode() - p.hashCode();
    }
    /**
     * @returns true if it is on one of the sides of the Board.
     */
    public boolean isOnSide(){
        return xCoor == 10 || xCoor == 1 || yCoor == 10 || yCoor == 1;
    }
    
    /**
     * @returns true if the positions is on one of the horizontal sides of the board.
     */
    public boolean isOnHorizontalSide(){
        boolean result = (yCoor == 1 || yCoor == 10);
        return result;
    }

    /**
     * @returns a HashSet of Position of the positions between the object and specified position.
     * It doesnt't include the the object but includes the specified object.
     */
    public HashSet<Position> positionsBetween(Position p){
        HashSet<Position> result = new HashSet<Position>();
        int direction;
        Position pd = new Position(1,1);
        if(this.getX() == p.getX()){
            direction = this.getY() - p.getY();
            direction = (direction < 0) ? 1 : -1;
            pd = new Position(0, direction);

        }
        else if(this.getY() == p.getY()){
            direction = this.getX() - p.getX();
            direction = (direction < 0) ? 1 : -1;
            pd = new Position(direction, 0);
        }
        else{
            return result;
        }
        Position iteration = this;
        while(!iteration.equals(p)){
            result.add(iteration);
            iteration = iteration.addVector(pd);
        }
        return result;
    }

}
