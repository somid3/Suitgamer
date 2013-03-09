package com.suitgamer.ifaces;

public interface Aggregable<T extends Object> {

  void aggregate(T aggregable);
}
