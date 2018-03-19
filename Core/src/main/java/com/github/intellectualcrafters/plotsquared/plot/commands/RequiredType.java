package com.github.intellectualcrafters.plotsquared.plot.commands;

import com.github.intellectualcrafters.plotsquared.commands.CommandCaller;

public enum RequiredType {
    CONSOLE, PLAYER, NONE;

    public boolean allows(CommandCaller player) {
        switch (this) {
            case NONE:
                return true;
            default:
                return this == player.getSuperCaller();
        }
    }
}
