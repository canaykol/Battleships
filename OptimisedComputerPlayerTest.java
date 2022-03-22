

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class OptimisedComputerPlayerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class OptimisedComputerPlayerTest
{
    /**
     * Default constructor for test class OptimisedComputerPlayerTest
     */
    public OptimisedComputerPlayerTest()
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
    public void OptimisedComputerTest() throws InvalidAnswerException, IllegalPositionException{
        
        assertEquals(Simulator.performanceTest(1000) < 55 ,true);
    
    
    }
}
