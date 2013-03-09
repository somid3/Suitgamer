package com.suitgamer.tests;
import org.junit.*;
import org.junit.Assert;
import com.suitgamer.tools.Card;
import com.suitgamer.tools.Deck;
import com.suitgamer.tools.PokerDeck;

/**
 * Created by IntelliJ IDEA.
 * User: Omid
 * Date: Dec 16, 2007
 * Time: 11:16:16 AM
 */
public class DeckTest {


    @BeforeClass
    public static void setupTest() { }

    @Before
    public void setup() { }

    @Test
    public void general() {

        // Setting up a random deck
        Deck original = new PokerDeck();
       
    }

    @After
    public void cleanup() { }

    @AfterClass
    public static void cleanupTest() { }



}
