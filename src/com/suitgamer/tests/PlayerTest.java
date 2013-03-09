package com.suitgamer.tests;

import org.junit.*;

import com.suitgamer.tools.Card;
import com.suitgamer.tools.Player;

public class PlayerTest {

  @BeforeClass
  public static void setupTest() {
  }

  @Before
  public void setup() {
  }

  @Test
  public void general() {

    Player pig = new Player("Bob");
    pig.won();
    pig.won();
    pig.won();
    pig.lost();
    pig.tied(2);
    pig.setBank(102);

    Assert.assertEquals(3, pig.getWonGames());
    Assert.assertEquals(1, pig.getLostGames());
    Assert.assertEquals(1, pig.getTiedGames());
    Assert.assertTrue(0.5 == pig.getTiedPoints());

    pig.getLockedHand().getCards().add(new Card('5', 'S', 5));
    pig.getLockedHand().getCards().add(new Card('T', 'D', 10));
    pig.getLockedHand().getCards().add(new Card('K', 'C', 13));
    pig.getLockedHand().getCards().add(new Card('6', 'S', 6));
    pig.getLockedHand().getCards().add(new Card('J', 'D', 11));
    pig.getLockedHand().getCards().add(new Card('A', 'C', new int[] { 1, 14 }));

    pig.getUnlockedHand().getCards().add(new Card('6', 'C', 6));
    pig.getUnlockedHand().getCards().add(new Card('Q', 'D', 12));
    pig.getUnlockedHand().getCards().add(new Card('6', 'C', 6));
    pig.getUnlockedHand().getCards().add(new Card('K', 'S', 13));
    pig.getUnlockedHand().getCards().add(new Card('3', 'H', 3));
    pig.getUnlockedHand().getCards().add(new Card('4', 'D', 4));

    pig.getCommunityHand().getCards().add(new Card('2', 'S', 2));
    pig.getCommunityHand().getCards().add(new Card('J', 'S', 11));
    pig.getCommunityHand().getCards().add(new Card('9', 'D', 9));
    pig.getCommunityHand().getCards().add(new Card('T', 'C', 10));
    pig.getCommunityHand().getCards().add(new Card('4', 'C', 4));
    pig.getCommunityHand().getCards().add(new Card('K', 'S', 12));

  }

  @After
  public void cleanup() {
  }

  @AfterClass
  public static void cleanupTest() {
  }

}
