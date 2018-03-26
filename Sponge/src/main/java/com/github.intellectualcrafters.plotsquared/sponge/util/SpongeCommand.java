package com.github.intellectualcrafters.plotsquared.sponge.util;

import com.github.intellectualcrafters.plotsquared.plot.commands.MainCommand;
import com.github.intellectualcrafters.plotsquared.plot.object.ConsolePlayer;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.TaskManager;
import com.github.intellectualcrafters.plotsquared.sponge.SpongeMain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class SpongeCommand implements CommandCallable {

  @Override
  public CommandResult process(CommandSource source, String arguments) throws CommandException {
    TaskManager.runTask(() -> {
      String id = source.getIdentifier();
      PlotPlayer plotPlayer = null;
      try {
        UUID uuid = UUID.fromString(id);

        Optional<Player> player = SpongeMain.THIS.getServer().getPlayer(uuid);
        if (player.isPresent()) {
          plotPlayer = SpongeUtil.getPlayer(player.get());
        }
      } catch (Exception ignored) {
        plotPlayer = ConsolePlayer.getConsole();
      }
      MainCommand
          .onCommand(plotPlayer, arguments.isEmpty() ? new String[]{} : arguments.split(" "));
    });
    return CommandResult.success();
  }

  @Override
  public List<String> getSuggestions(CommandSource source, String arguments,
      Location<World> targetPosition)
      throws CommandException {
    if (!(source instanceof Player)) {
      return null;
    }
    PlotPlayer player = SpongeUtil.getPlayer((Player) source);
    String[] args = arguments.split(" ");
    if (args.length == 0) {
      return Collections.singletonList(MainCommand.getInstance().toString());
    }
    Collection objects = MainCommand.getInstance().tab(player, args, arguments.endsWith(" "));
    if (objects == null) {
      return null;
    }
    List<String> result = new ArrayList<>();
    for (Object o : objects) {
      result.add(o.toString());
    }
    return result.isEmpty() ? null : result;
  }

  @Override
  public boolean testPermission(CommandSource source) {
    return true;
  }

  @Override
  public Optional<Text> getShortDescription(CommandSource source) {
    return Optional.of(Text.of("Shows plot help"));
  }

  @Override
  public Optional<Text> getHelp(CommandSource source) {
    return Optional.of(Text.of("/plot"));
  }

  @Override
  public Text getUsage(CommandSource source) {
    return Text.of("/plot <command>");
  }

}
