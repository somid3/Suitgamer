package com.suitgamer.games;

import java.util.ArrayList;

import com.suitgamer.tools.*;

/**
 * Reads arguments from the shell and creates the gaming environment for the
 * Texas Hold Em game and calls it as many times as required by the shell
 * parameter
 * 
 * @author Omid Sadeghpour <somid3@gmail.com>
 * @version 1.1
 */
public class TexasHoldEmSimulator extends Simulator {

  public TexasHoldEmSimulator() {
    game = new TexasHoldEmGame();
    name = "Texas Hold'em Simulator";
    playersCards = new ArrayList<String>();
    playersBank = new ArrayList<Double>();
  }
  
  /**
   * Reads the arguments from the shell and sets up the required simulation
   * variables
   */
  public void run() {
    simulate();
  }
  
  public void simulate() {
    
    /* Setting up game */
    game.setupPlayers(playersCards, playersBank, communitiesCards);
    game.setDeck(new PokerDeck());
    
    /* Running Monte-Carlo simulations */
    for (int s = 0; s < simulations - 1; s++)
      game.play();
  }
  
}
