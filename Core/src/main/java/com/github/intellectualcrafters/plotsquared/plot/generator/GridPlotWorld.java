package com.github.intellectualcrafters.plotsquared.plot.generator;

import com.github.intellectualcrafters.plotsquared.plot.object.PlotArea;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotId;

public abstract class GridPlotWorld extends PlotArea {

  public short SIZE;

  public GridPlotWorld(String worldName, String id, IndependentPlotGenerator generator, PlotId min,
      PlotId max) {
    super(worldName, id, generator, min, max);
  }
}
