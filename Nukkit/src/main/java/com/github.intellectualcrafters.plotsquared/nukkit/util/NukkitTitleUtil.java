package com.github.intellectualcrafters.plotsquared.nukkit.util;

import cn.nukkit.Player;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.AbstractTitle;
import com.github.intellectualcrafters.plotsquared.nukkit.object.NukkitPlayer;

public class NukkitTitleUtil extends AbstractTitle {

  @Override
  public void sendTitle(PlotPlayer player, String head, String sub, int in, int delay, int out) {
    Player plr = ((NukkitPlayer) player).player;
    plr.sendTitle(head, sub, in, delay, out);
  }
}
