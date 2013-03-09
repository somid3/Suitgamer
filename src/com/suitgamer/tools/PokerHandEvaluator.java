package com.suitgamer.tools;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Given a card containing cards from a Poker deck this object
 * provides a score or value to such hand. Granted that a higher score
 * or value means the hand is more likely to win a game of Poker.
 * 
 * This class makes many assumptions:
 * 1. Unlimited number of cards can exist in a hand
 * 2. Only one suit can be a flush suit at a time, otherwise the
 *    'flush ranks' variable must keep track of all the ranks
 *    in a flush for every suit
 * 3. There is only one straight flush per hand.  There can not
 *    be two straight flushes in a hand. Otherwise, the 'straight
 *    flush ranks' variable must keep track of all the ranks
 *    separately
 * 
 * @author Omid Sadeghpour <somid3@gmail.com>
 * @version 1.0
 */
public class PokerHandEvaluator {
    /*
     * Variables required to check and document all the hand qualities
     * that have so far been discovered
     */

    /**
     * Whenever this number becomes equal or greater than five, then a
     * flush will be documented
     */
    private int[] flushCheck;

    /**
     * Contains an array of integers representing the highest ranks
     * that form an flush. Given that there is no such thing as rank
     * zero, zero values are ignored
     */
    private int[] flushRanks;

    /**
     * Contains the integer-mapped-representation of the suit which at
     * least allows for one flush to occur in the hand. Since no suits
     * have an integer-mapped value of zero, zero values can be
     * ignored in this array
     */
    private int[] flushSuits;

    private int straightCheck;

    private int[] straightRanks;

    private int[] straightFlushRanks;

    private int[] straightFlushSuits;

    /**
     * Contains an array of integers representing the highest ranks
     * that form a pair. Given that there is no such thing as rank
     * zero, zero values are ignored
     */
    private int[] pairRanks;

    /**
     * Counts the number of pairs that have occured in the hand
     */
    private int pairCount;

    /**
     * Contains all the ranks of cards which do not form a pair. This
     * variable is used to determine the kickers for "pair" and
     * "two-pair" containing hands
     */
    private int[] pairKickers;

    /**
     * Contains an array of integers representing the highest ranks
     * that form a three of a kind. Given that there is no such thing
     * as rank zero, zero values are ignored
     */
    private int[] threeOfAKindRanks;

    /**
     * Contains all the ranks of cards which do not form a three of a
     * kind. This variable is used to determine the kickers for "three
     * of a kind" containing hands
     */
    private int[] threeOfAKindKickers;

    /**
     * Contains an array of integers representing the highest ranks
     * that form a four of a kind. Given that there is no such thing
     * as rank zero, zero values are ignored
     */
    private int[] fourOfAKindRanks;

    /**
     * Contains all the ranks of cards which do not form a four of a
     * kind. This variable is used to determine the kickers for "four
     * of a kind" containing hands
     */
    private int[] fourOfAKindKickers;

    /*
     * Variables containing overall hand qualities
     */

    /**
     * Determines whether or not the hand being scored cotains a pair.
     * Note that a three of a kind, or four of a kind, etc are not
     * pairs. A pair only occurs if and only if a rank appears only
     * twice
     */
    private boolean isPair;

    /**
     * Determines whether or not the hand being scored cotains a
     * two-pair. Note that a four of a kind is not a two-pair
     */
    private boolean isTwoPair;

    /**
     * Determines whether or not the hand being scored cotains a three
     * of a kind. Note that a full house is also a three of a kind,
     * but a four of a kind is not a three of kind. The three of a
     * kind occurs if and only if a rank appears three times
     */
    private boolean isThreeOfAKind;

    /**
     * Determines whether or not the hand being scored contains a
     * straigt
     */
    private boolean isStraight;

    /**
     * Determines whether or not the hand being scored contains a flush
     */
    private boolean isFlush;

    /**
     * Determines whether or not the hand being scored contains a full
     * house
     */
    private boolean isFullHouse;

    /**
     * Determines whether or not the hand being scored contains a four
     * of a kind
     */
    private boolean isFourOfAKind;

    /**
     * Determines whether or not the hand being scored contains a
     * straight flush
     */
    private boolean isStraightFlush;

