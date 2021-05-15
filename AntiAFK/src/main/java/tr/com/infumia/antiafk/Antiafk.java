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
        HttpResponse<JsonNode> response = Unirest.post("https://oyungar.com/api/product/read.php")
                .header("accept", "*/*")
                .header("IP", getIp())
                .header("License_Code", getConfig().getString("license-code"))
                .asJson();
        if(response.isSuccess()){
            JSONObject object = response.getBody().getObject();
            JSONArray records = object.getJSONArray("records");
            if(!records.isEmpty()) {
                JSONObject jsonObject = records.getJSONObject(0);
                if (jsonObject.getString("ip_adress").equals(getIp()) && jsonObject.getString("license_code").equals(getConfig().getString("license-code"))) {
                    // no problem
                }
                else {
                    Bukkit.getPluginManager().disablePlugin(this);
                    Bukkit.getConsoleSender().sendMessage(ColorUtil.colored("&6[ANTIAFK] &eLisans geçersiz, eklenti devredışı bırakılıyor."));
                    return;
                }
            }
            else{
                Bukkit.getPluginManager().disablePlugin(this);
                Bukkit.getConsoleSender().sendMessage(ColorUtil.colored("&6[ANTIAFK] &eLisans geçersiz, eklenti devredışı bırakılıyor."));
                return;
            }
        }
        else{
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getConsoleSender().sendMessage(ColorUtil.colored("&6[ANTIAFK] &eAPI ile bağlantıda bir hata oluştu, ektenti yapımcısı ile görüşün."));
            return;
        }
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

    private String getIp(){
        try{
            final URL url = new URL("http://checkip.amazonaws.com");
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            return in.readLine();
        }catch (Exception e){
            e.printStackTrace();
        }

        return "127.0.0.1";
    }
}
