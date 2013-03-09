package com.suitgamer.games;

import com.suitgamer.ifaces.Aggregable;
import com.suitgamer.tools.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Omid
 * Date: Feb 3, 2008
 * Time: 12:36:48 PM
 */
public abstract class Game implements Aggregable<Game> {

    /**
     * All the players present in the game
     */
    protected Group players = new Group();

    /**
     * Group that represent the community cards
     * available on the table
     */
    protected Group communities = new Group();

    /**
     * After {@link #play} is called this variable will contain a
     * subset of {@link #players} which represents all the players who
     * won in the game just played. These players have had their "won"
     * or tied method called
     */
    protected Group playersThatWon = new Group();

    /**
     * After {@link #play()} is called this variable will contain a
     * subset of {@link #players} which represents all the players who
     * lost in the game just played. These players have had their
     * "lost" method called
     */
    protected Group playersThatLost = new Group();

    /**
     * Deck used to deal random cards to simulate a game
     */
    protected Deck deck = new PokerDeck();

    /**
     * Evaluator which assigns a score to every Poker hand held by
     * each player
     */
    protected PokerHandEvaluator evaluator = new PokerHandEvaluator();


    public Group getPlayers() {
        return players;
    }

    public Player getPlayer(int index) {
        return players.getPlayers().get(index);
    }

    public void setPlayers(Group players) {
        this.players = players;
    }

    public void addPlayer (Player player) {
        players.addPlayer(player);
    }

    public Group getPlayersThatWon() {
        return playersThatWon;
    }

    public void setPlayersThatWon(Group playersThatWon) {
        this.playersThatWon = playersThatWon;
    }

    public Group getPlayersThatLost() {
        return playersThatLost;
    }

    public void setPlayersThatLost(Group playersThatLost) {
        this.playersThatLost = playersThatLost;
    }

    public void addCommunity (Player community) {
      communities.addPlayer(community);
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public PokerHandEvaluator getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(PokerHandEvaluator evaluator) {
        this.evaluator = evaluator;
    }
    
    public Group getCommunities() {
      return communities;
    }

    public void setCommunities(Group communities) {
      this.communities = communities;
    };

    /**
     * Deals unlocked (random) cards to all players until they reach
     * their limit, scores their hands using a poker hand evaluator,
     * defines the winner, loser, tiers, and retrieves back the dealt
     * cards
     */
    protected void play() {};

    /**
     * Takes back all the unlocked cards (random) that were dealt to
     * each player and adds it back to the game deck. Ditto for all
     * the community cards. Clears the list of players that won and
     * lost. All in all, this method resets the game scenario to start
     * another game
     */
    /**
     * Takes back all the unlocked cards (random) that were dealt to
     * each player and adds it back to the game deck. Ditto for all
     * the community cards. Clears the list of players that won and
     * lost. All in all, this method resets the game scenario to start
     * another game
     */
    protected void reset()
    {
        // Clearing the list of players that won and lost
        playersThatWon.clear();
        playersThatLost.clear();

        // Moving community cards back to game deck
        for (Player community : communities.getPlayers()) {
          deck.getCards().addAll(community.getUnlockedHand().getCards());
          community.getUnlockedHand().getCards().clear();
        }
        

        // Looping over all players
        for (Player player : players.getPlayers()) {

            /*
             * Moving player unlocked cards back to game deck and
             * disposing of all community cards the player received
             */
            deck.getCards().addAll(player.getUnlockedHand().getCards());
            player.getUnlockedHand().getCards().clear();
            player.getCommunityHand().getCards().clear();

        }
        
        // Resetting Poker hand evaluator
        evaluator.reset();
    }

    /**
     * Parses the player(s) and community(ies) cards, constructs
     * the players and setups the game ready to be {@link #play()}ed
     *
     * @param playersCards Strings containing unparsed player cards
     * @param playersBank Pot values of each player
     * @param communitiesCards Strings containing unparsed community cards
     */
    protected void setupPlayers(List<String> playersCards, List<Double> playersBank, List<String> communitiesCards) {}
    
    public void aggregate(Game game) {
      players.aggregate(game.getPlayers());
    }
}
