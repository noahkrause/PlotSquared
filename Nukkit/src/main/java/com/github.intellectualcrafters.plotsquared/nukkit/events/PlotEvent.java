package com.github.intellectualcrafters.plotsquared.nukkit.events;

import cn.nukkit.event.Event;
import com.github.intellectualcrafters.plotsquared.plot.object.Plot;

public abstract class PlotEvent extends Event {

  private final Plot plot;

  public PlotEvent(Plot plot) {
    this.plot = plot;
  }

  public final Plot getPlot() {
    return this.plot;
  }

}
