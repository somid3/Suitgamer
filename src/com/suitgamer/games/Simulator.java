package com.suitgamer.games;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.suitgamer.ifaces.Aggregable;
import com.suitgamer.tools.Player;

public abstract class Simulator implements Runnable, Aggregable<Simulator> {

  protected String name;
  protected Game game;
  protected int simulations;
  protected List<String> playersCards;
  protected List<String> communitiesCards;
  protected List<Double> playersBank;
  
  public void aggregate(Simulator simulator) {
    simulations = simulations + simulator.getSimulations();
    game.aggregate(simulator.getGame());
  }

  public String toString() {
    
    // Printing the winnings for each player
    DecimalFormat df = new DecimalFormat("00.00");
    int numberOfPlayers = game.getPlayers().size();
    StringBuilder out = new StringBuilder();
    String divider = "-----------------------------------------\n";
    
    out.append(name).append('\n');
    out.append("Simulations: ").append(simulations).append('\n');
    out.append('\n');
    out.append(divider);

    for (Player player : game.getPlayers().getPlayers()) {

      /*
       * Calculating winning ratio, advantage, completeness advantage, winning
       * likelihood, etc
       */
      int wonGames = player.getWonGames();
      int tiedGames = player.getTiedGames();
      double wonPoints = player.getWonPoints();
      double tiedPoints = player.getTiedPoints();
      double totalPoints = wonPoints + tiedPoints;
      double bank = player.getBank();

      double winningRatioOnGames = (double) wonGames / simulations;
      double winningRatioOnPoints = totalPoints / simulations;
      double winningLikelyhood = 1 / (double) numberOfPlayers;

      /*
       * Advantage is a number from [-100, +100] which represents the likelihood
       * for player zero to be the actual winner. An advantage of zero means the
       * player is at odds
       */
      double advantageOnGames = winningRatioOnGames - winningLikelyhood;
      double advantageOnPoints = winningRatioOnPoints - winningLikelyhood;

      // Normalizing the advantage based on won games
      if (advantageOnGames > 0) {
        advantageOnGames /= 1 - winningLikelyhood;
      } else {
        advantageOnGames /= winningLikelyhood;
      }

      // Normalizing the advantage based on won points
      if (advantageOnPoints > 0) {
        advantageOnPoints /= 1 - winningLikelyhood;
      } else {
        advantageOnPoints /= winningLikelyhood;
      }

      // Printing the results of this player
      out.append(
       "Advantage (%): " + df.format(advantageOnPoints * 100) + " ~ " + df.format(advantageOnGames * 100) + '\n' +
       "Max in Pot ($): " + df.format(advantageOnPoints * bank) + " ~ " + df.format(advantageOnGames * bank)  + '\n' +
       "Won/Tied Games: " + wonGames + '/' + tiedGames + '\n' +
       "Won/Tied Points: " + df.format(wonPoints) + '/' + df.format(tiedPoints) + '\n' +
       "Player: " + player.getName() + '\n');
      
      out.append(divider);

    }
    
    out.append('\n');
    out.append("Bye.");
    return out.toString();
  }
  
  public int getSimulations() {
    return simulations;
  }

  public List<String> getPlayersCards() {
    return playersCards;
  }

  public List<String> getCommunitiesCards() {
    return communitiesCards;
  }

  public List<Double> getPlayersBank() {
    return playersBank;
  }

  public void setSimulations(int simulations) {
    this.simulations = simulations;
  }

  public void setPlayersCards(List<String> playersCards) {
    this.playersCards = playersCards;
  }

  public void setCommunitiesCards(List<String> communitiesCards) {
    this.communitiesCards = communitiesCards;
  }

  public void setPlayersBank(List<Double> playersBank) {
    this.playersBank = playersBank;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }
  
  public abstract void simulate ();

}