    /*
     * Variables necessary for the score processing of the hand
     */

    /**
     * 2D Matrix of integers (board) used to dissect the provided hand
     * cards and score the Poker hand value
     */
    private Matrix board;

    /**
     * Hand containing the cards whose Poker value need to be analyzed
     */
    private Deck hand = new Deck();

    /**
     * Score representing the power of the poker hand provided with
     * {@link #setHand(Deck)}. The score is divided into multiple sections.
     * The first index indicates the type of hand, whereas the following
     * indexes are the sort ranks of all the cards of any importance.
     * 
     * For instance to represent a flush, these would be the following
     * scores:
     *  
     * Hand: AC 3C 5C 8C QC
     * Score:
     * [0] = 5
     * [1] = 14
     * [2] = 12
     * [3] = 8
     * [4] = 5
     * [5] = 3
     * 
     * Hand: 5C AD 5D 7S 9D
     * Score:
     * [0] = 1
     * [1] = 5
     * [2] = 14
     * [3] = 9
     * [4] = 7
     */
    private int[] score;

    /**
     * Reference Poker deck. This variable is not altered through out
     * the scoring procedure. This deck is only created to get an idea
     * of the suits, faces and ranks that are possible in the provided
     * hand
     */
    private Deck deck = new PokerDeck();

    /**
     * PokerHandEvaluator constructor
     */
    public PokerHandEvaluator()
    {
        /*
         * Creating matrix of integers where the rows are set by the
         * suits and each column represents a rank
         */
        board = new Matrix(deck.getSuits().length(), deck.getRanks().length);

        // Resetting object to instatiate all variabes
        reset();
    }

    /**
     * Saves a provided hand (of type Deck) to the local object
     * variable. It is the Cards in this hand that will populate the
     * {@link #board}
     * 
     * @param hand Deck of cards to be scored by the referee
     * @return
     */
    public void setHand(Deck hand)
    {
        this.hand = hand;
    }

    /**
     * Takes every card in the {@link #hand} and alters the respective
     * {@link #board} compartment for the specific card suit/rank For
     * instance if a hand contains a card with face "A" for "Ace",
     * suit "C" for "Clubs", and ranks 1 and 14. Then, we will change
     * the board integer value of location [1][1] and [1][14] to 1.
     * Likewise, if the same hand contains an "Ace of diamonds", "AD",
     * then we will change the board value at [2][1] and [2][14] to 1.
     * If a hand contains two "Ace of clubs" (meaning two decks were
     * used) then the board value at [1][1] and [1][14] will become 1 +
     * 1, that is 2. For more details as to what each board
     * compartment represents look at the documentation for the
     * {@link #board} variable
     * 
     * @return
     */
    private void populateBoard()
    {
        // Looping over all cards in hand
        for (Card card : hand.getCards()) {

            // Translating card suit to integer
            int suitDeckInt = this.deck.mapSuitToInt(card.getSuit());

            // Retrieving all card ranks or values
            int[] ranks = card.getRanks();

            // Looping over all card ranks
            for (int r = 0; r < ranks.length; r++) {

                // Retrieving present card rank
                int rankDeckInt = ranks[r];

                // Adding suit integer to board compartment
                board.addToCell(suitDeckInt, rankDeckInt, 1);

            }

        }
    }

