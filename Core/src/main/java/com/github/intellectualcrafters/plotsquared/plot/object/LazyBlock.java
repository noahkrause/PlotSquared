package com.github.intellectualcrafters.plotsquared.plot.object;

public abstract class LazyBlock {

  public abstract PlotBlock getPlotBlock();

  public int getId() {
    return getPlotBlock().id;
  }
}
