package com.suitgamer.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Represents a conglomerate of Cards that can be used as an object in
 * a game
 *
 * @author Omid Sadeghpour <somid3@gmail.com>
 * @version 1.0
 */
public class Deck {
    /**
     * Array containing all the Cards held within the Deck
     */
    private ArrayList<Card> cards = new ArrayList<Card>();

    /**
     * All the possible values or ranks which a regular poker card
     * could have. For instance, the 'Ace of clubs' has a rank of both
     * 1 and 14. A 'King of diamonds' has a rank or value of 13. Had
     * one been playing Blackjack instead of Poker then the Ace would
     * have a rank of 1 or 11
     */
    private int[] ranks = new int[1];

    /**
     * All possible suits that any card in the Poker deck could have.
     * In Poker decks a 'C' represents a 'Club', a 'D' a 'Diamond', a
     * 'H' a 'Heart' and a 'S' a 'Spade'. There is also the special
     * suits such as 'W' which stands for 'Wild', that is any suit and
     * 'N', meaning no suit defined. However the special suits 'W' and
     * 'N' are not listed in the array since they are special
     */
    private String suits = "";

    /**
     * The card face represents the image or character the human
     * player sees on the card to identify the card ranks. Faces
     * include 'A' for 'Aces', '2' for 'Twos', etc. There is on
     * exception, the '10' of any card is represented as a 'T'. This
     * is so that all cards in Poker can be represented with two
     * characters. There are also special faces such as 'W' which
     * means 'Wild' -- meaning it could stand for any face and 'N'
     * meaning no face defined
     */
    private String faces = "";

    /**
     * Deck constructor
     */
    public Deck(int size) {
        cards.ensureCapacity(size);
    }

    public Deck() {
    }

    /**
     * Adds a new card to the deck
     *
     * @param card Card to be added
     */
    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void addCards(List<Card> cards) {
        this.cards.addAll(cards);
    }

    /**
     * Deals the corresponding Card in the index of the Deck and
     * removes it from the Deck so that it is not dealt again in the
     * future. If the Deck contains no cards in its list of Cards then
     * a not-well-defined card is returned
     *
     * @param index Index position of card to be returned
     * @return Card dealt
     */
    public Card dealCard(int index) {

        // Reading the first Card in the Cards array list
        Card card = cards.get(index);

        // Removing the newly acquired Card
        cards.remove(index);

        // Returning the specified card
        return card;
    }

    /**
     * Deals a Card from the deck given a specified Card with the
     * required face and suit. If no such Card exists in the Deck then
     * return a null Card object that has the pre-determined default
     * face and suit
     *
     * @param card Card of the specified face and suit which should be
     *             dealt from the Deck. The rank of such provided card
     *             is trivial
     * @return
     */
    public Card dealSpecificCard(Card card) {
        /*
         * Given that a Deck might be empty and an error might occur
         * we try this code, if it fails method returns a null Card
         */
        try {

            // Searching for a card with similar face and suit
            int index = searchSpecificCard(card);

            // Saving the card to return
            card = cards.get(index);

            // Removing the newly acquired Card
            cards.remove(index);

            // Returning the specified card
            return card;

        } catch (Exception e) {

            // Returning a null card
            return new Card();

        }
    }

    /**
     * Provided a face and suit character this method returns a
     * similar card from the Deck
     *
     * @param face
     * @param suit
     * @return
     */
    public Card dealSpecificCard(char face, char suit) {
        /*
         * Given that a Deck might be empty and an error might occur
         * we try this code, if it fails method returns a null Card
         */
        try {

            // Searching for a card with the provided face and suit
            int index = searchSpecificCard(face, suit);

            // Retrieving the card whose index was found
            Card card = cards.get(index);

            // Removing the card that was returned from the search
            cards.remove(index);

            // Returning the found card
            return card;

        } catch (Exception e) {

            // Returning a null card
            return new Card();
        }
    }

    /**
     * Search for the specified requested card in the deck, if
     * the card is not found then a negative number is returned
     *
     * @param card Card of the specified face and suit which should be
     *             dealt from the Deck. The rank of such provided card
     *             is trivial
     * @return
     */
    private int searchSpecificCard(Card card) {
        // Search if such a Card exists in the Deck
        return searchSpecificCard(card.getFace(), card.getSuit());
    }

