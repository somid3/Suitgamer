package com.suitgamer.tools;

import java.util.Arrays;

import com.suitgamer.ifaces.Aggregable;

/**
 * Represents an ordinary player of any game where the player retains
 * cards which define its power of winning
 *
 * @author Omid Sadeghpour <somid3@gmail.com>
 * @version 1.0
 */
public class Player implements Aggregable<Player> {
    /**
     * Player name, only for debugging and identification purposes.
     * Set by the constructor
     */
    private String name;

    /**
     * Cards which the player holds in its hand. These are
     * non-changing cards. Meaning that the player will always have
     * these cards in its hand
     */
    private Deck lockedHand = new Deck();

    /**
     * Cards that a player receives randomly, these cards are
     * changeable and need to be returned back to the dealer who
     * randomly gave these cards to the player
     */
    private Deck unlockedHand = new Deck();

    /**
     * These cards are dealt randomly to the player, however these
     * represent community or board cards. After a game is finished
     * there is no need for the player to return these cards to the
     * dealer since the dealer has temporarely made these cards
     * accessible to all players
     */
    private Deck communityHand = new Deck();

    /**
     * One used a convenience object
     */
    private Deck hand = new Deck();

    /**
     * Total amount of money player has
     */
    private double bank;

    /**
     * Number of times this player has won a game
     */
    private int wonGames;

    /**
     * Number of times this payer tied a game
     */
    private int tiedGames;

    /**
     * Number of times this player has lost a game
     */
    private int lostGames;

    /**
     * Number representing the number of times this player won games.
     * When this player ties the points which he/she gains is the
     * percentage winner the player was in the tie
     */
    private double wonPoints;

    /**
     * Number representing the number of times player tied games.
     */
    private double tiedPoints;
    
    /**
     * Contains ten slots where this player's hand powers can be
     * stored. For any given player the higher the power of a hand the
     * more likely it is to win a game. The power is divided into
     * slots because many games (such as Poker) have multiple power
     * values. For instance in Poker the first power represents the
     * combo the player has on hand (ie: pair, two pair, etc) and the
     * second power element represents the high card and kicker within
     * such combo
     */
    private int[] power = new int[10];


    /**
     * Player constructor
     */
    public Player(String name) {
        this.name = new String(name);
    }

    /**
     * Returns the number of games the player won
     *
     * @return
     */
    public int getWonGames() {
        return wonGames;
    }

    /**
     * Returns the number of points the player accumlated after
     * winning X games taking ties into account
     *
     * @return
     */
    public double getWonPoints() {
        return wonPoints;
    }

/**
     * Returns the number of games the player tied
     *
     * @return
     */
    public int getTiedGames() {
        return tiedGames;
    }

    /**
     * Returns the number of points the player accumulated after
     * tying X games
     *
     * @return
     */
    public double getTiedPoints() {
        return tiedPoints;
    }



    /**
     * Returns the number of games the player lost
     *
     * @return
     */
    public int getLostGames() {
        return lostGames;
    }



    /**
     * Returns the power representation of a hand
     *
     * @return
     */
    public int[] getPower() {
        return power;
    }

    /**
     * Sets the power representation of a hand
     *
     * @param power
     * @return
     */
    public void setPower(int[] power) {
        this.power = power;
    }

    /**
     * Returns the player's bank
     *
     * @return
     */
    public double getBank() {
        return bank;
    }

    /**
     * Sets the player's bank
     *
     * @param bank
     * @return
     */
    public void setBank(double bank) {
        this.bank = bank;
    }

    /**
     * Returns the total number of cards this player holds, both
     * locked and unlocked cards
     *
     * @return
     */
    public int getNumberOfCardsOnHand() {
        // Summing up total number of cards in the different hand
        int count = lockedHand.getSize() + unlockedHand.getSize() + communityHand.getSize();

        return count;
    }

    /**
     * Each time a player wins, we increment his won games value by
     * one
     */
    public void won() {
        wonGames++;
        wonPoints++;
    }

