package tr.com.infumia.antiafk;

import io.github.portlek.smartinventory.Page;
import io.github.portlek.smartinventory.SmartInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.security.SecureRandom;
import java.util.*;

public class AntiRejoin implements Listener {

    private final ConfigManager manager;
    private final SmartInventory inventory;
    private final Antiafk plugin;
    private static final Random RANDOM = new SecureRandom();
    private List<String> antirejoin = new ArrayList<>();

    public AntiRejoin(ConfigManager manager, SmartInventory inventory, Antiafk plugin) {
        this.manager = manager;
        this.inventory = inventory;
        this.plugin = plugin;
    }

    @EventHandler
    public void onKicked(PlayerKickEvent event){
        if(manager.isAntiRejoin()){
            inventory.getPage(event.getPlayer())
                    .filter(page -> page.provider() instanceof ItemControllerMenu)
                    .ifPresent(page -> {
                        if(!antirejoin.contains(event.getPlayer().getName())){
                            antirejoin.add(event.getPlayer().getName());
                        }
                    });
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(manager.isAntiRejoin()){
            if(antirejoin.contains(event.getPlayer().getName())){
                BukkitRunnable task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        Map<String, String> map = manager.getMenuItems();
                        String randomize = randomize(map.keySet());
                        Page.build(inventory, new ItemControllerMenu(manager, randomize))
                                .row(1)
                                .title(map.get(randomize))
                                .tick(20)
                                .canClose(closeEvent -> false)
                                .async(false) // aslında false çaktırmayın
                                .open(event.getPlayer());
                        antirejoin.remove(event.getPlayer().getName());
                    }
                };
                task.runTaskLater(plugin, 20 * 1);
            }
        }
    }

    private static String randomize(Set<String> set){
        List<String> list = new ArrayList<>(set);
        return list.get(RANDOM.nextInt(list.size()));
    }

}
