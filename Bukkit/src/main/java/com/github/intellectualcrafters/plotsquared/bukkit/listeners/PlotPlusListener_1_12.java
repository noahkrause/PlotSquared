package com.github.intellectualcrafters.plotsquared.bukkit.listeners;

import com.github.intellectualcrafters.plotsquared.bukkit.util.BukkitUtil;
import com.github.intellectualcrafters.plotsquared.plot.flag.Flags;
import com.github.intellectualcrafters.plotsquared.plot.object.Plot;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import java.util.UUID;

public class PlotPlusListener_1_12 implements Listener {
    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        LivingEntity ent = event.getEntity();
        if (ent instanceof Player) {
            Player player = (Player) ent;
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
}
