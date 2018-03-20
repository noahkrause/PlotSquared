package com.github.intellectualcrafters.plotsquared.bukkit.titles;

import com.github.intellectualcrafters.plotsquared.bukkit.object.BukkitPlayer;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.AbstractTitle;

public class DefaultTitle_183 extends AbstractTitle {

  @Override
  public void sendTitle(PlotPlayer player, String head, String sub, int in, int delay, int out) {
    try {
      DefaultTitleManager_183 title = new DefaultTitleManager_183(head, sub, in, delay, out);
      title.send(((BukkitPlayer) player).player);
    } catch (Exception ignored) {
      AbstractTitle.TITLE_CLASS = new HackTitle();
      AbstractTitle.TITLE_CLASS.sendTitle(player, head, sub, in, delay, out);
    }
  }
}
