package com.github.intellectualcrafters.plotsquared.plot.commands;

import com.github.intellectualcrafters.plotsquared.commands.CommandDeclaration;
import com.github.intellectualcrafters.plotsquared.plot.config.C;
import com.github.intellectualcrafters.plotsquared.plot.config.Settings;
import com.github.intellectualcrafters.plotsquared.plot.object.CmdInstance;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.CmdConfirm;
import com.github.intellectualcrafters.plotsquared.plot.util.MainUtil;
import com.github.intellectualcrafters.plotsquared.plot.util.TaskManager;

@CommandDeclaration(command = "confirm",
        permission = "plots.use",
        description = "Confirm an action",
        category = CommandCategory.INFO)
public class Confirm extends SubCommand {

    @Override
    public boolean onCommand(PlotPlayer player, String[] args) {
        CmdInstance command = CmdConfirm.getPending(player);
        if (command == null) {
            MainUtil.sendMessage(player, C.FAILED_CONFIRM);
            return false;
        }
        CmdConfirm.removePending(player);
        if ((System.currentTimeMillis() - command.timestamp) > Settings.Confirmation.CONFIRMATION_TIMEOUT_SECONDS * 1000) {
            MainUtil.sendMessage(player, C.EXPIRED_CONFIRM);
            return false;
        }
        TaskManager.runTask(command.command);
        return true;
    }
}
