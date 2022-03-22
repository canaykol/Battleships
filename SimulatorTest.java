
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Scanner;

/**
 * The test class SimulatorTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SimulatorTest
{
    private Scanner user_input = new Scanner(System.in);
    /**
     * Default constructor for test class SimulatorTest
     */
    public SimulatorTest()
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
    @Test
    public void SimulatorTest()
    {
        Simulator s = new Simulator();

        s.initialisePlayers();
        for(Playable p: s.getPlayers()){
            p.toString();
        }
        System.out.println("Please type 'true' if your two players were initialised properly.");
        System.out.println("Check if both players have know their and their player's names, and have an empty board.");
        boolean result = new Boolean(user_input.next());
        assertEquals(true, result);

        s.shipDesignation();
        for(Playable p: s.getPlayers()){
            p.toString();
        }
        System.out.println("Please type 'true' if both players designated their ships properly.");
        System.out.println("In normal game the opponent boards aren't disclosed. This is for test purposes only.");
        result = new Boolean(user_input.next());
        assertEquals(true, result);
        System.out.println("Now play a trial game with the computer, you'll be asked for any inconvenience experienced.");
        s.play();
        System.out.println("Please type 'true' if your game was in expected standards");
        System.out.println("Type 'false' if the game confused the turns or wasn't able to tell the winner.");
        result = new Boolean(user_input.next());
        assertEquals(true, result);

    }
    
    @After
    public void tearDown(){}
    
    
    @Test
    /**
     * Please conduct this test with the load file provided with this game.
     */
    public void SimulatorLoadTest()
    {
        System.out.println("You'll now load a game and will be asked at which turn the game was launched.");
        System.out.println("Please conduct this test with the load file provided with this game.");
        Simulator s = Simulator.load();
        s.initialisePlayers();
        s.shipDesignation();
        s.play();

        System.out.println("Please type which turn the game was launched at.");
        int result = Integer.parseInt(user_input.next());
        assertEquals(8, result);

    }
}
