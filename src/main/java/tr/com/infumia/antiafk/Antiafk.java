package tr.com.infumia.antiafk;

import io.github.portlek.bukkititembuilder.util.ColorUtil;
import io.github.portlek.smartinventory.SmartInventory;
import io.github.portlek.smartinventory.manager.BasicSmartInventory;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

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
