package tr.com.infumia.antiafk;

import io.github.portlek.bukkititembuilder.util.ColorUtil;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigManager {

    private final Antiafk plugin;

    public ConfigManager(Antiafk plugin) {
        this.plugin = plugin;
    }

    public List<String> getBreakList(){
        return plugin.getConfig().getStringList("block-break");
    }
    public Map<String, String> getMenuItems(){
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("menu-items");
        return section.getKeys(false).stream()
                .collect(Collectors.toMap(s -> s, s -> ColorUtil.colored(section.getString(s))));
    }

    public boolean isKickMode(){
        return plugin.getConfig().getBoolean("kick-mode");
    }
    public boolean isAntiRejoin() { return plugin.getConfig().getBoolean("anti-rejoin"); }
    public String getKickMessage(){
        return ColorUtil.colored(plugin.getConfig().getString("kick-message"));
    }
    public int getKickInterval(){
        return plugin.getConfig().getInt("kick-interval");
    }
    public String getReloadMessage(){
        return plugin.getConfig().getString("reload-message");
    }
    public String getTrueUsage(){
        return plugin.getConfig().getString("true-usage");
    }
    public double getChance(){
        return plugin.getConfig().getDouble("check-chance");
    }
}
