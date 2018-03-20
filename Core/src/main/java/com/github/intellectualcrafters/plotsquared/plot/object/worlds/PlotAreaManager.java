package com.github.intellectualcrafters.plotsquared.plot.object.worlds;

import com.github.intellectualcrafters.plotsquared.plot.object.Location;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotArea;
import com.github.intellectualcrafters.plotsquared.plot.object.RegionWrapper;

public interface PlotAreaManager {

  public PlotArea getApplicablePlotArea(Location location);

  public PlotArea getPlotArea(Location location);

  public PlotArea getPlotArea(String world, String id);

  public PlotArea[] getPlotAreas(String world, RegionWrapper region);

  public PlotArea[] getAllPlotAreas();

  public String[] getAllWorlds();

  public void addPlotArea(PlotArea area);

  public void removePlotArea(PlotArea area);

  public void addWorld(String worldName);

  public void removeWorld(String worldName);

}
