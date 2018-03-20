package com.github.intellectualcrafters.plotsquared.plot.commands;

import com.github.intellectualcrafters.plotsquared.commands.CommandDeclaration;
import com.github.intellectualcrafters.plotsquared.json.JSONObject;
import com.github.intellectualcrafters.plotsquared.plot.PS;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.HttpUtil;
import com.github.intellectualcrafters.plotsquared.plot.util.MainUtil;
import com.github.intellectualcrafters.plotsquared.plot.util.TaskManager;

@CommandDeclaration(command = "plugin",
    permission = "plots.use",
    description = "Show plugin information",
    aliases = "version",
    category = CommandCategory.INFO)
public class PluginCmd extends SubCommand {

  @Override
  public boolean onCommand(final PlotPlayer player, String[] args) {
    TaskManager.IMP.taskAsync(new Runnable() {
      @Override
      public void run() {
        MainUtil.sendMessage(player, String
            .format("$2>> $1&l" + PS.imp().getPluginName() + " $2($1Version$2: $1%s$2)",
                PS.get().getVersion()));
        MainUtil.sendMessage(player,
            "$2>> $1&lAuthors$2: $1Citymonstret $2& $1Empire92 $2& $1MattBDev");
        MainUtil.sendMessage(player,
            "$2>> $1&lWiki$2: $1https://github.com/IntellectualCrafters/PlotSquared/wiki");
        MainUtil.sendMessage(player, "$2>> $1&lNewest Version$2: $1" + getNewestVersionString());
      }
    });
    return true;
  }

  public String getNewestVersionString() {
    String str = HttpUtil
        .readUrl("https://api.github.com/repos/IntellectualSites/PlotSquared/releases/latest");
    JSONObject release = new JSONObject(str);
    return release.getString("name");
  }
}
