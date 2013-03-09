package com.suitgamer.tools;

/**
 * Represents an ordinary english playing card used for playing games
 * such as Black Jack, Poker, Solitare, etc
 *
 * @author Omid Sadeghpour <somid3@gmail.com>, Michel Ph.
 *         <mic_ph005@yahoo.com>
 * @version 1.1
 */
public class Card {
    /**
     * The ranks is the value that a card has
     */
    private int[] ranks;

    /**
     * The suit is the value representing the type of the card.Example:
     * 'Diamont'
     */
    private char suit;

    /**
     * The face represents the value numeric of the card. Example: 'J'
     */
    private char face;

    /**
     * Card constructor to define a very-not-well-set-card.  It
     * has no rank, not defined face and suit.  Used to generate
     * trivial cards
     */
    public Card() {
        setRanks(-1);
        setFace('N');
        setSuit('N');
    }

    /**
     * Constructor used to initialize a very-specific-card that has multiple
     * ranks or values.  For instance this constructor is used to define
     * an 'Ace' since an 'Ace' in Poker has a value of 1 and 14
     *
     * @param face  Character defining card face
     * @param suit  Character defining card suit
     * @param ranks Integers defining the values or ranks of card
     */
    public Card(char face, char suit, int[] ranks) {
        setFace(face);
        setSuit(suit);
        setRanks(ranks);
    }

    /**
     * Constructor used to initialize a very-specific-card that has only one
     * rank or value.  For instance this constructor is used to define
     * a '3' since a '3' in Poker only has the value of 3
     *
     * @param face Character defining card face
     * @param suit Character defining card suit
     * @param rank Single integer
     */
    public Card(char face, char suit, int rank) {
        setFace(face);
        setSuit(suit);
        setRanks(rank);
    }

    /**
     * Sets the face of the Card to a character
     *
     * @param face
     * @return
     */
    public void setFace(char face) {
        this.face = face;
    }

    /**
     * Returns the face of a Card
     *
     * @return
     */
    public char getFace() {
        return this.face;
    }

    public void setSuit(char suit) {
        this.suit = suit;
    }

    public void setRanks(int[] ranks) {
        this.ranks = ranks;
    }

    public void setRanks(int rank) {
        this.ranks = new int[]{rank};
    }

    public char getSuit() {
        return suit;
    }

    public int[] getRanks() {
        return ranks;
    }

    public String toString() {

        StringBuilder output = new StringBuilder();

        output.append('[').append(face).append(suit).append('|');

        for (int rank : ranks)
            output.append(rank).append(',');

        output.deleteCharAt( output.length() - 1 ).append(']');

        return output.toString();
    }
}
