
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.HashSet;

/**
 * The test class BoardTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class BoardTest
{
    private Player p1 = new RandomisedComputerPlayer();
    private Player p2 = new RandomisedComputerPlayer();
    private Board p1Board;
    private Board p2Board;
    /**
     * Default constructor for test class BoardTest
     */
    public BoardTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() throws IllegalPositionException
    {
        p1.setOpponent(p2);
        p2.setOpponent(p1);
        p1.choosePlaces();
        p2.choosePlaces();
        p1Board = p1.getBoard();
        p2Board = p2.getBoard();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void BoardClassTesting() throws IllegalPositionException
    {
        Board p1Board = p1.getBoard();
        Board p2Board = p2.getBoard();

        p1Board.fire(new Position(5,5));
        assertEquals(true, p1Board.isItFired(new Position(5,5)));
        
        HashMap<Character, Ship> callsigns = p1Board.getShipCallsigns();
        assertEquals(callsigns.get('S'),callsigns.get('S'));
        
        
        TreeMap<Position, Field> fields = p1Board.getFields();
        assertEquals(fields.get(new Position(5,5)).hasBeenFired(),true);
        assertEquals(p1Board.isInField(new Position(15,-6)),false);
        
        
        //further tests are at simulator test class.

    }
}