    /**
     * Each time a player looses, we increment his lost games value by
     * one
     */
    public void lost() {
        lostGames++;
    }

    /**
     * Increment the winning percentage for a player. That is, if a
     * winner ties with three players (including himself) in a game
     * his points gained will be (1/3). In the rare event that a
     * player ties with zero players this method should not be called,
     * however, if called the accumulated points for the player will
     * not be affected
     *
     * @param numberOfPlayersWhoWon
     * @return
     */
    public void tied(int numberOfPlayersWhoWon) {

        // Calculating winning percentage for player
        double value = 1 / ((double) numberOfPlayersWhoWon);

        // Incrementing player tied points
        tiedPoints += value;

        // Incrementing tied games
        tiedGames++;
    }

    /**
     * Expensive method, returns a hand which contains all the
     * elements in {@link #lockedHand}, {@link #unlockedHand} and
     * {@link #communityHand}
     *
     * @return
     */
    public Deck getHand() {

        // Clearing all previous cards
        hand.clear();

        // Adding all locked cards to hand
        hand.getCards().addAll(getLockedHand().getCards());

        // Adding all unlocked cards to hand
        hand.getCards().addAll(getUnlockedHand().getCards());

        // Adding community cards to hand
        hand.getCards().addAll(getCommunityHand().getCards());

        return hand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Deck getLockedHand() {
        return lockedHand;
    }

    public Deck getUnlockedHand() {
        return unlockedHand;
    }

    public Deck getCommunityHand() {
        return communityHand;
    }

    public void setLockedHand(Deck deck) {
        lockedHand = deck;
    }

    public void setUnlockedHand(Deck deck) {
        unlockedHand = deck;
    }

    public void setCommunityHand(Deck deck) {
        communityHand = deck;
    }

    public void setWonGames (int wonGames) {
        this.wonGames = wonGames;
    }

    public void setLostGames (int lostGames) {
        this.lostGames = lostGames;
    }

    public void setWonPoints (double wonPoints) {
        this.wonPoints = wonPoints;
    }

    public void addLockedCard(Card card) {
        lockedHand.addCard(card);
    }

    public void addUnlockedCard(Card card) {
        unlockedHand.addCard(card);
    }

    public void addCommunityCard(Card card) {
        communityHand.addCard(card);
    }

    public void returnLockedHand(Deck deck) {
        deck.addCards(getLockedHand().getCards());
        getLockedHand().clear();
    }

    public void returnUnlockedHand(Deck deck) {
        deck.addCards(getUnlockedHand().getCards());
        getUnlockedHand().clear();
    }

    public void returnCommunityHand(Deck deck) {
        deck.addCards(getCommunityHand().getCards());
        getCommunityHand().clear();
    }


    /**
     * Returns a string containing all player details in a human
     * readable format
     *
     * @return
     */
    public String toString() {

        StringBuilder output = new StringBuilder();

        // Setting default new line
        char nl = '\n';

        output.append("Name: ").append(getName()).append(nl);
        output.append("Locked hand: ").append(getLockedHand()).append(nl);
        output.append("Unlocked hand: ").append(getUnlockedHand()).append(nl);
        output.append("Community hand: ").append(getCommunityHand()).append(nl);
        output.append("Hand: ").append(getHand()).append(nl);
        output.append("Won games: ").append(getWonGames()).append(nl);
        output.append("Won points: ").append(getWonPoints()).append(nl);
        output.append("Tied games: ").append(getTiedGames()).append(nl);
        output.append("Tied points: ").append(getTiedPoints()).append(nl);
        output.append("Lost games: ").append(getLostGames()).append(nl);
        output.append("Power: ").append(Arrays.toString(getPower())).append(nl);

        return output.toString();
    }

    public void aggregate(Player player) {
      wonGames = wonGames + player.getWonGames();
      wonPoints = wonPoints + player.getWonPoints();
      tiedGames = tiedGames + player.getTiedGames();
      tiedPoints = tiedPoints + player.getTiedPoints();
      lostGames = lostGames + player.getLostGames();
    }
}
