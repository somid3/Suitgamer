package com.suitgamer.tests;
import org.junit.*;
import org.junit.Assert;
import com.suitgamer.tools.Card;

/**
 * Created by IntelliJ IDEA.
 * User: Omid
 * Date: Dec 16, 2007
 * Time: 11:16:16 AM
 */
public class CardTest {


    @BeforeClass
    public static void setupTest() { }

    @Before
    public void setup() { }

    @Test
    public void general() {

        // To string feature
        Card original = new Card('A', 'D', new int[]{1,14});
        Assert.assertEquals("[AD|1,14]", original.toString());

        // Construction state
        Card blank = new Card();
        Assert.assertEquals("[NN|-1]", blank.toString());

    }

    @After
    public void cleanup() { }

    @AfterClass
    public static void cleanupTest() { }



}
