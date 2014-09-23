/*
 * Copyright (c) IntellectualCrafters - 2014.
 * You are not allowed to distribute and/or monetize any of our intellectual property.
 * IntellectualCrafters is not affiliated with Mojang AB. Minecraft is a trademark of Mojang AB.
 *
 * >> File = PlotHelper.java
 * >> Generated by: Citymonstret at 2014-08-09 01:43
 */

package com.intellectualcrafters.plot;

import com.intellectualcrafters.plot.database.DBFunc;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.*;

import static com.intellectualcrafters.plot.Settings.*;

/**
 * plot functions
 * @author Citymonstret
 *
 */
public class PlotHelper {

    private static double calculateNeededTime(double blocks, double blocks_per_second) {
        return (blocks / blocks_per_second);
    }

    public static Short[] getRandom(Random random, Object[] filling) {
        int len= ((Short[]) filling[0]).length; 
        if (len==1) {
            return new Short[] {((Short[]) filling[0])[0],((Short[]) filling[1])[0]};
        }
        int index = random.nextInt(len);
        return new Short[] {((Short[]) filling[0])[index],((Short[]) filling[1])[index]};
    }

    public static void removeSign(Player plr, Plot p) {
        PlotWorld plotworld = PlotMain.getWorldSettings(Bukkit.getWorld(p.world));
        Location pl = new Location(plr.getWorld(), getPlotBottomLoc(plr.getWorld(), p.id).getBlockX() , plotworld.ROAD_HEIGHT + 1, getPlotBottomLoc(plr.getWorld(), p.id).getBlockZ());
        Block bs = pl.add(0,0,-1).getBlock();
        bs.setType(Material.AIR);
    }

    @SuppressWarnings("deprecation")
    public static void setSign(Player plr, Plot p) {
        World world = Bukkit.getWorld(p.world);
        PlotWorld plotworld = PlotMain.getWorldSettings(world);
        Location pl = new Location(world, getPlotBottomLoc(world, p.id).getBlockX() , plotworld.ROAD_HEIGHT + 1, getPlotBottomLoc(world, p.id).getBlockZ());
        Block bs = pl.add(0,0,-1).getBlock();
        bs.setType(Material.AIR);
        bs.setTypeIdAndData(Material.WALL_SIGN.getId(), (byte) 2, false);
        String id = p.id.y+";"+p.id.x;
        Sign sign = (Sign) bs.getState();
        sign.setLine(0, C.OWNER_SIGN_LINE_1.translated().replaceAll("%id%", id));
        sign.setLine(1, C.OWNER_SIGN_LINE_2.translated().replaceAll("%id%", id).replaceAll("%plr%", plr.getName()));
        sign.setLine(2, C.OWNER_SIGN_LINE_3.translated().replaceAll("%id%", id).replaceAll("%plr%", plr.getName()));
        sign.setLine(3, C.OWNER_SIGN_LINE_4.translated().replaceAll("%id%", id).replaceAll("%plr%", plr.getName()));
        sign.update(true);
    }

    public static String getPlayerName(UUID uuid) {
        if(uuid == null) return "unknown";
        OfflinePlayer plr = Bukkit.getOfflinePlayer(uuid);
        if(plr == null) return "unknown";
        return plr.getName();
    }

    public static String getStringSized(int max, String string) {
        if(string.length() > max) return string.substring(0,max);
        return string;
    }

    public static void adjustWall(World w, Plot plot, short id, byte data) {
        
        PlotWorld plotworld = PlotMain.getWorldSettings(Bukkit.getWorld(plot.world));

        List<String> wallIds = new ArrayList<String>();

        wallIds.add("" + id + ":" + data);

        Location bottom = getPlotBottomLoc(w, plot.id);
        Location top = getPlotTopLoc(w, plot.id);

        int x, z;

        Block block;

        for (x = bottom.getBlockX(); x < top.getBlockX() + 1; x++) {
            z = bottom.getBlockZ();

            block = w.getBlockAt(x, plotworld.ROAD_HEIGHT + 1, z);
            setWall(block, "" + id + ":" + data);
        }

        for (z = bottom.getBlockZ(); z < top.getBlockZ() + 1; z++) {
            x = top.getBlockX() + 1;

            block = w.getBlockAt(x, plotworld.ROAD_HEIGHT + 1, z);
            setWall(block, "" + id + ":" + data);
        }

        for (x = top.getBlockX() + 1; x > bottom.getBlockX() - 1; x--) {
            z = top.getBlockZ() + 1;

            block = w.getBlockAt(x, plotworld.ROAD_HEIGHT + 1, z);
            setWall(block, "" + id + ":" + data);
        }

        for (z = top.getBlockZ() + 1; z > bottom.getBlockZ() - 1; z--) {
            x = bottom.getBlockX();
            block = w.getBlockAt(x, plotworld.ROAD_HEIGHT + 1, z);
            setWall(block, "" + id + ":" + data);
        }
    }

