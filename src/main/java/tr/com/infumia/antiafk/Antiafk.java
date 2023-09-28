package tr.com.infumia.antiafk;

import io.github.portlek.smartinventory.SmartInventory;
import io.github.portlek.smartinventory.manager.BasicSmartInventory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class Antiafk extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        // Plugin startup logic
        SmartInventory inventory = new BasicSmartInventory(this);
        inventory.init();
        ConfigManager manager = new ConfigManager(this);
        Bukkit.getPluginManager().registerEvents(new EventListeners(manager, inventory),this);
        Bukkit.getPluginManager().registerEvents(new AntiRejoin(manager, inventory, this), this);
        getCommand("antiafk").setExecutor(new ReloadCommand(manager, this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public SmartInventory getInventory(){
        SmartInventory inventory = new BasicSmartInventory(this);
        return inventory;
    }
}
