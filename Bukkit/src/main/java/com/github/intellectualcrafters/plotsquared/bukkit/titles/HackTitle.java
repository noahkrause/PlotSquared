package com.github.intellectualcrafters.plotsquared.bukkit.titles;

import com.github.intellectualcrafters.plotsquared.bukkit.object.BukkitPlayer;
import com.github.intellectualcrafters.plotsquared.plot.PS;
import com.github.intellectualcrafters.plotsquared.plot.config.Settings;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.AbstractTitle;

public class HackTitle extends AbstractTitle {

    @Override
    public void sendTitle(PlotPlayer player, String head, String sub, int in, int delay, int out) {
        try {
            HackTitleManager title = new HackTitleManager(head, sub, in, delay, out);
            title.send(((BukkitPlayer) player).player);
        } catch (Exception ignored) {
            PS.debug("&cYour server version does not support titles!");
            Settings.TITLES = false;
            AbstractTitle.TITLE_CLASS = null;
        }
    }
}
