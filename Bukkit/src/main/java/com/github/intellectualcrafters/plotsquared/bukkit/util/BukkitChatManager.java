package com.github.intellectualcrafters.plotsquared.bukkit.util;

import com.github.intellectualcrafters.plotsquared.bukkit.chat.FancyMessage;
import com.github.intellectualcrafters.plotsquared.bukkit.object.BukkitPlayer;
import com.github.intellectualcrafters.plotsquared.plot.config.C;
import com.github.intellectualcrafters.plotsquared.plot.config.Settings;
import com.github.intellectualcrafters.plotsquared.plot.object.ConsolePlayer;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotMessage;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.ChatManager;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;

public class BukkitChatManager extends ChatManager<FancyMessage> {

  @Override
  public FancyMessage builder() {
    return new FancyMessage("");
  }

  @Override
  public void color(PlotMessage message, String color) {
    message.$(this).color(ChatColor.getByChar(C.color(color).substring(1)));
  }

  @Override
  public void tooltip(PlotMessage message, PlotMessage... tooltips) {
    List<FancyMessage> lines = new ArrayList<>();
    for (PlotMessage tooltip : tooltips) {
      lines.add(tooltip.$(this));
    }
    message.$(this).formattedTooltip(lines);
  }

  @Override
  public void command(PlotMessage message, String command) {
    message.$(this).command(command);
  }

  @Override
  public void text(PlotMessage message, String text) {
    message.$(this).then(ChatColor.stripColor(text));
  }

  @Override
  public void send(PlotMessage plotMessage, PlotPlayer player) {
    if (player instanceof ConsolePlayer || !Settings.Chat.INTERACTIVE) {
      player.sendMessage(plotMessage.$(this).toOldMessageFormat());
    } else {
      plotMessage.$(this).send(((BukkitPlayer) player).player);
    }
  }

  @Override
  public void suggest(PlotMessage plotMessage, String command) {
    plotMessage.$(this).suggest(command);
  }

}
