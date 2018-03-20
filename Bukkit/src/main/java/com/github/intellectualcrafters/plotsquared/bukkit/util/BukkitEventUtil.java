package com.github.intellectualcrafters.plotsquared.bukkit.util;

import com.github.intellectualcrafters.plotsquared.bukkit.events.ClusterFlagRemoveEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.events.PlayerClaimPlotEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.events.PlayerEnterPlotEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.events.PlayerLeavePlotEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.events.PlayerPlotDeniedEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.events.PlayerPlotHelperEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.events.PlayerPlotTrustedEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.events.PlayerTeleportToPlotEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.events.PlotClearEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.events.PlotComponentSetEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.events.PlotDeleteEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.events.PlotFlagAddEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.events.PlotFlagRemoveEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.events.PlotMergeEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.events.PlotRateEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.events.PlotUnlinkEvent;
import com.github.intellectualcrafters.plotsquared.bukkit.object.BukkitPlayer;
import com.github.intellectualcrafters.plotsquared.plot.flag.Flag;
import com.github.intellectualcrafters.plotsquared.plot.object.Location;
import com.github.intellectualcrafters.plotsquared.plot.object.Plot;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotArea;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotCluster;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotId;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.object.Rating;
import com.github.intellectualcrafters.plotsquared.plot.util.EventUtil;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class BukkitEventUtil extends EventUtil {

  public Player getPlayer(PlotPlayer player) {
    if (player instanceof BukkitPlayer) {
      return ((BukkitPlayer) player).player;
    }
    return null;
  }

  public boolean callEvent(Event event) {
    Bukkit.getServer().getPluginManager().callEvent(event);
    return !(event instanceof Cancellable) || !((Cancellable) event).isCancelled();
  }

  @Override
  public boolean callClaim(PlotPlayer player, Plot plot, boolean auto) {
    return callEvent(new PlayerClaimPlotEvent(getPlayer(player), plot, auto));
  }

  @Override
  public boolean callTeleport(PlotPlayer player, Location from, Plot plot) {
    return callEvent(new PlayerTeleportToPlotEvent(getPlayer(player), from, plot));
  }

  @Override
  public boolean callComponentSet(Plot plot, String component) {
    return callEvent(new PlotComponentSetEvent(plot, component));
  }

  @Override
  public boolean callClear(Plot plot) {
    return callEvent(new PlotClearEvent(plot));
  }

  @Override
  public void callDelete(Plot plot) {
    callEvent(new PlotDeleteEvent(plot));
  }

  @Override
  public boolean callFlagAdd(Flag flag, Plot plot) {
    return callEvent(new PlotFlagAddEvent(flag, plot));
  }

  @Override
  public boolean callFlagRemove(Flag<?> flag, Plot plot, Object value) {
    return callEvent(new PlotFlagRemoveEvent(flag, plot));
  }

  @Override
  public boolean callMerge(Plot plot, ArrayList<PlotId> plots) {
    return callEvent(new PlotMergeEvent(BukkitUtil.getWorld(plot.getWorldName()), plot, plots));
  }

  @Override
  public boolean callUnlink(PlotArea area, ArrayList<PlotId> plots) {
    return callEvent(new PlotUnlinkEvent(BukkitUtil.getWorld(area.worldname), area, plots));
  }

  @Override
  public void callEntry(PlotPlayer player, Plot plot) {
    callEvent(new PlayerEnterPlotEvent(getPlayer(player), plot));
  }

  @Override
  public void callLeave(PlotPlayer player, Plot plot) {
    callEvent(new PlayerLeavePlotEvent(getPlayer(player), plot));
  }

  @Override
  public void callDenied(PlotPlayer initiator, Plot plot, UUID player, boolean added) {
    callEvent(new PlayerPlotDeniedEvent(getPlayer(initiator), plot, player, added));
  }

  @Override
  public void callTrusted(PlotPlayer initiator, Plot plot, UUID player, boolean added) {
    callEvent(new PlayerPlotTrustedEvent(getPlayer(initiator), plot, player, added));
  }

  @Override
  public void callMember(PlotPlayer initiator, Plot plot, UUID player, boolean added) {
    callEvent(new PlayerPlotHelperEvent(getPlayer(initiator), plot, player, added));
  }

  @Override
  public boolean callFlagRemove(Flag flag, Object object, PlotCluster cluster) {
    return callEvent(new ClusterFlagRemoveEvent(flag, cluster));
  }

  @Override
  public Rating callRating(PlotPlayer player, Plot plot, Rating rating) {
    PlotRateEvent event = new PlotRateEvent(player, rating, plot);
    Bukkit.getServer().getPluginManager().callEvent(event);
    return event.getRating();
  }

}
