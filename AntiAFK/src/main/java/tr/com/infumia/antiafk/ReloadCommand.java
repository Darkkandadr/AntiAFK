package tr.com.infumia.antiafk;

import io.github.portlek.bukkititembuilder.util.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final ConfigManager configManager;
    private final Antiafk plugin;

    public ReloadCommand(ConfigManager configManager, Antiafk plugin) {
        this.configManager = configManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("antiafk")) {
            if (args == null || args.length == 0) {
                sender.sendMessage(ColorUtil.colored(configManager.getTrueUsage()));
                return false;
            }
            else if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
                plugin.reloadConfig();
                sender.sendMessage(ColorUtil.colored(configManager.getReloadMessage()));
                return true;
            }
        }
        return false;
    }
}
