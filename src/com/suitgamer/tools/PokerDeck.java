package com.suitgamer.tools;

/**
 * Represents a regular deck of cards for the game of Poker
 *
 * @author Omid Sadeghpour <somid3@gmail.com>
 * @version 1.0
 */
public class PokerDeck extends Deck {

    /**
     * Initialize all the cards with their appropriate value by a For
     * loop in order to create a specific deck card
     */
    public PokerDeck() {
        super();

        /*
         * Defining all possible faces in deck, each face must be
         * represented by a single character
         */
        setFaces("23456789TJQKA");

        /*
         * Defining all possible suits in deck, each suit must be
         * represented by a single character
         */
        setSuits("CDHS");

        /*
         * Defining all possible ranks or values in deck, each rank or
         * value is represented by a single integer, the higher the
         * value of the integer the higher the card value
         */
        setRanks(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14});

        /*
         * Adding all the cards with their respective ranks, we add
         * all the cards manually for clarity and to make it obvious
         * that the 'Ace' has a dual rank
         */
        addCard(new Card('2', 'C', 2));
        addCard(new Card('2', 'D', 2));
        addCard(new Card('2', 'H', 2));
        addCard(new Card('2', 'S', 2));
        addCard(new Card('3', 'C', 3));
        addCard(new Card('3', 'D', 3));
        addCard(new Card('3', 'H', 3));
        addCard(new Card('3', 'S', 3));
        addCard(new Card('4', 'C', 4));
        addCard(new Card('4', 'D', 4));
        addCard(new Card('4', 'H', 4));
        addCard(new Card('4', 'S', 4));
        addCard(new Card('5', 'C', 5));
        addCard(new Card('5', 'D', 5));
        addCard(new Card('5', 'H', 5));
        addCard(new Card('5', 'S', 5));
        addCard(new Card('6', 'C', 6));
        addCard(new Card('6', 'D', 6));
        addCard(new Card('6', 'H', 6));
        addCard(new Card('6', 'S', 6));
        addCard(new Card('7', 'C', 7));
        addCard(new Card('7', 'D', 7));
        addCard(new Card('7', 'H', 7));
        addCard(new Card('7', 'S', 7));
        addCard(new Card('8', 'C', 8));
        addCard(new Card('8', 'D', 8));
        addCard(new Card('8', 'H', 8));
        addCard(new Card('8', 'S', 8));
        addCard(new Card('9', 'C', 9));
        addCard(new Card('9', 'D', 9));
        addCard(new Card('9', 'H', 9));
        addCard(new Card('9', 'S', 9));
        addCard(new Card('T', 'C', 10));
        addCard(new Card('T', 'D', 10));
        addCard(new Card('T', 'H', 10));
        addCard(new Card('T', 'S', 10));
        addCard(new Card('J', 'C', 11));
        addCard(new Card('J', 'D', 11));
        addCard(new Card('J', 'H', 11));
        addCard(new Card('J', 'S', 11));
        addCard(new Card('Q', 'C', 12));
        addCard(new Card('Q', 'D', 12));
        addCard(new Card('Q', 'H', 12));
        addCard(new Card('Q', 'S', 12));
        addCard(new Card('K', 'C', 13));
        addCard(new Card('K', 'D', 13));
        addCard(new Card('K', 'H', 13));
        addCard(new Card('K', 'S', 13));
        addCard(new Card('A', 'C', new int[]{1, 14}));
        addCard(new Card('A', 'D', new int[]{1, 14}));
        addCard(new Card('A', 'H', new int[]{1, 14}));
        addCard(new Card('A', 'S', new int[]{1, 14}));
    }
}