    /**
     * Search for the specified requested card in the deck by
     * providing the face and suit. If such a card is not found then
     * this method returns a negative number
     *
     * @param face
     * @param suit
     * @return
     */
    private int searchSpecificCard(char face, char suit) {
        // Setting default index if card found
        int i = 0;

        // Looping over all Cards in deck
        for (Card card : cards) {

            /*
             * Does the provided face and suit match the card face and
             * suit?
             */
            if (card.getFace() == face && card.getSuit() == suit) {

                // Yes, return the current index
                return i;

            }

            // No match, increment index and go to next card
            i++;
        }

        // No card found, return an erronious number
        return -1;
    }

    /**
     * Returns an array of cards of a specified suit. The cards
     * returned are not removed from the deck. Only methods
     * starting with 'deal' remove cards from the deck
     *
     * @param suit
     * @return
     */
    public ArrayList<Card> searchCardsOfSuit(char suit) {
        
        // Setting default index if card found
        ArrayList<Card> out = new ArrayList<Card>();

        // Looping over all Cards in deck
        for (Card card : cards) {

            /*
             * Does the provided suit match the card suit?
             */
            if (card.getSuit() == suit) {

              // Yes, add this card to the return cards
              out.add(card);

            }

        }

        // Return all the cards found
        return out;
    }


    /**
     * Get a random Card and then remove it from the list
     *
     * @return
     */
    public Card dealRandomCard() {
        // Obtain the number total of cards available in the list
        Integer space = getSize();

        // Get a random number from 0 to the range of the list size
        Integer index = (int) (Math.random() * space);

        /*
         * Create a new Card object and assign it the value of the
         * corresponding cards random index, and then remove that card
         * from the cards list by calling the {@link removeCard()}
         * method defined above
         */
        Card card = dealCard(index);

        // Returning the random card
        return card;
    }

    /**
     * Returns the total number of Card elements stored in the Deck
     *
     * @return
     */
    public Integer getSize() {
        return cards.size();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    /**
     * Return a formated string of all the Cards contained in the Deck
     */
    public String toString() {
        return cards.toString();
    }

    /**
     * Removes all cards in the cards list and sets the cards list
     * capacity to a value similar to the most recent number of cards
     * in hand
     *
     * @return
     */
    public void clear() {
        // Retrieving previous number of cards
        int lastSize = cards.size();

        // Clearing all cards but setting similar capacity
        cards.clear();
        cards.ensureCapacity(lastSize);
    }

    /**
     * Given a suit in character form (human form) this method
     * translates the suit into a unique integer. Note that an integer
     * of value 0 will never result
     *
     * @param suit
     * @return
     */
    public int mapSuitToInt(char suit) {
        // Getting suit index in suits
        int index = suits.indexOf(suit);

        // Mapping index
        index++;

        return index;
    }

    /**
     * Given a face in character form (human form) this method
     * translates the face into a unique integer. Note that an integer
     * of value 0 will never result
     *
     * @param face
     * @return
     */
    public int mapFaceToInt(char face) {
        // Getting face index in faces
        int index = faces.indexOf(face);

        // Mapping index
        index++;

        return index;
    }

    /**
     * Given an integer this method translates the suit index into its
     * character form. Note that the first suit index is 1 not 0
     *
     * @param index
     * @return
     */
    public char mapIntToSuit(int index) {
        // Mapping index
        index--;

        // Returning character index
        return suits.charAt(index);
    }

    /**
     * Given an integer this method translates the face index into its
     * character form. Note that the first face index is 1 not 0
     *
     * @param index
     * @return
     */
    public char mapIntToFace(int index) {
        // Mapping index
        index--;

        // Returning character index
        return faces.charAt(index);
    }

    public void setFaces (String faces) {
        this.faces = faces;
    }

    public void setRanks (int[] ranks) {
        this.ranks = ranks;
    }

    public void setSuits (String suits) {
        this.suits = suits;
    }

    public String getFaces () {
        return faces;
    }

    public int[] getRanks () {
        return ranks;
    }

    public String getSuits () {
        return suits;
    }
}
