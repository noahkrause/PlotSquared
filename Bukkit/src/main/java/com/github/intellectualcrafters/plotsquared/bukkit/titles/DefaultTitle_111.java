package com.github.intellectualcrafters.plotsquared.bukkit.titles;

import com.github.intellectualcrafters.plotsquared.bukkit.object.BukkitPlayer;
import com.github.intellectualcrafters.plotsquared.bukkit.util.BukkitVersion;
import com.github.intellectualcrafters.plotsquared.plot.PS;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.AbstractTitle;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class DefaultTitle_111 extends AbstractTitle {

  private final boolean valid;

  public DefaultTitle_111() {
    this.valid = PS.get().checkVersion(PS.get().IMP.getServerVersion(), BukkitVersion.v1_11_0);
  }

  @Override
  public void sendTitle(PlotPlayer player, String head, String sub, int in, int delay, int out) {
    if (valid) {
      try {
        final Player playerObj = ((BukkitPlayer) player).player;
        TitleManager_1_11 title = new TitleManager_1_11(head, sub, in, delay, out);
        title.send(playerObj);
        return;
      } catch (Throwable ignored) {
      }
    }
    AbstractTitle.TITLE_CLASS = new DefaultTitle_180();
    AbstractTitle.TITLE_CLASS.sendTitle(player, head, sub, in, delay, out);
  }
}
