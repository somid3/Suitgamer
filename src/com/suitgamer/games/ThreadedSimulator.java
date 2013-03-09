package com.suitgamer.games;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThreadedSimulator {

  private static int numberOfThreads = Runtime.getRuntime().availableProcessors();
  private static List<Thread> threads = new ArrayList<Thread>();
  private static List<Simulator> simulators = new ArrayList<Simulator>();

  public static void main(String[] args) throws InterruptedException {

    if (numberOfThreads < 1)
      throw new IllegalArgumentException("Number of threads can not be less than one.");

    List<String> playersCards = new ArrayList<String>();
    List<String> communitiesCards = new ArrayList<String>();
    List<Double> playersBank = new ArrayList<Double>();

    int simulationsPerThread =    10000                        ;
    int numberOfPlayers =         5                           ;
    playersBank.add(              6000d                       );

    playersCards.add("       ");
    communitiesCards.add("        ");



    /* Adding empty players */
    for (int i = playersCards.size(); i < numberOfPlayers; i++) {
      playersCards.add("");
      playersBank.add(0d);
    }
      
    
    /* Setting up threads */
    for (int i = 0; i <= numberOfThreads - 1; i++) {
      TexasHoldEmSimulator simulator = new TexasHoldEmSimulator();
      simulator.setSimulations(simulationsPerThread);
      simulator.setPlayersCards(playersCards);
      simulator.setPlayersBank(playersBank);
      simulator.setCommunitiesCards(communitiesCards);
      simulators.add(simulator);
      threads.add(new Thread(simulator));
    }

    /* Begin timer */
    Date begin = new Date();
    
    /* Starting threads */
    for (Thread thread : threads)
      thread.start();

    /* Waiting for threads to finish */
    for (Thread thread : threads)
      thread.join();

    /* End timer */
    Date end = new Date();
    long milliseconds = end.getTime() - begin.getTime();
    
    /* Aggregating results */
    TexasHoldEmSimulator result = new TexasHoldEmSimulator();
    result.setPlayersCards(playersCards);
    result.setPlayersBank(playersBank);
    result.setCommunitiesCards(communitiesCards);
    result.simulate();
    for (Simulator simulator : simulators)
      result.aggregate(simulator);

    /* Printing results */
    System.out.println("Number of threads: " + numberOfThreads);
    System.out.println("Milliseconds to compute: " + milliseconds);
    System.out.println(result.toString());
  }

}
