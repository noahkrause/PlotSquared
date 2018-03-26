package com.github.intellectualcrafters.plotsquared.nukkit.util;

import com.github.intellectualcrafters.plotsquared.plot.PS;
import com.github.intellectualcrafters.plotsquared.plot.generator.HybridUtils;
import com.github.intellectualcrafters.plotsquared.plot.object.RegionWrapper;
import com.github.intellectualcrafters.plotsquared.plot.object.RunnableVal;
import com.github.intellectualcrafters.plotsquared.plot.util.expiry.PlotAnalysis;

public class NukkitHybridUtils extends HybridUtils {

  public NukkitHybridUtils() {
    PS.debug("Not implemented: NukkitHybridUtils");
  }

  @Override
  public void analyzeRegion(final String world, final RegionWrapper region,
      final RunnableVal<PlotAnalysis> whenDone) {
    throw new UnsupportedOperationException("NOT IMPLEMENTED YET");
  }
}
