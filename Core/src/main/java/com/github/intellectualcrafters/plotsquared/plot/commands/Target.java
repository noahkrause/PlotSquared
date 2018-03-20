package com.github.intellectualcrafters.plotsquared.plot.commands;

import com.github.intellectualcrafters.plotsquared.commands.Argument;
import com.github.intellectualcrafters.plotsquared.commands.CommandDeclaration;
import com.github.intellectualcrafters.plotsquared.plot.PS;
import com.github.intellectualcrafters.plotsquared.plot.config.C;
import com.github.intellectualcrafters.plotsquared.plot.object.Location;
import com.github.intellectualcrafters.plotsquared.plot.object.Plot;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.MainUtil;
import com.github.intellectualcrafters.plotsquared.plot.util.StringMan;

@CommandDeclaration(
    command = "target",
    usage = "/plot target <<plot>|nearest>",
    description = "Target a plot with your compass",
    permission = "plots.target",
    requiredType = RequiredType.PLAYER,
    category = CommandCategory.INFO)
public class Target extends SubCommand {

  public Target() {
    super(Argument.PlotID);
  }

  @Override
  public boolean onCommand(PlotPlayer player, String[] args) {
    Location location = player.getLocation();
    if (!location.isPlotArea()) {
      MainUtil.sendMessage(player, C.NOT_IN_PLOT_WORLD);
      return false;
    }
    Plot target = null;
    if (StringMan.isEqualIgnoreCaseToAny(args[0], "near", "nearest")) {
      int distance = Integer.MAX_VALUE;
      for (Plot plot : PS.get().getPlots(location.getWorld())) {
        double current = plot.getCenter().getEuclideanDistanceSquared(location);
        if (current < distance) {
          distance = (int) current;
          target = plot;
        }
      }
      if (target == null) {
        MainUtil.sendMessage(player, C.FOUND_NO_PLOTS);
        return false;
      }
    } else if ((target = MainUtil.getPlotFromString(player, args[0], true)) == null) {
      return false;
    }
    player.setCompassTarget(target.getCenter());
    MainUtil.sendMessage(player, C.COMPASS_TARGET);
    return true;
  }
}
