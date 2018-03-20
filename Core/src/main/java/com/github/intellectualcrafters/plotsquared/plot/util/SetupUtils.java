package com.github.intellectualcrafters.plotsquared.plot.util;

import com.github.intellectualcrafters.plotsquared.plot.generator.GeneratorWrapper;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotArea;
import com.github.intellectualcrafters.plotsquared.plot.object.SetupObject;
import java.util.HashMap;

public abstract class SetupUtils {

  public static SetupUtils manager;

  public static HashMap<String, GeneratorWrapper<?>> generators = new HashMap<>();

  public abstract void updateGenerators();

  public abstract String getGenerator(final PlotArea plotArea);

  public abstract String setupWorld(final SetupObject object);

  public abstract void unload(String world, boolean save);
}