    /**
     * Checks for the appearance of pairs, two pairs, three of a
     * kinds, four of a kind, flushes and full houses in the provided
     * hand
     * 
     * @return
     */
    private boolean evaluateLowerHands()
    {

        /*
         * Looping over all suits to check for flush, this region
         * of code appears here because we want to only store the
         * highest ranking flush values while ignoring rank 1
         */
        for (int s = 0; s < this.deck.getSuits().length(); s++) {

            // Retrieving suit character
            char suit = this.deck.getSuits().charAt(s);

            // Searching all cards of specific suit
            ArrayList<Card> suitedCards = this.hand.searchCardsOfSuit(suit);
            
            // Does the suit appear five or more times?
            if (suitedCards.size() >= 5) {

                // Yes, document flush
                this.isFlush = true;

                // Mapping suit to integer
                int suitDeckInt = this.deck.mapSuitToInt(suit);

                // Documenting flush suit
                this.flushSuits[s] = suitDeckInt;

                // Looping over all cards that form this flush
                for (Card card : suitedCards) {
                
                    // Looping over each rank of each flush card
                    for (int rank : card.getRanks()) {
                    
                        // Documenting flush rank
                        this.flushRanks[rank - 1] = rank;

                    }

                }
                
                /*
                 * Substracting one from flush checker so that the
                 * refee does not keep documenting flushes
                 */
                this.flushCheck[s]--;

            }

        }
        
        /*
         * Looping over all ranks except rank 1. We ignore rank 1
         * because rank 14 accounts for the lower-hand Poker value of
         * rank 1 given that the "Ace" is both of rank 1 and 14
         */
        for (int r = 1; r < this.deck.getRanks().length; r++) {

            // Retrieving actual rank value
            int rankDeckInt = this.deck.getRanks()[r];

            /*
             * Summing all elements in current column to generate a
             * number that represents the number of times a given rank
             * appears in the hand
             */
            int total = this.board.getColumnSum(rankDeckInt);

            /*
             * Continue only if the card with this rank appears at
             * least once, this is an optimization step and it also
             * makes sure the kickers actually do exist in the hand
             */
            if (total < 1) {
                continue;
            }

            // Does such rank appear twice or more?
            if (total == 2) {

                // Yes, document pair
                this.isPair = true;
                this.pairRanks[r] = rankDeckInt;
                this.pairCount++;

            } else {

                // Saving rank as a possible pair kicker
                this.pairKickers[r] = rankDeckInt;

            }

            // Does such rank appear trice or more?
            if (total == 3) {

                // Yes, document three of a kind
                this.isThreeOfAKind = true;
                this.threeOfAKindRanks[r] = rankDeckInt;

            } else {

                // Saving rank as a possible three of a kind kicker
                this.threeOfAKindKickers[r] = rankDeckInt;

            }

            // Does such rank appear four or more?
            if (total >= 4) {

                // Yes, document four of a kind
                this.isFourOfAKind = true;
                this.fourOfAKindRanks[r] = rankDeckInt;

            } else {

                // Saving rank as a possible four of a kind kicker
                this.fourOfAKindKickers[r] = rankDeckInt;

            }

        }

        // Has a two pair ocurred?
        if (this.pairCount >= 2) {

            // Yes, documenting two pair
            this.isTwoPair = true;

        }

        // Has a full house ocurred?
        if (this.isPair && this.isThreeOfAKind) {

            // Yes, document full house
            this.isFullHouse = true;

        }

        return true;
    }

    /**
     * Checks for the appearance of straigths and straigh flushes in
     * the provided hand. Straight flushes are only checked if in the
     * lower hands a flush was documented
     * 
     * @return
     */
    private boolean evaluateHigherHands()
    {
        // Are there enough cards for a hihger hands?
        if (hand.getCards().size() <= 4) {

            // No, not enough cards
            return true;

        }

        /* Checking for a straight */
        for (int r = 0; r < this.deck.getRanks().length; r++) {

            // Retrieving actual rank value
            int rankDeckInt = this.deck.getRanks()[r];

            /*
             * Increment straight checker value by one if the current
             * rank appears one or more times
             */
            if (this.board.getColumnSum(rankDeckInt) >= 1) {

                // Ok, this rank appears more than once
                this.straightCheck++;

            } else {

                /*
                 * No, this rank does not appear. Because there are no
                 * consecutive ranks right now restart the straight
                 * checker
                 */
                this.straightCheck = 0;

            }

            // Should a straight be documented?
            if (this.straightCheck >= 5) {

                // Yes, document straight
                this.isStraight = true;
                this.straightRanks[r] = rankDeckInt;

            }

        }

        /*
         * Ahead we check for straight flushes, if and only if both a
         * straight and a flush is already contained by the hand,
         * otherwise this function returns a true statement
         */
        if (!isFlush || !isStraight) {
            return true;
        }

        // Looping over flush suits
        for (int s = 0; s < this.flushSuits.length; s++) {

            // Retrieving suit quasi-id
            int suitDeckInt = this.flushSuits[s];

            // Consider this suit since its flush bearing
            if (suitDeckInt > 0) {

                // Looping over all straight ranks
                for (int x = 0; x < this.straightRanks.length; x++) {

                    // Retrieving rank quasi-id
                    int rankDeckInt = this.straightRanks[x];

                    // Consider this rank since its straight head
                    if (rankDeckInt > 0) {

                        /*
                         * Making sure the last five cards (including
                         * this one) are of the same suit
                         */
                        if (this.board.getCell(suitDeckInt, rankDeckInt - 0) >= 1 && this.board.getCell(suitDeckInt, rankDeckInt - 1) >= 1 && this.board.getCell(suitDeckInt, rankDeckInt - 2) >= 1 && this.board.getCell(suitDeckInt, rankDeckInt - 3) >= 1 && this.board.getCell(suitDeckInt, rankDeckInt - 4) >= 1) {

                            // Straight flush occured, document it
                            this.isStraightFlush = true;
                            this.straightFlushSuits[s] = suitDeckInt;

                            // Adding all ranks of straight flush cards
                            this.straightFlushRanks[x] = rankDeckInt;
                            this.straightFlushRanks[x - 1] = rankDeckInt - 1;
                            this.straightFlushRanks[x - 2] = rankDeckInt - 2;
                            this.straightFlushRanks[x - 3] = rankDeckInt - 3;
                            this.straightFlushRanks[x - 4] = rankDeckInt - 4;
                            
                        }

                    }

                }

            }

        }

        return true;
    }

