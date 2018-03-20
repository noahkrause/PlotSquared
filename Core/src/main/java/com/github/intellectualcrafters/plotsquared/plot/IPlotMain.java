package com.github.intellectualcrafters.plotsquared.plot;

import com.github.intellectualcrafters.plotsquared.plot.generator.GeneratorWrapper;
import com.github.intellectualcrafters.plotsquared.plot.generator.HybridUtils;
import com.github.intellectualcrafters.plotsquared.plot.generator.IndependentPlotGenerator;
import com.github.intellectualcrafters.plotsquared.plot.logger.ILogger;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.AbstractTitle;
import com.github.intellectualcrafters.plotsquared.plot.util.ChatManager;
import com.github.intellectualcrafters.plotsquared.plot.util.ChunkManager;
import com.github.intellectualcrafters.plotsquared.plot.util.EconHandler;
import com.github.intellectualcrafters.plotsquared.plot.util.EventUtil;
import com.github.intellectualcrafters.plotsquared.plot.util.InventoryUtil;
import com.github.intellectualcrafters.plotsquared.plot.util.SchematicHandler;
import com.github.intellectualcrafters.plotsquared.plot.util.SetupUtils;
import com.github.intellectualcrafters.plotsquared.plot.util.TaskManager;
import com.github.intellectualcrafters.plotsquared.plot.util.UUIDHandlerImplementation;
import com.github.intellectualcrafters.plotsquared.plot.util.WorldUtil;
import com.github.intellectualcrafters.plotsquared.plot.util.block.QueueProvider;
import java.io.File;
import java.util.List;

public interface IPlotMain extends ILogger {

  /**
   * Log a message to console.
   *
   * @param message The message to log
   */
  void log(String message);

  /**
   * Get the `PlotSquared` directory.
   *
   * @return The plugin directory
   */
  File getDirectory();

  /**
   * Get the directory containing all the worlds.
   *
   * @return The directory containing the worlds
   */
  File getWorldContainer();

  /**
   * Wrap a player into a PlotPlayer object.
   *
   * @param player The player to convert to a PlotPlayer
   * @return A PlotPlayer
   */
  PlotPlayer wrapPlayer(Object player);

  /**
   * Disable the implementation.
   *
   * <ul> <li>If a full disable isn't feasibly, just disable what it can. </ul>
   */
  void disable();

  /**
   * Get the version of the PlotSquared being used.
   *
   * @return the plugin version
   */
  int[] getPluginVersion();

  /**
   * Get the version of the PlotSquared being used as a string.
   *
   * @return the plugin version as a string
   */
  String getPluginVersionString();

  /**
   * Usually PlotSquared
   */
  String getPluginName();

  /**
   * Get the version of Minecraft that is running.
   */
  int[] getServerVersion();

  /**
   * Get the NMS package prefix.
   *
   * @return The NMS package prefix
   */
  String getNMSPackage();

  /**
   * Get the schematic handler.
   *
   * @return The {@link SchematicHandler}
   */
  SchematicHandler initSchematicHandler();

  /**
   * Get the Chat Manager.
   *
   * @return The {@link ChatManager}
   */
  ChatManager initChatManager();

  /**
   * The task manager will run and manage Minecraft tasks.
   */
  TaskManager getTaskManager();

  /**
   * Run the task that will kill road mobs.
   */
  void runEntityTask();

  /**
   * Register the implementation specific commands.
   */
  void registerCommands();

  /**
   * Register the protection system.
   */
  void registerPlayerEvents();

  /**
   * Register inventory related events.
   */
  void registerInventoryEvents();

  /**
   * Register plot plus related events.
   */
  void registerPlotPlusEvents();

  /**
   * Register force field events.
   */
  void registerForceFieldEvents();

  /**
   * Register the WorldEdit hook.
   */
  boolean initWorldEdit();

  /**
   * Get the economy provider.
   */
  EconHandler getEconomyHandler();

  /**
   * Get the {@link QueueProvider} class.
   */
  QueueProvider initBlockQueue();

  /**
   * Get the {@link WorldUtil} class.
   */
  WorldUtil initWorldUtil();

  /**
   * Get the EventUtil class.
   */
  EventUtil initEventUtil();

  /**
   * Get the chunk manager.
   */
  ChunkManager initChunkManager();

  /**
   * Get the {@link SetupUtils} class.
   */
  SetupUtils initSetupUtils();

  /**
   * Get {@link HybridUtils} class.
   */
  HybridUtils initHybridUtils();

  /**
   * Start Metrics.
   */
  void startMetrics();

  /**
   * If a world is already loaded, set the generator (use NMS if required).
   *
   * @param world The world to set the generator
   */
  void setGenerator(String world);

  /**
   * Get the {@link UUIDHandlerImplementation} which will cache and provide UUIDs.
   */
  UUIDHandlerImplementation initUUIDHandler();

  /**
   * Get the {@link InventoryUtil} class (used for implementation specific inventory guis).
   */
  InventoryUtil initInventoryUtil();

  /**
   * Run the converter for the implementation (not necessarily PlotMe, just any plugin that we can
   * convert from).
   */
  boolean initPlotMeConverter();

  /**
   * Unregister a PlotPlayer from cache e.g. if they have logged off.
   */
  void unregister(PlotPlayer player);

  /**
   * Get the generator wrapper for a world (world) and generator (name).
   */
  GeneratorWrapper<?> getGenerator(String world, String name);

  GeneratorWrapper<?> wrapPlotGenerator(String world, IndependentPlotGenerator generator);

  /**
   * Register the chunk processor which will clean out chunks that have too many blockstates or
   * entities.
   */
  void registerChunkProcessor();

  /**
   * Register the world initialization events (used to keep track of worlds being generated).
   */
  void registerWorldEvents();

  /**
   * Usually HybridGen
   *
   * @return Default implementation generator
   */
  IndependentPlotGenerator getDefaultGenerator();

  /**
   * Get the class that will manage player titles.
   */
  AbstractTitle initTitleManager();

  List<String> getPluginIds();
}
