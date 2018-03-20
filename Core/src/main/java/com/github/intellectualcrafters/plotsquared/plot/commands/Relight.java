package com.github.intellectualcrafters.plotsquared.plot.commands;

import com.github.intellectualcrafters.plotsquared.commands.Command;
import com.github.intellectualcrafters.plotsquared.commands.CommandDeclaration;
import com.github.intellectualcrafters.plotsquared.plot.config.C;
import com.github.intellectualcrafters.plotsquared.plot.object.Plot;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.object.RegionWrapper;
import com.github.intellectualcrafters.plotsquared.plot.object.RunnableVal;
import com.github.intellectualcrafters.plotsquared.plot.object.RunnableVal2;
import com.github.intellectualcrafters.plotsquared.plot.object.RunnableVal3;
import com.github.intellectualcrafters.plotsquared.plot.util.ChunkManager;
import com.github.intellectualcrafters.plotsquared.plot.util.block.LocalBlockQueue;
import java.util.HashSet;

@CommandDeclaration(command = "relight", description = "Relight your plot", category = CommandCategory.DEBUG)
public class Relight extends Command {

  public Relight() {
    super(MainCommand.getInstance(), true);
  }

  @Override
  public void execute(final PlotPlayer player, String[] args,
      RunnableVal3<Command, Runnable, Runnable> confirm,
      RunnableVal2<Command, CommandResult> whenDone) {
    final Plot plot = player.getCurrentPlot();
    if (plot == null) {
      C.NOT_IN_PLOT.send(player);
      return;
    }
    HashSet<RegionWrapper> regions = plot.getRegions();
    final LocalBlockQueue queue = plot.getArea().getQueue(false);
    ChunkManager.chunkTask(plot, new RunnableVal<int[]>() {
      @Override
      public void run(int[] value) {
        queue.fixChunkLighting(value[0], value[1]);
      }
    }, new Runnable() {
      @Override
      public void run() {
        plot.refreshChunks();
        C.SET_BLOCK_ACTION_FINISHED.send(player);
      }
    }, 5);
  }
}
