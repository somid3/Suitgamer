package com.suitgamer.games;

import com.suitgamer.tools.*;

import java.util.List;

/**
 * Simulates a single game of a flavor of Poker called 'Texas Hold Em' Before
 * the {@link #play()} method is called all players should be set and a deck
 * must be provided with {@link #setDeck(PokerDeck)}. After {@link #play()} is
 * called and the results generated all the game data will be reset ready to
 * start another game
 * 
 * @author Omid Sadeghpour <somid3@gmail.com>
 * @version 1.0
 */
class TexasHoldEmGame extends Game {

  /**
   * Instantiates the players, player that have won, players that have lost,
   * community cards, the deck to be used and the Poker hand evaluator to define
   * the winners
   */
  @Override
  protected void setupPlayers(List<String> playersCards, List<Double> playersBanks, List<String> communitiesCards) {

    if (playersBanks == null)
      throw new NullPointerException("Bank values can not be null");
    if (playersCards == null)
      throw new NullPointerException("Player cards can not be null");
    if (communitiesCards == null)
      throw new NullPointerException("Community cards can not be null");
    if (playersCards.size() != playersBanks.size())
      throw new IllegalStateException("Bank size does not equal player cards size");
    if (playersCards.size() < 2)
      throw new IllegalStateException("Number of players can not be less than two");
    if (communitiesCards.size() != 1)
      throw new IllegalStateException("Only one set of community cards are allowed");
    
    // Creating player(s) and adding them to the game
    String playerCards = null;
    for (int p = 0; p < playersCards.size(); p++) {

      // Creating player
      Player player = new Player("Player " + p);
      player.setBank(playersBanks.get(p));

      // Parsing player card
      playerCards = playersCards.get(p).trim();
      playerCards = playerCards.toUpperCase();

      // Where any cards provided for this player?
      if (playerCards.length() > 0) {

        // Adding player cards to player
        for (String playerCard : playerCards.split(" ")) {

          // In case the user entered an extra space in the input
          if (playerCard.length() == 0)
            continue;

          // Adding individual player card
          player.getLockedHand().getCards().add(deck.dealSpecificCard(playerCard.charAt(0), playerCard.charAt(1)));

        }

      }

      // Adding player to game
      addPlayer(player);
    }

    // Adding community player
    {
      String communityCards = communitiesCards.get(0);
      communityCards = communityCards.trim();
      communityCards = communityCards.toUpperCase();
      Player community = new Player("Community");
      for (String communityCard : communityCards.split(" ")) {
        if (communityCard.isEmpty())
          continue;
        
        // Adding individual community card
        community.getLockedHand().getCards().add(deck.dealSpecificCard(communityCard.charAt(0), communityCard.charAt(1)));
        
      }
      
      addCommunity(community);
    }
}

  /**
   * Deals unlocked (random) cards to all players until they reach their limit,
   * scores their hands using a poker hand evaluator, defines the winner, loser,
   * tiers, and retrieves back the dealt cards
   * 
   * @return
   */
  public void play() {

    Player community = communities.getPlayer(0);

    // Resetting game scenario
    reset();

    /*
     * Making sure the community cards in the game have the flop, turn and
     * river. That is the community must always have five cards
     */
    switch (community.getNumberOfCardsOnHand()) {
    case 0:
      community.getUnlockedHand().getCards().add(deck.dealRandomCard());
      community.getUnlockedHand().getCards().add(deck.dealRandomCard());
      community.getUnlockedHand().getCards().add(deck.dealRandomCard());
      community.getUnlockedHand().getCards().add(deck.dealRandomCard());
      community.getUnlockedHand().getCards().add(deck.dealRandomCard());
      break;
    case 1:
      community.getUnlockedHand().getCards().add(deck.dealRandomCard());
      community.getUnlockedHand().getCards().add(deck.dealRandomCard());
      community.getUnlockedHand().getCards().add(deck.dealRandomCard());
      community.getUnlockedHand().getCards().add(deck.dealRandomCard());
      break;
    case 2:
      community.getUnlockedHand().getCards().add(deck.dealRandomCard());
      community.getUnlockedHand().getCards().add(deck.dealRandomCard());
      community.getUnlockedHand().getCards().add(deck.dealRandomCard());
    case 3:
      community.getUnlockedHand().getCards().add(deck.dealRandomCard());
      community.getUnlockedHand().getCards().add(deck.dealRandomCard());
    case 4:
      community.getUnlockedHand().getCards().add(deck.dealRandomCard());
      break;
    }

    // Maximum score so far
    int[] maximumScore = evaluator.getScore();

    /*
     * Looping over each player, dealing as many necessary cards until each
     * player has at least 2 cards
     */
    for (Player player : players.getPlayers()) {

      // Dealing pocket cards
      switch (player.getNumberOfCardsOnHand()) {
      case 0:
        player.getUnlockedHand().getCards().add(deck.dealRandomCard());
        player.getUnlockedHand().getCards().add(deck.dealRandomCard());
        break;
      case 1:
        player.getUnlockedHand().getCards().add(deck.dealRandomCard());
        break;
      }

      // Adding community cards to player
      player.getCommunityHand().getCards().addAll(community.getHand().getCards());

      // Scoring current player hand
      evaluator.reset();
      evaluator.setHand(player.getHand());
      evaluator.evaluate();
      int[] currentScore = evaluator.getScore();
      player.setPower(currentScore);

      // Comparing player score with the best score so far
      int result = PokerHandEvaluator.compareScores(currentScore, maximumScore);

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
        maximumScore = currentScore;

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
    if (playersThatWon.size() == 1) {

       // Only one player won, documenting the win
       playersThatWon.getPlayer(0).won();

    } else {

        // More than one player tied
        for (Player player : playersThatWon.getPlayers()) {

          // Documenting the tie
          player.tied(playersThatWon.size());

        }
    }

    // Group that lost
    for (Player player : playersThatLost.getPlayers()) {

      // Documenting the loss
      player.lost();

    }

  }

}
