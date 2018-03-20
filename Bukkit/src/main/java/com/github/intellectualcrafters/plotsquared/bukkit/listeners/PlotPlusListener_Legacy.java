package com.github.intellectualcrafters.plotsquared.bukkit.listeners;

import com.github.intellectualcrafters.plotsquared.bukkit.util.BukkitUtil;
import com.github.intellectualcrafters.plotsquared.plot.flag.Flags;
import com.github.intellectualcrafters.plotsquared.plot.object.Plot;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlotPlusListener_Legacy implements Listener {

  @EventHandler
  public void onItemPickup(PlayerPickupItemEvent event) {
    Player player = event.getPlayer();
    PlotPlayer pp = BukkitUtil.getPlayer(player);
    Plot plot = BukkitUtil.getLocation(player).getOwnedPlot();
    if (plot == null) {
      return;
    }
    UUID uuid = pp.getUUID();
    if (!plot.isAdded(uuid) && Flags.DROP_PROTECTION.isTrue(plot)) {
      event.setCancelled(true);
    }
  }
}