    public static boolean createPlot(Player player, Plot plot)  {
        @SuppressWarnings("deprecation")
        World w = plot.getWorld();
        Plot p = new Plot(plot.id, player.getUniqueId(), plot.settings.getBiome(), null, null, w.getName());
        PlotMain.updatePlot(p);
        DBFunc.createPlot(p);
        DBFunc.createPlotSettings(DBFunc.getId(w.getName(),p.id), p);
        return true;
    }

    public static int getLoadedChunks(World world) {
        return world.getLoadedChunks().length;
    }

    public static int getEntities(World world) {
        return world.getEntities().size();
    }

    public static int getTileEntities(World world) {
        PlotWorld plotworld = PlotMain.getWorldSettings(world);
        int x = 0;
        for(Chunk chunk : world.getLoadedChunks()) {
            x += chunk.getTileEntities().length;
        }
        return x;
    }

    public static double getWorldFolderSize(){
        //long size = FileUtils.sizeOfDirectory(Bukkit.getWorld(Settings.PLOT_WORLD).getWorldFolder());
        long size = 10;
        return (((size) / 1024) / 1024);
    }

//	public static void adjustLinkedPlots(String id) {
//		World world = Bukkit.getWorld(Settings.PLOT_WORLD);
//		int x = getIdX(id);
//		int z = getIdZ(id);
//
//		plot p11 = getPlot(id);
//		if (p11 != null) {
//			plot p01, p10, p12, p21, p00, p02, p20, p22;
//			p01 = getPlot(x - 1, z);
//			p10 = getPlot(x, z - 1);
//			p12 = getPlot(x, z + 1);
//			p21 = getPlot(x + 1, z);
//			p00 = getPlot(x - 1, z - 1);
//			p02 = getPlot(x - 1, z + 1);
//			p20 = getPlot(x + 1, z - 1);
//			p22 = getPlot(x + 1, z + 1);
//			if (p01 != null && p01.owner.equals(p11.owner)) {
//				fillroad(p01, p11, world);
//			}
//
//			if (p10 != null && p10.owner.equals(p11.owner)) {
//				fillroad(p10, p11, world);
//			}
//
//			if (p12 != null && p12.owner.equals(p11.owner)) {
//				fillroad(p12, p11, world);
//			}
//
//			if (p21 != null && p21.owner.equals(p11.owner)) {
//				fillroad(p21, p11, world);
//			}
//
//			if (p00 != null && p10 != null && p01 != null
//					&& p00.owner.equals(p11.owner)
//					&& p11.owner.equals(p10.owner)
//					&& p10.owner.equals(p01.owner)) {
//				fillmiddleroad(p00, p11, world);
//			}
//
//			if (p10 != null && p20 != null && p21 != null
//					&& p10.owner.equals(p11.owner)
//					&& p11.owner.equals(p20.owner)
//					&& p20.owner.equals(p21.owner)) {
//				fillmiddleroad(p20, p11, world);
//			}
//
//			if (p01 != null && p02 != null && p12 != null
//					&& p01.owner.equals(p11.owner)
//					&& p11.owner.equals(p02.owner)
//					&& p02.owner.equals(p12.owner)) {
//				fillmiddleroad(p02, p11, world);
//			}
//
//			if (p12 != null && p21 != null && p22 != null
//					&& p12.owner.equals(p11.owner)
//					&& p11.owner.equals(p21.owner)
//					&& p21.owner.equals(p22.owner)) {
//				fillmiddleroad(p22, p11, world);
//			}
//		}
//	}
//
//	public static void fillroad(plot plot1, plot plot2, World w) {
//		Location bottomPlot1, topPlot1, bottomPlot2, topPlot2;
//		bottomPlot1 = getPlotBottomLoc(w, plot1.id);
//		topPlot1 = getPlotTopLoc(w, plot1.id);
//		bottomPlot2 = getPlotBottomLoc(w, plot2.id);
//		topPlot2 = getPlotTopLoc(w, plot2.id);
//		
//		int minX, maxX, minZ, maxZ;
//		
//		boolean isWallX;
//		
//		int h = Settings.ROAD_HEIGHT;
//		int wallId = Settings.WALL_BLOCK;
//		int fillId = Settings.TOP_BLOCK;
//		
//		if(bottomPlot1.getBlockX() == bottomPlot2.getBlockX()) {
//			minX = bottomPlot1.getBlockX();
//			maxX = topPlot1.getBlockX();
//			
//			minZ = Math.min(bottomPlot1.getBlockZ(), bottomPlot2.getBlockZ()) + Settings.PLOT_WIDTH;
//			maxZ = Math.min(topPlot1.getBlockZ(), topPlot2.getBlockZ()) - Settings.PLOT_WIDTH;
//		} else {
//			minZ = bottomPlot1.getBlockZ();
//			maxZ = topPlot1.getBlockZ();
//
//			minX = Math.min(bottomPlot1.getBlockX(), bottomPlot2.getBlockX()) + Settings.PLOT_WIDTH;
//			maxX = Math.max(topPlot1.getBlockX(), topPlot2.getBlockX()) - Settings.PLOT_WIDTH;
//		}
//		
//		isWallX = (maxX - minX) > (maxZ - minZ);
//		
//		if(isWallX) {
//			minX--;
//			maxX++;
//		} else {
//			minZ--;
//			maxZ++;
//		}
//		
//		for(int x = minX; x <= maxX; x++) {
//			for(int z = minZ; x <= maxZ; z++) {
//				for(int y = h; y < h + 3; y++) {
//					if(y >= (h + 2)) {
//						w.getBlockAt(x,y,z).setType(Material.AIR);
//					} else if(y == (h + 1)) {
//						if(isWallX && (x == minX || x == maxX)) {
//							w.getBlockAt(x,y,z).setTypeIdAndData(wallId, (byte) 0, true);
//						} else if(!isWallX && (z == minZ || z == maxZ)) {
//							w.getBlockAt(x,y,z).setTypeIdAndData(wallId, (byte) 0, true);
//						} else {
//							w.getBlockAt(x,y,z).setType(Material.AIR);
//						}
//					} else {
//						w.getBlockAt(x,y,z).setTypeIdAndData(fillId, (byte) 0, true);
//					}
//				}
//			}
//		}
//	}
//	
//	public static void fillmiddleroad(plot p1, plot p2, World w) {
//		Location b1 = getPlotBottomLoc(w, p1.id);
//		Location t1 = getPlotTopLoc(w, p1.id);
//		Location b2 = getPlotBottomLoc(w, p2.id);
//		Location t2 = getPlotTopLoc(w, p2.id);
//		
//		int minX, maxX, minZ, maxZ;
//		
//		int h = Settings.ROAD_HEIGHT;
//		int fillID = Settings.TOP_BLOCK;
//		
//		minX = Math.min(t1.getBlockX(), t2.getBlockX());
//		maxX = Math.max(b1.getBlockX(), b2.getBlockX());
//		
//		minZ = Math.min(t1.getBlockZ(), t2.getBlockZ());
//		maxZ = Math.max(b1.getBlockZ(), b2.getBlockZ());
//		
//		for(int x = minX; x <= maxX; x++) {
//			for(int z = minZ; z <= maxZ; z++) {
//				for(int y = h; y < h + 3; y++) {
//					if(y >= (h + 1)) {
//						w.getBlockAt(x,y,z).setType(Material.AIR);
//					} else {
//						w.getBlockAt(x,y,z).setTypeId(fillID);
//					}
//				}
//			}
//		}
//	}

