package com.intellectualcrafters.plot.commands;

import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.config.Settings;
import com.intellectualcrafters.plot.database.DBFunc;
import com.intellectualcrafters.plot.object.Expression;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotArea;
import com.intellectualcrafters.plot.object.PlotId;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.object.RunnableVal;
import com.intellectualcrafters.plot.util.ByteArrayUtilities;
import com.intellectualcrafters.plot.util.EconHandler;
import com.intellectualcrafters.plot.util.MainUtil;
import com.intellectualcrafters.plot.util.Permissions;
import com.intellectualcrafters.plot.util.TaskManager;
import com.plotsquared.general.commands.CommandDeclaration;
import org.bukkit.Bukkit;

import java.util.Set;

@CommandDeclaration(command = "auto",
        permission = "plots.auto",
        category = CommandCategory.CLAIMING,
        requiredType = RequiredType.NONE,
        description = "Claim the nearest plot",
        aliases = "a",
        usage = "/plot auto [length,width]")
public class Auto extends SubCommand {

    @Deprecated
    public static PlotId getNextPlotId(PlotId id, int step) {
        return id.getNextId(step);
    }

    @Override
    public boolean onCommand(final PlotPlayer player, String[] args) {
        Bukkit.getLogger().info("Called plot auto command.");
        PlotArea plotarea = player.getApplicablePlotArea();
        if (plotarea == null) {
            Bukkit.getLogger().info("Plot area is null.");
            if (EconHandler.manager != null) {
                Bukkit.getLogger().info("Econ handler is not null.");
                for (PlotArea area : PS.get().getPlotAreaManager().getAllPlotAreas()) {
                    Bukkit.getLogger().info("Looping through plot area...");
                    if (EconHandler.manager.hasPermission(area.worldname, player.getName(), "plots.auto")) {
                        Bukkit.getLogger().info("Has permission.");
                        if (plotarea != null) {
                            Bukkit.getLogger().info("Plot area is not null setting to null and breaking...");
                            plotarea = null;
                            break;
                        }
                        Bukkit.getLogger().info("Setting plot area...");
                        plotarea = area;
                    }
                }
            }
            if (plotarea == null) {
                Bukkit.getLogger().info("Plot area is null. (2)");
                MainUtil.sendMessage(player, C.NOT_IN_PLOT_WORLD);
                return false;
            }
        }
        int size_x = 1;
        int size_z = 1;
        String schematic = null;
        if (args.length > 0) {
            Bukkit.getLogger().info("Args length > 0");
            if (Permissions.hasPermission(player, C.PERMISSION_AUTO_MEGA)) {
                Bukkit.getLogger().info("Has permission. (2)");
                try {
                    String[] split = args[0].split(",|;");
                    size_x = Integer.parseInt(split[0]);
                    size_z = Integer.parseInt(split[1]);
                    if (size_x < 1 || size_z < 1) {
                        MainUtil.sendMessage(player, "&cError: size<=0");
                    }
                    if (args.length > 1) {
                        schematic = args[1];
                    }
                    Bukkit.getLogger().info("Tried.");
                } catch (NumberFormatException ignored) {
                    size_x = 1;
                    size_z = 1;
                    schematic = args[0];
                    Bukkit.getLogger().info("Caught.");
                    // PlayerFunctions.sendMessage(plr,
                    // "&cError: Invalid size (X,Y)");
                    // return false;
                }
                Bukkit.getLogger().info("Finished try/catch.");
            } else {
                Bukkit.getLogger().info("Does not have permission.");
                schematic = args[0];
                // PlayerFunctions.sendMessage(plr, C.NO_PERMISSION);
                // return false;
            }
        }
        if (size_x * size_z > Settings.Claim.MAX_AUTO_AREA) {
            Bukkit.getLogger().info("Something happened here. (1)");
            MainUtil.sendMessage(player, C.CANT_CLAIM_MORE_PLOTS_NUM, Settings.Claim.MAX_AUTO_AREA + "");
            return false;
        }
        int currentPlots = Settings.Limit.GLOBAL ? player.getPlotCount() : player.getPlotCount(plotarea.worldname);
        int diff = currentPlots - player.getAllowedPlots();
        if (diff + size_x * size_z > 0) {
            Bukkit.getLogger().info("Something happened here. (2)");
            if (diff < 0) {
                Bukkit.getLogger().info("Something happened here. (3)");
                MainUtil.sendMessage(player, C.CANT_CLAIM_MORE_PLOTS_NUM, -diff + "");
                return false;
            } else if (player.hasPersistentMeta("grantedPlots")) {
                Bukkit.getLogger().info("Something happened here. (4)");
                int grantedPlots = ByteArrayUtilities.bytesToInteger(player.getPersistentMeta("grantedPlots"));
                if (grantedPlots - diff < size_x * size_z) {
                    Bukkit.getLogger().info("Something happened here. (5)");
                    player.removePersistentMeta("grantedPlots");
                    return sendMessage(player, C.CANT_CLAIM_MORE_PLOTS);
                } else {
                    Bukkit.getLogger().info("Something happened here. (6)");
                    int left = grantedPlots - diff - size_x * size_z;
                    if (left == 0) {
                        Bukkit.getLogger().info("Something happened here. (7)");
                        player.removePersistentMeta("grantedPlots");
                    } else {
                        Bukkit.getLogger().info("Something happened here. (8)");
                        player.setPersistentMeta("grantedPlots", ByteArrayUtilities.integerToBytes(left));
                    }
                    sendMessage(player, C.REMOVED_GRANTED_PLOT, "" + left, "" + (grantedPlots - left));
                }
            } else {
                Bukkit.getLogger().info("Something happened here. (9)");
                MainUtil.sendMessage(player, C.CANT_CLAIM_MORE_PLOTS);
                return false;
            }
        }
        if (schematic != null && !schematic.isEmpty()) {
            Bukkit.getLogger().info("Something happened here. (10)");
            if (!plotarea.SCHEMATICS.contains(schematic.toLowerCase())) {
                Bukkit.getLogger().info("Something happened here. (11)");
                sendMessage(player, C.SCHEMATIC_INVALID, "non-existent: " + schematic);
                return true;
            }
            if (!Permissions.hasPermission(player, C.PERMISSION_CLAIM_SCHEMATIC.f(schematic)) && !Permissions.hasPermission(player, C.PERMISSION_ADMIN_COMMAND_SCHEMATIC)) {
                Bukkit.getLogger().info("Something happened here. (12)");
                MainUtil.sendMessage(player, C.NO_PERMISSION, C.PERMISSION_CLAIM_SCHEMATIC.f(schematic));
                return true;
            }
        }
        if (EconHandler.manager != null && plotarea.USE_ECONOMY) {
            Bukkit.getLogger().info("Something happened here. (13)");
            Expression<Double> costExp = plotarea.PRICES.get("claim");
            double cost = costExp.evaluate((double) currentPlots);
            cost = (size_x * size_z) * cost;
            if (cost > 0d) {
                Bukkit.getLogger().info("Something happened here. (14)");
                if (EconHandler.manager.getMoney(player) < cost) {
                    Bukkit.getLogger().info("Something happened here. (15)");
                    sendMessage(player, C.CANNOT_AFFORD_PLOT, "" + cost);
                    return true;
                }
                EconHandler.manager.withdrawMoney(player, cost);
                sendMessage(player, C.REMOVED_BALANCE, cost + "");
            }
        }
        // TODO handle type 2 the same as normal worlds!
        if (size_x == 1 && size_z == 1) {
            Bukkit.getLogger().info("Something happened here. (16)");
            autoClaimSafe(player, plotarea, null, schematic);
            return true;
        } else {
            Bukkit.getLogger().info("Something happened here. (17)");
            if (plotarea.TYPE == 2) {
                Bukkit.getLogger().info("Something happened here. (18)");
                // TODO
                MainUtil.sendMessage(player, C.NO_FREE_PLOTS);
                return false;
            }
            while (true) {
                Bukkit.getLogger().info("While true...");
                PlotId start = plotarea.getMeta("lastPlot", new PlotId(0, 0)).getNextId(1);
                PlotId end = new PlotId(start.x + size_x - 1, start.y + size_z - 1);
                if (plotarea.canClaim(player, start, end)) {
                    Bukkit.getLogger().info("Something happened while true...");
                    plotarea.setMeta("lastPlot", start);
                    for (int i = start.x; i <= end.x; i++) {
                        for (int j = start.y; j <= end.y; j++) {
                            Plot plot = plotarea.getPlotAbs(new PlotId(i, j));
                            boolean teleport = i == end.x && j == end.y;
                            plot.claim(player, teleport, null);
                        }
                    }
                    if (!plotarea.mergePlots(MainUtil.getPlotSelectionIds(start, end), true, true)) {
                        Bukkit.getLogger().info("Something happened here. (19)");
                        return false;
                    }
                    break;
                }
                Bukkit.getLogger().info("Set meta.");
                plotarea.setMeta("lastPlot", start);
            }
            return true;
        }
    }