    /**
     * Provides a score for the Poker hand provided which sumarizes
     * the hand value. The score contains two elements, the first
     * element indicates what type of hand it is. For instance, if the
     * first score element is 0 that means the provided hand contains
     * no noticeable power. If its 1, then the hand contains a "pair",
     * if it contains an 8, then the hand cotains a straight flush,
     * etc. Finally, after a hand has been scored its value can be
     * requested with {@link #getScore()}
     * </ul>
     * Summary of first element of score (value: highest hand combo)
     * <li>0: nothing</li>
     * <li>1: pair</li>
     * <li>2: two pair</li>
     * <li>3: three of a kind</li>
     * <li>4: straight</li>
     * <li>5: flush</li>
     * <li>6: full house</li>
     * <li>7: four of a kind</li>
     * <li>8: straight flush</li>
     * </ul>
     * The second element of the score contains the high cards that
     * complement the score's first element
     * 
     * @return
     */
    private boolean score()
    {
        // Scoring if: straight flushes
        if (this.isStraightFlush) {

            // Setting hand-combo element of score
            this.score[0] = 8;

            // Highest rank making the best straight possible
            Arrays.sort(this.straightFlushRanks);
            int length = this.straightFlushRanks.length;
            
            // Saving ranks to score
            this.score[1] = this.straightFlushRanks[length - 1];
            this.score[2] = this.straightFlushRanks[length - 2];
            this.score[3] = this.straightFlushRanks[length - 3];
            this.score[4] = this.straightFlushRanks[length - 4];
            this.score[5] = this.straightFlushRanks[length - 5];

            return true;

        }

        // Scoring if: four of a kinds
        if (this.isFourOfAKind) {

            // Setting hand-combo element of score
            this.score[0] = 7;

            // Highest ranking four of a kind
            this.score[1] = Matrix.max(this.fourOfAKindRanks);
            
            // Getting best kicker
            this.score[2] = Matrix.max(this.fourOfAKindKickers);

            return true;

        }

        // Scoring full houses
        if (this.isFullHouse) {

            // Setting hand-combo element of score
            this.score[0] = 6;

            // Highest ranking three-of-a-kind
            this.score[1] = Matrix.max(this.threeOfAKindRanks);
            
            // Highest ranking pair
            this.score[2] = Matrix.max(this.pairRanks);

            return true;

        }

        // Scoring flushes
        if (this.isFlush) {

            // Setting hand-combo element of score
            this.score[0] = 5;

            // Gathering best five card ranks in flush
            Arrays.sort(this.flushRanks);
            int length = this.flushRanks.length;
            this.score[1] = this.flushRanks[length - 1];
            this.score[2] = this.flushRanks[length - 2];
            this.score[3] = this.flushRanks[length - 3];
            this.score[4] = this.flushRanks[length - 4];
            this.score[5] = this.flushRanks[length - 5];

            return true;

        }

        // Scoring straights
        if (this.isStraight) {

            // Setting hand-combo element of score
            this.score[0] = 4;

            // Highest ranking straight
            this.score[1] = Matrix.max(this.straightRanks);

            return true;

        }

        // Scoring three of a kinds
        if (this.isThreeOfAKind) {

            // Setting hand-combo element of score
            this.score[0] = 3;

            // Highest ranking three of a kind
            this.score[1] = Matrix.max(this.threeOfAKindRanks);
            
            // Getting kickers
            Arrays.sort(this.threeOfAKindKickers);
            int length = this.threeOfAKindKickers.length;
            score[2] = this.threeOfAKindKickers[length - 1];
            score[3] = this.threeOfAKindKickers[length - 2];

            return true;

        }

        // Scoring two pairs
        if (this.isTwoPair) {

            // Setting hand-combo element of score
            this.score[0] = 2;

            // Highest ranking two pair
            Arrays.sort(this.pairRanks);
            int length = this.pairRanks.length;
            this.score[1] = this.pairRanks[length - 1];
            this.score[2] = this.pairRanks[length - 2];
            
            // Getting kicker
            this.score[3] = Matrix.max(this.pairKickers);
            
            return true;

        }

        // Scoring pairs
        if (this.isPair) {

            // Setting hand-combo element of score
            this.score[0] = 1;

            // Highest ranking pair
            this.score[1] = Matrix.max(this.pairRanks);

            // Getting kickers
            Arrays.sort(this.pairKickers);
            int length = this.pairKickers.length;
            this.score[2] = this.pairKickers[length - 1];
            this.score[3] = this.pairKickers[length - 2];
            this.score[4] = this.pairKickers[length - 3];

            return true;

        }

        /*
         * The hand contains no traceable Poker combos, just provide a
         * score based on the kickers
         */

        // Setting hand-combo element of score
        this.score[0] = 0;

        // Getting kickers
        Arrays.sort(this.pairKickers);
        int length = this.pairKickers.length;
        score[1] = this.pairKickers[length - 1];
        score[2] = this.pairKickers[length - 2];
        score[3] = this.pairKickers[length - 3];
        score[4] = this.pairKickers[length - 4];
        score[5] = this.pairKickers[length - 5];

        return true;
    }

