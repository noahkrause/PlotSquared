package com.github.intellectualcrafters.plotsquared.bukkit.uuid;

import com.github.intellectualcrafters.plotsquared.plot.object.OfflinePlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.google.common.base.Charsets;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class LowerOfflineUUIDWrapper extends OfflineUUIDWrapper {

    @Override
    public UUID getUUID(PlotPlayer player) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getName().toLowerCase()).getBytes(Charsets.UTF_8));
    }

    @Override
    public UUID getUUID(OfflinePlotPlayer player) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getName().toLowerCase()).getBytes(Charsets.UTF_8));
    }

    @Override
    public UUID getUUID(OfflinePlayer player) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getName().toLowerCase()).getBytes(Charsets.UTF_8));
    }

    @Override
    public UUID getUUID(String name) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name.toLowerCase()).getBytes(Charsets.UTF_8));
    }

}
