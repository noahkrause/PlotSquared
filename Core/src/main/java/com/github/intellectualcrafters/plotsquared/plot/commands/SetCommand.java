package com.github.intellectualcrafters.plotsquared.plot.commands;

import com.github.intellectualcrafters.plotsquared.plot.config.C;
import com.github.intellectualcrafters.plotsquared.plot.object.Location;
import com.github.intellectualcrafters.plotsquared.plot.object.Plot;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.MainUtil;
import com.github.intellectualcrafters.plotsquared.plot.util.Permissions;
import com.github.intellectualcrafters.plotsquared.plot.util.StringMan;

public abstract class SetCommand extends SubCommand {

  @Override
  public boolean onCommand(PlotPlayer player, String[] args) {
    Location loc = player.getLocation();
    Plot plot = loc.getPlotAbs();
    if (plot == null) {
      return !sendMessage(player, C.NOT_IN_PLOT);
    }
    if (!plot.hasOwner()) {
      if (!Permissions.hasPermission(player, C.PERMISSION_ADMIN_COMMAND.f(getFullId()))) {
        MainUtil.sendMessage(player, C.NO_PERMISSION, C.PERMISSION_ADMIN_COMMAND.f(getFullId()));
        MainUtil.sendMessage(player, C.PLOT_NOT_CLAIMED);
        return false;
      }
    }
    if (!plot.isOwner(player.getUUID())) {
      if (!Permissions.hasPermission(player, C.PERMISSION_ADMIN_COMMAND.f(getFullId()))) {
        MainUtil.sendMessage(player, C.NO_PERMISSION, C.PERMISSION_ADMIN_COMMAND.f(getFullId()));
        MainUtil.sendMessage(player, C.NO_PLOT_PERMS);
        return false;
      }
    }
    if (args.length == 0) {
      return set(player, plot, "");
    }
    return set(player, plot, StringMan.join(args, " "));
  }

  public abstract boolean set(PlotPlayer player, Plot plot, String value);

}