    /**
     * Get the next plot id (spiral out from 0,0)
     * @param start
     * @return
     */
    @Deprecated
    public static PlotId getNextPlot(PlotId start) {
        Bukkit.getLogger().info("Getting next plot...");
        int plots;
        PlotId center;
        center = new PlotId(0, 0);
        plots = Integer.MAX_VALUE;
        PlotId currentId;
        for (int i = 0; i < plots; i++) {
            if (start == null) {
                Bukkit.getLogger().info("Something NEW happened here. (1)");
                start = new PlotId(0, 0);
            } else {
                Bukkit.getLogger().info("Something NEW happened here. (2)");
                start = start.getNextId(1);
            }
            currentId = new PlotId(center.x + start.x, center.y + start.y);
            Bukkit.getLogger().info("Returning a value...");
            return currentId;
        }
        Bukkit.getLogger().info("Returning null...");
        return null;
    }

    /**
     * Teleport the player home, or claim a new plot
     * @param player
     * @param area
     * @param start
     * @param schem
     */
    public static void homeOrAuto(final PlotPlayer player, final PlotArea area, PlotId start, final String schem) {
        Bukkit.getLogger().info("Calling home or auto...");
        Set<Plot> plots = player.getPlots();
        if (!plots.isEmpty()) {
            Bukkit.getLogger().info("Plots is not empty...");
            plots.iterator().next().teleportPlayer(player);
        } else {
            Bukkit.getLogger().info("Plots is empty...");
            autoClaimSafe(player, area, start, schem);
        }
    }

