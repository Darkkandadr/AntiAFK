package tr.com.infumia.antiafk;

import io.github.portlek.smartinventory.Page;
import io.github.portlek.smartinventory.SmartInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.security.SecureRandom;
import java.util.*;

public class EventListeners implements Listener {

    private final ConfigManager manager;
    private final SmartInventory inventory;
    private static final Random RANDOM = new SecureRandom();

    public EventListeners(ConfigManager manager, SmartInventory inventory) {
        this.manager = manager;
        this.inventory = inventory;
    }

    /* @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Material material = event.getBlock().getType();
        if (manager.getBreakList().contains(material.toString()) && chance(manager.getChance())){
            Map<String, String> map = manager.getMenuItems();
            String randomize = randomize(map.keySet());
            Page.build(inventory, new ItemControllerMenu(manager, randomize))
                    .row(1)
                    .title(map.get(randomize))
                    .tick(20)
                    .canClose(closeEvent -> false)
                    .async(false) // aslında false çaktırmayın
                    .open(player);
        }
        validEvent(player, event);
    }

     @EventHandler
    public void onAnyMove(PlayerMoveEvent event){
        validEvent(event.getPlayer(), event);
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        validEvent(event.getPlayer(), event);
    } */

    @EventHandler
    public void onFishing(PlayerInteractEvent event){
        validEvent(event.getPlayer(), event);
    }

    /* @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        validEvent(event.getPlayer(), event);
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        validEvent(event.getPlayer(), event);
    }
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) { validEvent(event.getPlayer(), event);} */


    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if(event.getState().equals(PlayerFishEvent.State.FISHING)){
            if(chance(manager.getChance())){
                Map<String, String> map = manager.getMenuItems();
                String randomize = randomize(map.keySet());
                Page.build(inventory, new ItemControllerMenu(manager, randomize))
                        .row(1)
                        .title(map.get(randomize))
                        .tick(20)
                        .canClose(closeEvent -> false)
                        .async(false) // aslında false çaktırmayın
                        .open(event.getPlayer());
            }
        }
    }

    private void validEvent(Player player, Cancellable event){
        inventory.getPage(player)
                .filter(page -> page.provider() instanceof ItemControllerMenu)
                .ifPresent(page -> {
                    event.setCancelled(true);
                });
    }

    private static String randomize(Set<String> set){
        List<String> list = new ArrayList<>(set);
        return list.get(RANDOM.nextInt(list.size()));
    }

    private boolean chance(double chance) {
        Random random = new Random();
        double d = random.nextDouble() * 100;
        if ((d -=  chance) < 0)
            return true;
        return false;
    }
    
}