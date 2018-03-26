package com.github.intellectualcrafters.plotsquared.sponge.events;

import com.github.intellectualcrafters.plotsquared.plot.object.Plot;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.object.Rating;

public class PlotRateEvent extends PlotEvent {

  private final PlotPlayer rater;
  private Rating rating;

  public PlotRateEvent(final PlotPlayer rater, final Rating rating, final Plot plot) {
    super(plot);
    this.rater = rater;
    this.rating = rating;
  }

  public PlotPlayer getRater() {
    return rater;
  }

  public Rating getRating() {
    return rating;
  }

  public void setRating(final Rating rating) {
    this.rating = rating;
  }
}