    /**
     * Claim a new plot for a player
     * @param player
     * @param area
     * @param start
     * @param schem
     */
    public static void autoClaimSafe(final PlotPlayer player, final PlotArea area, PlotId start, final String schem) {
        Bukkit.getLogger().info("Auto claiming safe");
        autoClaimFromDatabase(player, area, start, new RunnableVal<Plot>() {
            @Override
            public void run(final Plot plot) {
                Bukkit.getLogger().info("Running first...");
                TaskManager.IMP.sync(new RunnableVal<Object>() {
                    @Override
                    public void run(Object ignore) {
                        Bukkit.getLogger().info("Running second...");
                        if (plot == null) {
                            Bukkit.getLogger().info("Plot is null...");
                            MainUtil.sendMessage(player, C.NO_FREE_PLOTS);
                        } else {
                            Bukkit.getLogger().info("Plot is not null...");
                            plot.claim(player, true, schem, false);
                            if (area.AUTO_MERGE) {
                                Bukkit.getLogger().info("Auto merging...");
                                plot.autoMerge(-1, Integer.MAX_VALUE, player.getUUID(), true);
                            }
                        }
                    }
                });
            }
        });
    }

    public static void autoClaimFromDatabase(final PlotPlayer player, final PlotArea area, PlotId start, final RunnableVal<Plot> whenDone) {
        Bukkit.getLogger().info("Auto claiming from database");
        final Plot plot = area.getNextFreePlot(player, start);
        if (plot == null) {
            Bukkit.getLogger().info("Plot is null");
            whenDone.run(null);
            return;
        }
        Bukkit.getLogger().info("Plot is not null");
        whenDone.value = plot;
        plot.owner = player.getUUID();
        Bukkit.getLogger().info("Creating plot safe...");
        DBFunc.createPlotSafe(plot, whenDone, new Runnable() {
            @Override
            public void run() {
                Bukkit.getLogger().info("Got here...");
                autoClaimFromDatabase(player, area, plot.getId(), whenDone);
            }
        });
    }
}
