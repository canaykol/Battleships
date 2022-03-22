

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Scanner;

/**
 * The test class HumanPlayerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class HumanPlayerTest
{
    private Scanner user_input = new Scanner(System.in);
    HumanPlayer h = new HumanPlayer("Can");
    /**
     * Default constructor for test class HumanPlayerTest
     */
    public HumanPlayerTest()
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
    
    @Test
    public void HumanPlayerClassTest() throws IllegalPositionException, InvalidAnswerException{
        
        String pos = h.positionSelectionInput().toString();
        System.out.println("Please type 'true' if your input was "+ pos);
        boolean result = new Boolean(user_input.next());
        assertEquals(true, result);
        
        String ship = h.shipSelectionInput().toString();
        System.out.println("Please type 'true' if your selected ship was "+ ship);
        result = new Boolean(user_input.next());
        assertEquals(true, result);
         
        String direction = h.directionSelectionInput().toString();
        System.out.println("Please type 'true' if your selected direction was "+ ship);
        result = new Boolean(user_input.next());
        assertEquals(true, result);
        
        assertEquals(h.isInteger("267"),true);
        assertEquals(h.isInteger("5uıy5yu"),false);
        assertEquals(h.getPlayerName(),"Can");
        
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
}