    /**
     * Completely analyzes the Poker hand provided with
     * {@link #setHand(Deck)}
     * 
     * @return
     */
    public boolean evaluate()
    {
        // Disecting provided hand onto a matrix
        this.populateBoard();

        // Checking for pairs, two pairs, flushes, etc
        this.evaluateLowerHands();

        // Checking for straight and straight flushes
        this.evaluateHigherHands();

        // Providing a score to the hand
        this.score();

        return true;
    }

    /**
     * Returns the board of type Matrix which the referee uses to
     * score Poker hands
     * 
     * @return
     */
    public Matrix getBoard()
    {
        return this.board;
    }

    /**
     * Returns the Poker hand that is to be or was evaluated
     * 
     * @return
     */
    public Deck getHand()
    {
        return this.hand;
    }
    
    /**
     * Returns the score elements which quality the strength of the
     * provided Poker hand
     * 
     * @return
     */
    public int[] getScore()
    {
        return this.score;
    }

    /**
     * Given two scores (see {@link #score}) this method compares
     * the scores and determines which score is higher than the
     * other score. If both scores are equal then it documents
     * that a tie occured
     * <p>
     * Return values:
     * 0 = tie
     * 1 = first score is better
     * 2 = second score is better
     * 
     * @param score1 first score to compare
     * @param score2 second score to compare
     * @return
     */
    public static int compareScores(int[] score1, int[] score2)
    {
        /*
         * Looping over all elements of score1, here we
         * assume score1 and score2 have the same number
         * of elements
         */
        for (int i = 0; i < score1.length; i++) {
            
            // Is the current element in score1 greater than in score2
            if (score1[i] > score2[i]) {
                
                // Score1 is best
                return 1;
                
            } else if (score1[i] < score2[i]) {
                
                // Score2 is best
                return 2;
                
            }
            
        }
        
        // A tie resulted
        return 0;
        
    }
    
