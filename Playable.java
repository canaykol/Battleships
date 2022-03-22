import java.io.Serializable;
public interface Playable extends Serializable
{
    String getPlayerName();
    Board getBoard();
    void setOpponent(Playable o);
    Playable getOpponent();
    
    /**
     * Accesses the its board through and uses
     * placeShip(Ship s, Position location, Position direction)
     * method in Board class to place a ship to its board.
     * This method can be used to strategize about ship placements.
     */
    
    void choosePlaces() throws IllegalPositionException;
    /**
     * Accesses the opponent board through getOpponent().getBoard()
     * and uses fire(Position p) method there to fire on a 
     * strategic location determined by this method.            
     */
    boolean makeMove() throws InvalidAnswerException;
}
