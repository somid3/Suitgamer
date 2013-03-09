package com.suitgamer.games;

import com.suitgamer.tools.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Reads arguments from the shell and creates the gaming environment for the
 * Seven-Card-Stud game and calls it as many times as required by the shell
 * parameter
 * 
 * @author Omid Sadeghpour <somid3@gmail.com>
 * @version 1.1
 */
public class SevenCardStudSimulator extends Simulator {

  public SevenCardStudSimulator() {
    playersCards = new ArrayList<String>();
    playersBank = new ArrayList<Double>();
  }
  
  /**
   * Runs a simulation of a seven card stud game
   */
  @Override
  public void simulate() {

    SevenCardStudGame specificGame = new SevenCardStudGame();
    specificGame.setDeck(new PokerDeck());
    specificGame.setupPlayers(playersCards, playersBank, communitiesCards);
    specificGame.setCardsPerPlayer(7);
    specificGame.play();
    
    game = specificGame;

    System.out.println("finished one game...");
  }

  public void run() {
    simulate();
  }
}
