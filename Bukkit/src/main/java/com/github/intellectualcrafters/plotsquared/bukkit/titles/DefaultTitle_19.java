package com.github.intellectualcrafters.plotsquared.bukkit.titles;

import com.github.intellectualcrafters.plotsquared.bukkit.object.BukkitPlayer;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.AbstractTitle;
import com.github.intellectualcrafters.plotsquared.plot.util.TaskManager;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class DefaultTitle_19 extends AbstractTitle {

  @Override
  public void sendTitle(PlotPlayer player, String head, String sub, int in, int delay, int out) {
    try {
      final Player playerObj = ((BukkitPlayer) player).player;
      playerObj.sendTitle(head, sub);
      TaskManager.runTaskLater(new Runnable() {
        @Override
        public void run() {
          playerObj.sendTitle("", "");
        }
      }, delay * 20);
    } catch (Throwable ignored) {
      AbstractTitle.TITLE_CLASS = new DefaultTitle_183();
      AbstractTitle.TITLE_CLASS.sendTitle(player, head, sub, in, delay, out);
    }
  }
}