    public static String createId(int x, int z) {
        return x + ";" + z;
    }

    public static ArrayList<String> runners_p = new ArrayList<String>();
    public static HashMap<Plot, Integer> runners = new HashMap<Plot, Integer>();

    public static void adjustWallFilling(final Player requester, final World w, final Plot plot,
                                         final short id, final byte data) {
        if(runners.containsKey(plot)) {
            PlayerFunctions.sendMessage(requester, C.WAIT_FOR_TIMER);
            return;
        }
        PlayerFunctions.sendMessage(requester, C.GENERATING_WALL_FILLING);
        final PlotWorld plotworld = PlotMain.getWorldSettings(w);
        runners.put(
                plot,
                Bukkit.getScheduler().scheduleSyncRepeatingTask(PlotMain.getMain(),
                        new Runnable() {
                            Location bottom = getPlotBottomLoc(w, plot.id);
                            Location top = getPlotTopLoc(w, plot.id);
                            int y = plotworld.ROAD_HEIGHT;
                            int x, z;

                            @Override
                            public void run() {
                                for (x = bottom.getBlockX(); x < top
                                        .getBlockX() + 1; x++) {
                                    z = bottom.getBlockZ();
                                    setWall(w.getBlockAt(x, y, z), "" + id
                                            + ":" + data);
                                }

                                for (z = bottom.getBlockZ(); z < top
                                        .getBlockZ() + 1; z++) {
                                    x = top.getBlockX() + 1;
                                    setWall(w.getBlockAt(x, y, z), "" + id
                                            + ":" + data);
                                }

                                for (x = top.getBlockX() + 1; x > bottom
                                        .getBlockX() - 1; x--) {
                                    z = top.getBlockZ() + 1;
                                    setWall(w.getBlockAt(x, y, z), "" + id
                                            + ":" + data);
                                }

                                for (z = top.getBlockZ() + 1; z > bottom
                                        .getBlockZ() - 1; z--) {
                                    x = bottom.getBlockX();
                                    setWall(w.getBlockAt(x, y, z), "" + id
                                            + ":" + data);
                                }

                                y--;
                                if (y < 1) {
                                    int runner = runners.get(plot);
                                    runners.remove(plot);
                                    PlayerFunctions.sendMessage(requester, C.SET_BLOCK_ACTION_FINISHED);
                                    Bukkit.getScheduler().cancelTask(runner);
                                }
                            }
                        }, 0l, 5l));
    }

