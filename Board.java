import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.ArrayList;

import java.io.Serializable;
public class Board implements Serializable
{
    private final Playable owner;
    private final TreeMap<Position, Field> fields = new TreeMap<Position, Field>();
    private final int boardSize = 10;
    private final HashSet<Ship> ships = new HashSet<Ship>();
    private final HashMap<Character, Ship> shipCallsigns = new HashMap<Character, Ship>();
    static final long serialVersionUID = -7588980448693010399L;
    /**
     * Constructs a board specified a player.
     */
    public Board(Playable owner)
    {
        this.owner = owner;

        for(int a=1; a<11; a++){
            for(int b=1; b<11; b++){
                Position tempPoint = new Position(a,b);
                fields.put(tempPoint, new Field(tempPoint));
            }
        }

        ships.add(new Ship(5, "Aircraft carrier"));
        ships.add(new Ship(4, "Battleship"));
        ships.add(new Ship(3, "Destroyer"));
        ships.add(new Ship(3, "Submarine"));
        ships.add(new Ship(2, "Patrol boat"));

        for(Ship s: ships){
            shipCallsigns.put(s.getShipCallsign(), s);
        }
    }   

    /**
     * @returns the ships on the board in a HashSet<Ship>
     */
    public HashSet<Ship> getShips(){
        return ships;
    }

    /**
     * @returns a TreeMap<Position, Field> which relates the Field objects of the board to their positions.
     */
    public TreeMap<Position, Field> getFields(){
        return fields;
    }

    public HashMap<Character, Ship> getShipCallsigns(){
        return shipCallsigns;
    }
    
    public boolean fire(Position a)throws IllegalPositionException{
        if(isItFired(a)){
            throw new IllegalPositionException("You have already fired at this location.");
        }
        return fields.get(a).fire();
    }

    public boolean isItFired(Position a){
        return fields.get(a).hasBeenFired();
    }

    /**
     * @returns true if the specified Position is on the Board.
     */
    public boolean isInField(Position p){
        return fields.containsKey(p);
    }
    
    /**
     * @returns a HashSet<Position> in which all the unsuccesful attempts were made to.
     */
    public HashSet<Position> getFailedAttempts(){
        HashSet<Position> failedAttempts = new HashSet<Position>();
        for(Field f: this.getFields().values()){
            if(f.hasBeenFired() && !f.hasShip()){
                failedAttempts.add(f.getLocation());
            }
        }
        return failedAttempts;
    }

    /**
     * @returns an ArrayList<Position> of the Positions which don't have a ship yet.
     */
    public ArrayList<Position> getEmptyPositions(){
        ArrayList<Position> emptySpots = new ArrayList<Position>();
        for(Field f: fields.values()){
            if(!f.hasShip()){
                emptySpots.add(new Position(f.getLocation()));
            }
        }
        return emptySpots;
    }

    public HashSet<Position> getUnfiredPositions(){
        HashSet<Position> unfiredPositions = new HashSet<Position>();
        for(Position p: fields.keySet()){
            if(!isItFired(p)){
                unfiredPositions.add(new Position(p));
            }
        }
        return unfiredPositions;
    }

    /**
     * @returns a HashSet<Position> containing the positions of successful attemps.
     */
    public HashSet<Position> getShotPlaces(){
        HashSet<Position> shotPlaces = new HashSet<Position>();
        for(Field f: this.getFields().values()){
            if(f.hasShip() && f.hasBeenFired()){
                shotPlaces.add(f.getLocation());
            }
        }
        //System.out.println(shotPlaces);
        return shotPlaces;
    }

    /**
     * @returns a HashSet<Ship> containing the ships that aren't placed.
     */
    public HashSet<Ship> getUnplacedShips(){
        HashSet<Ship> unplacedShips = new HashSet<Ship>();
        for(Ship s: ships){
            if(!s.hasBeenPlaced()){
                unplacedShips.add(s);
            }
        }
        return unplacedShips;
    }

    /**
     * @returns a HashSet<Ship> containing sunk ships.
     */
    public HashSet<Ship> getSunkShips(){
        HashSet<Ship> sunkShips = new HashSet<Ship>();
        for(Ship s: ships){
            if(s.hasBeenSunk()){
                sunkShips.add(s);
            }
        }
        return sunkShips;
    }

    /**
     * @returns a HashSet<Ship> which are not sunk yet. However the ships in the set do not point to the actual ships on the board.
     */
    public HashSet<Ship> getRemainingShips(){
        HashSet<Ship> remainingShips = new HashSet<Ship>();
        for(Ship s: ships){
            if(!s.hasBeenSunk()){
                remainingShips.add(new Ship(s.getSize()));
            }
        }
        return remainingShips;
    }


