package com.suitgamer.tests;
import org.junit.*;
import org.junit.Assert;
import com.suitgamer.tools.Card;
import com.suitgamer.tools.Player;
import com.suitgamer.tools.Deck;
import com.suitgamer.tools.PokerHandEvaluator;

/**
 * Created by IntelliJ IDEA.
 * User: Omid
 * Date: Dec 16, 2007
 * Time: 11:16:16 AM
 */
public class PokerHandEvaluatorTest {

    @BeforeClass
    public static void setupTest() { }

    @Before
    public void setup() { }

    public void pairTest() {

        Deck pair = new Deck();
        pair.addCard( new Card('2', 'D', new int[]{2}));
        pair.addCard( new Card('A', 'S', new int[]{1,14}));
        pair.addCard( new Card('A', 'D', new int[]{1,14}));
        pair.addCard( new Card('5', 'H', new int[]{5}));
        pair.addCard( new Card('J', 'S', new int[]{11}));

        PokerHandEvaluator phe = new PokerHandEvaluator();
        phe.setHand(pair);
        phe.evaluate();

        Assert.assertTrue(phe.isPair());
        Assert.assertFalse(phe.isTwoPair());
        Assert.assertFalse(phe.isThreeOfAKind());
        Assert.assertFalse(phe.isStraight());
        Assert.assertFalse(phe.isFlush());
        Assert.assertFalse(phe.isFullHouse());
        Assert.assertFalse(phe.isFourOfAKind());
        Assert.assertFalse(phe.isStraightFlush());

    }

    @Test
    public void flushTest() {

        Deck pair = new Deck();
        pair.addCard( new Card('2', 'D', new int[]{2}));
        pair.addCard( new Card('A', 'D', new int[]{1,14}));
        pair.addCard( new Card('T', 'D', new int[]{10}));
        pair.addCard( new Card('5', 'D', new int[]{5}));
        pair.addCard( new Card('J', 'D', new int[]{11}));

        PokerHandEvaluator phe = new PokerHandEvaluator();
        phe.setHand(pair);
        phe.evaluate();

        Assert.assertFalse(phe.isPair());
        Assert.assertFalse(phe.isTwoPair());
        Assert.assertFalse(phe.isThreeOfAKind());
        Assert.assertFalse(phe.isStraight());
        Assert.assertTrue(phe.isFlush());
        Assert.assertFalse(phe.isFullHouse());
        Assert.assertFalse(phe.isFourOfAKind());
        Assert.assertFalse(phe.isStraightFlush());

    }
    
    @After
    public void cleanup() { }

    @AfterClass
    public static void cleanupTest() { }



}
