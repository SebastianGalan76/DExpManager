package pl.dream.dexpmanager.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.dream.dexpmanager.DExpManager;
import pl.dream.dexpmanager.Locale;
import pl.dream.dexpmanager.utils.Experience;
import pl.dream.dreamlib.Message;
import pl.dream.dreamlib.NBT;

public class PlayerUseStorageItemListener implements Listener {
    @EventHandler
    public void onUseStorageItem(PlayerInteractEvent e){
        Player p = e.getPlayer();

        ItemStack itemStack = p.getInventory().getItemInMainHand();
        if(itemStack.isSimilar(DExpManager.getPlugin().configController.getStorageItem())){
            if(NBT.has(DExpManager.getPlugin(), itemStack, "storageExp")){
                int exp = Integer.parseInt(NBT.get(DExpManager.getPlugin(), itemStack, "storageExp"));
                p.getInventory().getItemInMainHand().setAmount(itemStack.getAmount() - 1);
                Experience.changeExp(p, exp);

                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_BOTTLE_THROW, 0.5f, 0.5f);
                Message.sendMessage(p, Locale.STORAGE_ITEM_USE.toString()
                        .replace("{EXP}", String.valueOf(exp)));

                e.setCancelled(true);
            }
        }
    }
}
