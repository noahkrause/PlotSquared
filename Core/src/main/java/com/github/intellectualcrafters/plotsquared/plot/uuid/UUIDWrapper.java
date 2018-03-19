package com.github.intellectualcrafters.plotsquared.plot.uuid;

import com.github.intellectualcrafters.plotsquared.plot.object.OfflinePlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;

import java.util.UUID;

public abstract class UUIDWrapper {

    public abstract UUID getUUID(PlotPlayer player);

    public abstract UUID getUUID(OfflinePlotPlayer player);

    public abstract UUID getUUID(String name);

    public abstract OfflinePlotPlayer getOfflinePlayer(UUID uuid);

    public abstract OfflinePlotPlayer getOfflinePlayer(String name);

    public abstract OfflinePlotPlayer[] getOfflinePlayers();
}
