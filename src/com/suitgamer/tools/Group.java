package com.suitgamer.tools;

import java.util.List;
import java.util.ArrayList;

import com.suitgamer.ifaces.Aggregable;

public class Group implements Aggregable<Group> {

  private List<Player> players = new ArrayList<Player>();

  public void addPlayer(Player player) {
    players.add(player);
  }

  public Player getPlayer(int index) {
    return players.get(index);
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  public void clear() {
    players.clear();
  }

  public void addAll(Group players) {
    for (Player player : players.getPlayers())
      addPlayer(player);
  }

  public String toString() {
    return players.toString();
  }

  public int size() {
    return players.size();
  }

  public void aggregate(Group group) {
    
    if (players == null)
      throw new NullPointerException ("Reference group can not have null players while aggregating");

    if (group.getPlayers() == null)
      throw new NullPointerException ("Aggregated group can not have null players while aggregating");

    if (players.size() !=  group.getPlayers().size())
      throw new IllegalStateException ("Can not aggregate two groups with different number of players. Reference has " + players.size() + " players while the aggregate has " + group.getPlayers().size());
    
    for (int i = 0; i < players.size(); i++)
      players.get(i).aggregate(group.getPlayer(i));

  }

}