    public static void setFloor(final Player requester, final Plot plot, final Material material[], final byte data[]) {
        final PlotWorld plotworld = PlotMain.getWorldSettings(Bukkit.getWorld(plot.world));
        if(runners.containsKey(plot)) {
            PlayerFunctions.sendMessage(requester, C.WAIT_FOR_TIMER);
            return;
        }
        String time = RUtils.formatTime(calculateNeededTime(square(plotworld.PLOT_WIDTH), 2 * plotworld.PLOT_WIDTH));
        String send = C.GENERATING_FLOOR.s().replaceAll("%time%", time);
        PlayerFunctions.sendMessage(requester, send);
        runners.put(plot, Bukkit.getScheduler().scheduleSyncRepeatingTask(PlotMain.getMain(), new Runnable() {
            World world = Bukkit.getWorld(plot.world);
            int x1 = getPlotBottomLoc(world, plot.id).getBlockX();
            int x2 = x1 + plotworld.PLOT_WIDTH;
            int z1 = getPlotBottomLoc(world, plot.id).getBlockZ();
            int z2 = z1 + plotworld.PLOT_WIDTH;

            int xMin = Math.min(x1, x2) + 1;
            int xMax = Math.max(x1, x2);

            int zMin = Math.min(z1, z2) + 1;
            int zMax = Math.max(z1, z2);
            Random random = new Random();
            int x = xMin;
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                for(int z = zMin; z <= zMax; z++) {
                    int y = plotworld.PLOT_HEIGHT;
                    byte d;
                    short id;
                    if(material.length > 1) {
                        int index = random.nextInt(material.length);
                        d = data[index];
                        id = (short) material[index].getId();
                    } else {
                        d = data[0];
                        id = (short)material[0].getId();
                    }
                    world.getBlockAt(x, y, z).setTypeIdAndData(id,d, true);
                }
                x++;
                if(x > xMax) {
                    int runner = runners.get(plot);
                    runners.remove(plot);
                    PlayerFunctions.sendMessage(requester, C.SET_BLOCK_ACTION_FINISHED);
                    Bukkit.getScheduler().cancelTask(runner);
                }
            }

        },0l, 10l));
    }


    public static int square(int x) {
        return x * x;
    }

    public static int getPlotSize(World world) {
        PlotWorld plotworld = PlotMain.getWorldSettings(world);
        return (square(plotworld.PLOT_WIDTH)) * (world.getMaxHeight());
    }
    public static Short[] getBlock(String block) {
        if (block.contains(":")) {
            String[] split = block.split(":");
            return new Short[] {Short.parseShort(split[0]),Short.parseShort(split[1])};
        }
        return new Short[] {Short.parseShort(block),0};
    }

    public static void clear(final Player requester, final Plot plot) {
        PlotWorld plotworld = PlotMain.getWorldSettings(Bukkit.getWorld(plot.world));
        long start = System.nanoTime();
        PlotHelper.setBiome(requester.getWorld(), plot, Biome.FOREST);
        PlotHelper.removeSign(requester, plot);
        PlayerFunctions.sendMessage(requester, C.CLEARING_PLOT);
        World world = requester.getWorld();
        Location pos1 = getPlotBottomLoc(world, plot.id).add(1,0,1);
        Location pos2 = getPlotTopLoc(world, plot.id);
        SetBlockFast setBlockClass = null;
        
        Short[] plotfloors = new Short[plotworld.TOP_BLOCK.length];
        Short[] plotfloors_data = new Short[plotworld.TOP_BLOCK.length];
        
        Short[] filling = new Short[plotworld.MAIN_BLOCK.length];
        Short[] filling_data = new Short[plotworld.MAIN_BLOCK.length];
        
        for (int i = 0; i < plotworld.TOP_BLOCK.length; i++) {
            Short[] result = getBlock(plotworld.TOP_BLOCK[i]);
            plotfloors[i] = result[0];
            plotfloors_data[i] = result[1];
        }
        for (int i = 0; i < plotworld.MAIN_BLOCK.length; i++) {
            Short[] result = getBlock(plotworld.MAIN_BLOCK[i]);
            filling[i] = result[0];
            filling_data[i] = result[1];
        }
        
        
        try {
            setBlockClass = new SetBlockFast();
            regenerateCuboid(pos1, pos2,requester,plotworld, new Object[] {plotfloors,plotfloors_data}, new Object[] {filling, filling_data});
            PlayerFunctions.sendMessage(requester, C.CLEARING_DONE.s().replaceAll("%time%", ""+((System.nanoTime()-start)/1000000.0)));
            SetBlockFast.update(requester);
            PlayerFunctions.sendMessage(requester, C.CLEARING_DONE_PACKETS.s().replaceAll("%time%", ""+((System.nanoTime()-start)/1000000.0)));
            return;
        }
        catch (NoClassDefFoundError e) {
            PlotMain.sendConsoleSenderMessage(C.PREFIX.s() + "&cFast plot clearing is currently not enabled.");
            PlotMain.sendConsoleSenderMessage(C.PREFIX.s() + "&c - Please get PlotSquared for "+Bukkit.getVersion()+" for improved performance");
        }
        
        if (pos2.getBlockX()-pos1.getBlockX()<16) {
            regenerateCuboid(pos1, pos2,requester,plotworld, new Object[] {plotfloors,plotfloors_data}, new Object[] {filling, filling_data});
            return;
        }
        int startX = (pos1.getBlockX()/16)*16;
        int startZ = (pos1.getBlockZ()/16)*16;
        int chunkX = 16+pos2.getBlockX();
        int chunkZ = 16+pos2.getBlockZ();
        int plotMinX = getPlotBottomLoc(world,plot.id).getBlockX()+1;
        int plotMinZ = getPlotBottomLoc(world,plot.id).getBlockZ()+1;
        int plotMaxX = getPlotTopLoc(world,plot.id).getBlockX();
        int plotMaxZ = getPlotTopLoc(world,plot.id).getBlockZ();
        Location min = null;
        Location max = null;
        for (int i = startX; i<chunkX;i+=16) {
            for (int j = startZ; j<chunkZ;j+=16) {
                Plot plot1 = getCurrentPlot(new Location(world, i, 0, j));
                if (plot1!=null && plot1.getId()!=plot.getId() && plot1.hasOwner()) {
                    break;
                }
                Plot plot2 = getCurrentPlot(new Location(world, i+15, 0, j));
                if (plot2!=null && plot2.getId()!=plot.getId() && plot2.hasOwner()) {
                    break;
                }
                Plot plot3 = getCurrentPlot(new Location(world, i+15, 0, j+15));
                if (plot3!=null && plot3.getId()!=plot.getId() && plot3.hasOwner()) {
                    break;
                }
                Plot plot4 = getCurrentPlot(new Location(world, i, 0, j+15));
                if (plot4!=null && plot4.getId()!=plot.getId() && plot4.hasOwner()) {
                    break;
                }
                Plot plot5 = getCurrentPlot(new Location(world, i+15, 0, j+15));
                if (plot5!=null && plot5.getId()!=plot.getId() && plot5.hasOwner()) {
                    break;
                }
                if (min==null) {
                    min = new Location(world, Math.max(i-1, plotMinX), 0, Math.max(j-1, plotMinZ));
                    max = new Location(world, Math.min(i+16, plotMaxX), 0, Math.min(j+16, plotMaxZ));
                }
                else if (max.getBlockZ() < j + 15 || max.getBlockX() < i + 15) {
                    max = new Location(world, Math.min(i+16, plotMaxX), 0, Math.min(j+16, plotMaxZ));
                }
                world.regenerateChunk(i/16, j/16);
            }
        }
        if (min==null) {
            regenerateCuboid(pos1, pos2,requester,plotworld, new Object[] {plotfloors,plotfloors_data}, new Object[] {filling, filling_data});
        }
        else {
            int height = world.getMaxHeight();
            regenerateCuboid(new Location(world, plotMinX, 0, plotMinZ), new Location(world, min.getBlockX(), height, min.getBlockZ()),requester,plotworld, new Object[] {plotfloors,plotfloors_data}, new Object[] {filling, filling_data}); //1
            regenerateCuboid(new Location(world, min.getBlockX(), 0, plotMinZ), new Location(world, max.getBlockX(), height, min.getBlockZ()),requester,plotworld, new Object[] {plotfloors,plotfloors_data}, new Object[] {filling, filling_data}); //2
            regenerateCuboid(new Location(world, max.getBlockX(), 0, plotMinZ), new Location(world, plotMaxX, height, min.getBlockZ()),requester,plotworld, new Object[] {plotfloors,plotfloors_data}, new Object[] {filling, filling_data}); //3
            regenerateCuboid(new Location(world, plotMinX, 0, min.getBlockZ()), new Location(world, min.getBlockX(), height, max.getBlockZ()),requester,plotworld, new Object[] {plotfloors,plotfloors_data}, new Object[] {filling, filling_data}); //4
            regenerateCuboid(new Location(world, plotMinX, 0, max.getBlockZ()), new Location(world, min.getBlockX(), height, plotMaxZ),requester,plotworld, new Object[] {plotfloors,plotfloors_data}, new Object[] {filling, filling_data}); //5
            regenerateCuboid(new Location(world, min.getBlockX(), 0, max.getBlockZ()), new Location(world, max.getBlockX(), height, plotMaxZ),requester,plotworld, new Object[] {plotfloors,plotfloors_data}, new Object[] {filling, filling_data}); //6
            regenerateCuboid(new Location(world, max.getBlockX(), 0, min.getBlockZ()), new Location(world, plotMaxX, height, max.getBlockZ()),requester,plotworld, new Object[] {plotfloors,plotfloors_data}, new Object[] {filling, filling_data}); //7
            regenerateCuboid(new Location(world, max.getBlockX(), 0, max.getBlockZ()), new Location(world, plotMaxX, height, plotMaxZ),requester,plotworld, new Object[] {plotfloors,plotfloors_data}, new Object[] {filling, filling_data}); //8
        }
        PlayerFunctions.sendMessage(requester, C.CLEARING_DONE.s().replaceAll("%time%", ""+((System.currentTimeMillis()-start)/1000.0)));
    }
    public static void regenerateCuboid(Location pos1, Location pos2,Player player, PlotWorld plotworld, Object[] plotfloors, Object[] filling) {
        World world = pos1.getWorld();
        int zMin = pos1.getBlockZ();
        int zMax = pos2.getBlockZ();
        int xMin = pos1.getBlockX();
        int xMax = pos2.getBlockX();
        int height = pos2.getBlockY();
        Random random = new Random();
        
        for (int y = 0; y<=height; y++) {
            for (int x = xMin; x<=xMax; x++) {
                for(int z = zMin; z <= zMax; z++) {
                    short d = 0;
                    short id = 0;
                    boolean change = true;
                    Block block = world.getBlockAt(x,y,z);
                    int type = block.getTypeId();
                    if(y == 0) {
                        if(type != 7)
                            id = (short) Material.BEDROCK.getId();
                        else
                            change = false;
                    } else if(y == plotworld.PLOT_HEIGHT) {
                        Short[] result = getRandom(random, plotfloors);
                        id = result[0];
                        d = result[1];
                    } else if(y < plotworld.PLOT_HEIGHT) {
                        Short[] result = getRandom(random, filling);
                        id = result[0];
                        d = result[1];
                    } else if(y > plotworld.PLOT_HEIGHT && y < world.getMaxHeight()) {
                        if(type != 0)
                            id = 0;
                        else
                            change = false;
                    }
                    else {
                        change = false;
                    }
                    if(change) {
                        if (type!=id) {
                            block.setTypeIdAndData(id, (byte) d, true);
                        }
                    }
                }
            }
        }
    }

    public static void setBiome(World world, Plot plot, Biome b) {
        int bottomX = getPlotBottomLoc(world, plot.id).getBlockX() - 1;
        int topX = getPlotTopLoc(world, plot.id).getBlockX() + 1;
        int bottomZ = getPlotBottomLoc(world, plot.id).getBlockZ() - 1;
        int topZ = getPlotTopLoc(world, plot.id).getBlockZ() + 1;

        for (int x = bottomX; x <= topX; x++) {
            for (int z = bottomZ; z <= topZ; z++) {
                world.getBlockAt(x, 0, z).setBiome(b);
            }
        }

        plot.settings.setBiome(b);
        PlotMain.updatePlot(plot);
        refreshPlotChunks(world, plot);
    }

    public static Location getPlotHome(World w, Plot plot) {
        PlotWorld plotworld = PlotMain.getWorldSettings(w);
        if(plot.settings.getPosition() == PlotHomePosition.DEFAULT) {
            int x = getPlotBottomLoc(w, plot.id).getBlockX()
                    + (getPlotTopLoc(w, plot.id).getBlockX() - getPlotBottomLoc(w,
                    plot.id).getBlockX());
            int z = getPlotBottomLoc(w, plot.id).getBlockZ() - 2;
            return new Location(w, x, plotworld.ROAD_HEIGHT + 2, z);
        } else {
            World world = w;
            int x1 = getPlotBottomLoc(world, plot.id).getBlockX();
            int x2 = x1 + plotworld.PLOT_WIDTH;
            int z1 = getPlotBottomLoc(world, plot.id).getBlockZ();
            int z2 = z1 + plotworld.PLOT_WIDTH;

            int xMin = Math.min(x1, x2) + 1;
//            int xMax = Math.max(x1, x2);

            int zMin = Math.min(z1, z2) + 1;
//            int zMax = Math.max(z1, z2);

            double adder = (plotworld.PLOT_WIDTH / 2);
            double x = (xMin + adder), y = plotworld.ROAD_HEIGHT + 3, z = (zMin + adder);
            return new Location(world, x, y, z);
        }
    }

    public static Location getPlotHome(World w, PlotId id) {
        PlotWorld plotworld = PlotMain.getWorldSettings(w);
        if(getPlot(w,id).settings.getPosition() == PlotHomePosition.DEFAULT) {
            int x = getPlotBottomLoc(w, id).getBlockX()
                    + (getPlotTopLoc(w, id).getBlockX() - getPlotBottomLoc(w, id)
                    .getBlockX());
            int z = getPlotBottomLoc(w, id).getBlockZ() - 2;
            return new Location(w, x, plotworld.ROAD_HEIGHT + 2, z);
        } else {
            World world = w;
            int x1 = getPlotBottomLoc(world, id).getBlockX();
            int x2 = x1 + plotworld.PLOT_WIDTH;
            int z1 = getPlotBottomLoc(world, id).getBlockZ();
            int z2 = z1 + plotworld.PLOT_WIDTH;

            int xMin = Math.min(x1, x2) + 1;
            int xMax = Math.max(x1, x2);

            int zMin = Math.min(z1, z2) + 1;
            int zMax = Math.max(z1, z2);

            double adder = (plotworld.PLOT_WIDTH / 2);
            double x = (xMin + adder), y = plotworld.ROAD_HEIGHT + 3, z = (zMin + adder);
            return new Location(world, x, y, z);
        }
    }

    public static void refreshPlotChunks(World world, Plot plot) {
        int bottomX = getPlotBottomLoc(world, plot.id).getBlockX();
        int topX = getPlotTopLoc(world, plot.id).getBlockX();
        int bottomZ = getPlotBottomLoc(world, plot.id).getBlockZ();
        int topZ = getPlotTopLoc(world, plot.id).getBlockZ();

        int minChunkX = (int) Math.floor((double) bottomX / 16);
        int maxChunkX = (int) Math.floor((double) topX / 16);
        int minChunkZ = (int) Math.floor((double) bottomZ / 16);
        int maxChunkZ = (int) Math.floor((double) topZ / 16);

        for (int x = minChunkX; x <= maxChunkX; x++) {
            for (int z = minChunkZ; z <= maxChunkZ; z++) {
                world.refreshChunk(x, z);
            }
        }
    }

    public static Location getPlotTopLoc(World world, PlotId id) {
        PlotWorld plotworld = PlotMain.getWorldSettings(world);
        int px = id.x;
        int pz = id.y;

        int x = px * (plotworld.ROAD_WIDTH + plotworld.PLOT_WIDTH)
                - ((int) Math.floor(plotworld.ROAD_WIDTH / 2)) - 1;
        int z = pz * (plotworld.ROAD_WIDTH + plotworld.PLOT_WIDTH)
                - ((int) Math.floor(plotworld.ROAD_WIDTH / 2)) - 1;

        return new Location(world, x, 255, z);
    }

    public static Location getPlotBottomLoc(World world, PlotId id) {
        PlotWorld plotworld = PlotMain.getWorldSettings(world);
        int px = id.x;
        int pz = id.y;

        int x = px * (plotworld.ROAD_WIDTH + plotworld.PLOT_WIDTH) - plotworld.PLOT_WIDTH
                - ((int) Math.floor(plotworld.ROAD_WIDTH / 2)) - 1;
        int z = pz * (plotworld.ROAD_WIDTH + plotworld.PLOT_WIDTH) - plotworld.PLOT_WIDTH
                - ((int) Math.floor(plotworld.ROAD_WIDTH / 2)) - 1;

        return new Location(world, x, 1, z);
    }

    public static Plot getPlot(World world, PlotId id) {
        if (id == null) {
            return null;
        }
        if (PlotMain.getPlots(world).containsKey(id)) {
            return PlotMain.getPlots(world).get(id);
        }
        return new Plot(id, null, Biome.FOREST, new ArrayList<UUID>(), new ArrayList<UUID>(), world.getName());
    }

    public static Plot getCurrentPlot(Location loc) {
		/*
		 * Vector vector = player.getLocation().toVector(); for(plot plot :
		 * getPlots()) if(vector.isInAABB(plot.l1.toVector(),
		 * plot.l2.toVector())) return plot; return null;
		 */
        PlotId id = PlayerFunctions.getPlot(loc);
        if (id.equals("...") || id.equals("road")) {
            return null;
        }
        if (PlotMain.getPlots(loc.getWorld()).containsKey(id)) {
            return PlotMain.getPlots(loc.getWorld()).get(id);
        }
        return new Plot(id, null, Biome.FOREST, new ArrayList<UUID>(), new ArrayList<UUID>(), loc.getWorld().getName());
    }

    @SuppressWarnings({ "deprecation" })
    private static void setWall(Block block, String currentBlockId) {
        int blockId;
        byte blockData = 0;
        if (currentBlockId.contains(":")) {
            try {
                blockId = Integer.parseInt(currentBlockId.substring(0,
                        currentBlockId.indexOf(":")));
                blockData = Byte.parseByte(currentBlockId
                        .substring(currentBlockId.indexOf(":") + 1));
            } catch (NumberFormatException e) {
                blockId = 1;
                blockData = (byte) 0;
                e.printStackTrace();
            }
        } else {
            try {
                blockId = Integer.parseInt(currentBlockId);
            } catch (NumberFormatException e) {
                blockId = 1;
                blockData = (byte) 0;
                e.printStackTrace();
            }
        }
        block.setTypeIdAndData(blockId, blockData, true);
    }
}
