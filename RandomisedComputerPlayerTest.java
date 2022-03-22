
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class RandomisedComputerPlayerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class RandomisedComputerPlayerTest
{
    RandomisedComputerPlayer randy = new RandomisedComputerPlayer();
    /**
     * Default constructor for test class RandomisedComputerPlayerTest
     */
    public RandomisedComputerPlayerTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
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
    public void RandomisedComputerPlayerTest(){
        
        
        
        assertEquals(randy.getBoard().isInField(randy.positionSelection()),true);
        
        Position d1 = new Position(-1, 0);
        Position d2 = new Position(1, 0);
        Position d3 = new Position(0, -1);
        Position d4 = new Position(0, 1);
            
        Position d = randy.directionSelection();
        assertEquals(d.equals(d1)||d.equals(d2)||d.equals(d3)||d.equals(d4),true);

        
    }
    
    
}
