package com.suitgamer.games;

import com.suitgamer.tools.*;
import com.suitgamer.games.Game;
import com.suitgamer.tools.PokerHandEvaluator;

import java.util.List;

/**
 * Simulates a single game of a flavor of Poker called '7 Card Stud' Before the
 * {@link #play()} method is called all players should be set and a deck must be
 * provided with {@link #setDeck(Deck)}. After {@link #play()} is called and the
 * results generated all the game data will be reset ready to start another game
 * 
 * @author Omid Sadeghpour <somid3@gmail.com>
 * @version 1.0
 */
class SevenCardStudGame extends Game {

  /**
   * Total number of cards that each player will be dealt on every game
   */
  protected int numberOfCardsPerPlayer;

  @Override
  protected void setupPlayers(List<String> playerCards, List<Double> playerBanks, List<String> communityCards) {

    if (playerBanks == null)
      throw new NullPointerException("bank values are null");
    if (playerCards == null)
      throw new NullPointerException("player cards are null");
    if (playerCards.size() != playerBanks.size())
      throw new NullPointerException("bank lenght does not equal player cards length");

    // Creating player(s) and adding them to the game
    for (int p = 0; p < playerCards.size(); p++) {

      // Creating player
      Player player = new Player("Player " + p);
      player.setBank(playerBanks.get(p));

      // Parsing player card
      String trimmedPlayerCards = playerCards.get(p).trim();

      // Where any cards provided for this player?
      if (trimmedPlayerCards.length() > 0) {

        // Adding player cards to player
        for (String playerCard : trimmedPlayerCards.split(" ")) {

          // Adding individual player card
          player.getLockedHand().getCards().add(deck.dealSpecificCard(playerCard.charAt(0), playerCard.charAt(1)));

        }

      }

      // Adding player to game
      addPlayer(player);

    }
  }

  @Override
  protected void play() {
    // Resetting game scenario
    reset();

    // Maximum score so far
    int[] maximumScore = evaluator.getScore();

    /*
     * Looping over each player, dealing as many necessary cards until each
     * player has at least 2 cards
     */
    for (Player player : players.getPlayers()) {

      // Dealing pocket cards
      int initialNumberOfCards = player.getNumberOfCardsOnHand();
      for (int c = 0; c < numberOfCardsPerPlayer - initialNumberOfCards; c++)
        player.getUnlockedHand().getCards().add(deck.dealRandomCard());

      // Scoring current player hand
      evaluator.reset();
      evaluator.setHand(player.getHand());
      evaluator.evaluate();
      player.setPower(evaluator.getScore());

      // Comparing player score with the best score so far
      int result = PokerHandEvaluator.compareScores(player.getPower(), maximumScore);

      // Do we have a new winner?
      if (result == 1) {

        /*
         * So far, yes, we have a new winner. So, take all the previous winners
         * and make them losers, clear the previous winners from the winners
         * list and add current player as the sole winner. Lastly update the new
         * maximum score
         */
        playersThatLost.addAll(playersThatWon);
        playersThatWon.clear();
        playersThatWon.addPlayer(player);
        maximumScore = player.getPower();

        // Do we have a new tie?
      } else if (result == 0) {

        /*
         * Well, we have a tie. Just add the current player to the list of
         * winners. Do not clear any list of players that have won or lost
         */
        playersThatWon.addPlayer(player);

        // Do we have a new loser?
      } else {

        /*
         * Sorry buddy, maybe next time. Take the current player and add it to
         * the losers list... Oh so very sad.
         */
        playersThatLost.addPlayer(player);

      }

    }

    /*
     * Looping over all players who won to compensate them, and looping over the
     * players who lost to punish them
     */

    // Group that won or tied
    for (Player player : playersThatWon.getPlayers()) {

      // Compensating player
      player.tied(playersThatWon.size());

    }

    // Group that lost
    for (Player player : playersThatLost.getPlayers()) {

      // Compensating player
      player.lost();

    }
  }

  @Override
  protected void reset() {
    // Clearing the list of players that won and lost
    playersThatWon.clear();
    playersThatLost.clear();

    // Looping over all players
    for (Player player : players.getPlayers()) {

      /*
       * Moving player unlocked cards back to game deck and disposing of all
       * community cards the player received
       */
      player.returnUnlockedHand(deck);
      player.returnCommunityHand(deck);

    }

    // Resetting Poker hand evaluator
    evaluator.reset();
  }

  public int getNumberOfCardsPerPlayer() {
    return numberOfCardsPerPlayer;
  }

  public void setCardsPerPlayer(int numberOfCardsPerPlayer) {
    this.numberOfCardsPerPlayer = numberOfCardsPerPlayer;
  }

}
