package tr.com.infumia.antiafk;

import com.cryptomorin.xseries.XMaterial;
import io.github.portlek.bukkititembuilder.ItemStackBuilder;
import io.github.portlek.smartinventory.Icon;
import io.github.portlek.smartinventory.InventoryContents;
import io.github.portlek.smartinventory.InventoryProvided;
import io.github.portlek.smartinventory.event.abs.SmartEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ItemControllerMenu implements InventoryProvided {

    private final ConfigManager manager;
    private final String item;

    public ItemControllerMenu(ConfigManager manager, String item) {
        this.manager = manager;
        this.item = item;
    }


    @Override
    public void init(InventoryContents contents) {
        manager.getMenuItems().forEach((m,s)-> {
            Optional<XMaterial> xMaterial = XMaterial.matchXMaterial(m);
            if(!xMaterial.isPresent()){
                return;
            }
            ItemStack item = ItemStackBuilder.from(xMaterial.get()).name(s).itemStack();
            if(!m.equals(this.item)){
                contents.add(Icon.cancel(item));
            }
            else{
                contents.add(Icon.click(item, SmartEvent::close));
            }
        });
    }

    @Override
    public void tick(InventoryContents contents) {
        if (!manager.isKickMode()){
            return;
        }
        long tick = contents.propertyOrDefault("tick", 0L);
        contents.property("tick", tick + 20L);
        if(tick >= manager.getKickInterval()*20){
            contents.player().kickPlayer(manager.getKickMessage());
        }

    }

}