    /**
     * @returns true if the given combination of Ship, location and direction are valid for
     */
    public boolean isPlaceable(Ship s, Position location, Position direction){
        HashMap<Position, Field> proposedDeck = new HashMap<Position, Field>();
        HashSet<Position> surroundings = new HashSet<Position>();
        Position currentPosition = location;
        for(int a=0; a < s.getSize(); a++){
            proposedDeck.put(currentPosition, fields.get(currentPosition));
            currentPosition = currentPosition.addVector(direction);
        }
        for(Field f: proposedDeck.values()){
            if(f == null){
                return false;//throw new IllegalPlacementException("Your placement is out of the board.");
            }
            if(f.hasShip()){
                return false;//throw new IllegalPlacementException("This spot is already occupied by another ship.");
            }
        }

        for(Position p: proposedDeck.keySet()){
            surroundings.addAll(p.getSurroundings());
        }
        for(Position p: proposedDeck.keySet()){
            surroundings.remove(p);
        }
        HashSet<Position> outOfTable = new HashSet<Position>();
        for(Position p: surroundings){
            if(!fields.keySet().contains(p)){
                outOfTable.add(p);
            }
        }
        for(Position p: outOfTable){
            surroundings.remove(p);
        }
        for(Position p: surroundings){
            if(fields.get(p).hasShip()){
                return false;//throw new IllegalPlacementException("This spot is too close to another ship...");
            }
        }

        return true;
    }

    public void placeShip(Ship s, Position location, Position direction) throws IllegalPositionException
    {
        //error catching ve tekrar yönlendirme yapılabilir.

        HashMap<Position, Field> proposedDeck = new HashMap<Position, Field>();
        HashSet<Position> surroundings = new HashSet<Position>();
        Position currentPosition = location;

        for(int a=0; a < s.getSize(); a++){
            proposedDeck.put(currentPosition, fields.get(currentPosition));
            currentPosition = currentPosition.addVector(direction);
        }
        for(Field f: proposedDeck.values()){
            if(f == null){
                throw new IllegalPositionException("Your placement is out of the board.");
            }
            if(f.hasShip()){
                throw new IllegalPositionException("This spot is already occupied by another ship.");
            }
        }

        for(Position p: proposedDeck.keySet()){
            surroundings.addAll(p.getSurroundings());
        }
        for(Position p: proposedDeck.keySet()){
            surroundings.remove(p);
        }
        HashSet<Position> outOfTable = new HashSet<Position>();
        for(Position p: surroundings){
            if(!fields.keySet().contains(p)){
                outOfTable.add(p);
            }
        }
        for(Position p: outOfTable){
            surroundings.remove(p);
        }
        for(Position p: surroundings){
            if(fields.get(p).hasShip()){
                throw new IllegalPositionException("This spot is too close to another ship...");
            }
        }

        for(Field f: proposedDeck.values())
        {
            f.addShip();
        }
        s.setDeck(proposedDeck);
        s.isNowPlaced();
    }

    public String drawDesignationBoard(){ //maybe location scale can be added.
        String theBoard = " ";
        for(int i = 1; i<11; i++)
        {
            theBoard = theBoard +"| "+String.valueOf((char)(i + 'A' - 1))+" ";
        }
        theBoard = theBoard +"\n";
        for(Field f: this.getFields().tailMap(new Position(1,1)).values()){
            if(f.getLocation().getX() == 1){
                theBoard = theBoard + f.getLocation().getY();
            }

            if(f.hasShip()){
                theBoard = theBoard +"| S ";
            }
            else
            {
                theBoard = theBoard +"|   ";
            }

            if(f.getLocation().getX() == 10){
                theBoard = theBoard +"|\n__________________________________________\n";
            }
        }
        System.out.println(theBoard);
        return theBoard;
    }

    public String drawAttackBoard(){
        String theBoard = " ";
        for(int i = 1; i<11; i++)
        {
            theBoard = theBoard +"| "+String.valueOf((char)(i + 'A' - 1))+" ";
        }
        theBoard = theBoard +"\n";
        for(Field f: this.getFields().tailMap(new Position(1,1)).values()){
            if(f.getLocation().getX() == 1){
                theBoard = theBoard + f.getLocation().getY();
            }

            if(f.hasBeenFired() && f.hasShip()){
                theBoard = theBoard +"| H ";
            }                        
            else if(f.hasBeenFired())
            {
                theBoard = theBoard +"| X ";
            }
            else
            {
                theBoard = theBoard +"|   ";
            }

            if(f.getLocation().getX() == 10){
                theBoard = theBoard +"|\n__________________________________________\n";
            }
        }
        System.out.println(theBoard);
        return theBoard;
    }

    public String drawFinalBoard(){
        String theBoard = " ";
        for(int i = 1; i<11; i++)
        {
            theBoard = theBoard +"| "+String.valueOf((char)(i + 'A' - 1))+" ";
        }
        theBoard = theBoard +"\n";
        for(Field f: this.getFields().tailMap(new Position(1,1)).values()){
            if(f.getLocation().getX() == 1){
                theBoard = theBoard + f.getLocation().getY();
            }

            if(f.hasBeenFired() && f.hasShip()){
                theBoard = theBoard +"| H ";
            }                        
            else if(f.hasBeenFired())
            {
                theBoard = theBoard +"| X ";
            }
            else if(f.hasShip())
            {
                theBoard = theBoard +"| S ";
            }
            else
            {
                theBoard = theBoard +"|   ";
            }

            if(f.getLocation().getX() == 10){
                theBoard = theBoard +"|\n__________________________________________\n";
            }
        }
        System.out.println(theBoard);
        return theBoard;
    }
}