    /**
     * Resets all variabler of referee. The provided hand is not reset
     * 
     * @return
     */
    public void reset()
    {
        // Resetting hand quality-discovery variables
        straightCheck = 0;
        straightRanks = new int[deck.getRanks().length];
        straightFlushRanks = new int[deck.getRanks().length];
        straightFlushSuits = new int[deck.getSuits().length()];
        flushCheck = new int[deck.getSuits().length()];
        flushRanks = new int[deck.getRanks().length];
        flushSuits = new int[deck.getSuits().length()];
        pairRanks = new int[deck.getRanks().length];
        pairCount = 0;
        pairKickers = new int[deck.getRanks().length];
        threeOfAKindRanks = new int[deck.getRanks().length];
        threeOfAKindKickers = new int[deck.getRanks().length];
        fourOfAKindRanks = new int[deck.getRanks().length];
        fourOfAKindKickers = new int[deck.getRanks().length];

        // Resetting hand qualities
        isPair = false;
        isTwoPair = false;
        isThreeOfAKind = false;
        isStraight = false;
        isFlush = false;
        isFullHouse = false;
        isFourOfAKind = false;
        isStraightFlush = false;

        // Resetting hand score
        score = new int[6];

        // Resetting board
        board.reset();
    }

    public boolean isPair() { return isPair; }
    public boolean isTwoPair() { return isTwoPair; }
    public boolean isThreeOfAKind() { return isThreeOfAKind; }
    public boolean isStraight() { return isStraight; }
    public boolean isFlush() { return isFlush; }
    public boolean isFullHouse() { return isFullHouse; }
    public boolean isFourOfAKind() { return isFourOfAKind; }
    public boolean isStraightFlush() { return isStraightFlush; }
                                                
    /**
     * Returns a string containing hand evaluation details in a human
     * readable format
     * 
     * @return
     */
    public String toString()
    {
        // Instatiating temp output
        StringBuffer temp = new StringBuffer();

        // Setting default new line
        char nl = '\n';

        temp.append("Hand:" + nl);
        temp.append(hand.toString() + nl);
        temp.append(nl);
        temp.append("Score:" + nl);
        temp.append(Arrays.toString(score) + nl);
        temp.append(nl);
        temp.append("Board:" + nl);
        temp.append(Matrix.toReadableString(getBoard().getMatrix()) + nl);
        temp.append(nl);
        temp.append("Is pair: " + isPair + nl);
        temp.append("Is two pair: " + isTwoPair + nl);
        temp.append("Is three of a kind: " + isThreeOfAKind + nl);
        temp.append("Is straight: " + isStraight + nl);
        temp.append("Is flush: " + isFlush + nl);
        temp.append("Is full house: " + isFullHouse + nl);
        temp.append("Is four of a kind: " + isFourOfAKind + nl);
        temp.append("Is straight flush: " + isStraightFlush + nl);
        temp.append(nl);
        temp.append("Pair ranks: " + Arrays.toString(pairRanks) + nl);
        temp.append("Pair count: " + pairCount + nl);
        temp.append("Pair kickers: " + Arrays.toString(pairKickers) + nl);
        temp.append("Three of a kind ranks: " + Arrays.toString(threeOfAKindRanks) + nl);
        temp.append("Three of a kind kickers: " + Arrays.toString(threeOfAKindKickers) + nl);
        temp.append("Straight ranks: " + Arrays.toString(straightRanks) + nl);
        temp.append("Flush suits: " + Arrays.toString(flushSuits) + nl);
        temp.append("Flush ranks: " + Arrays.toString(flushRanks) + nl);
        temp.append("Four of a kind ranks: " + Arrays.toString(fourOfAKindRanks) + nl);
        temp.append("Four of a kind kickers: " + Arrays.toString(fourOfAKindKickers) + nl);
        temp.append("Straight flush ranks: " + Arrays.toString(straightFlushRanks) + nl);
        temp.append("Straight flush suits: " + Arrays.toString(straightFlushSuits) + nl);

        // Creating output
        String output = temp.toString();

        return output;
    }

}
