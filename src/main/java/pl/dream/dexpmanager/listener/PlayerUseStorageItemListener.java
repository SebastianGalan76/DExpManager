package pl.dream.dexpmanager.listener;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import pl.dream.dexpmanager.DExpManager;
import pl.dream.dexpmanager.Locale;
import pl.dream.dexpmanager.utils.Experience;
import pl.dream.dreamlib.Message;
import pl.dream.dreamlib.NBT;

public class PlayerUseStorageItemListener implements Listener {
    @EventHandler
    public void onStorageItemUse(PlayerInteractEvent e){
        Player p = e.getPlayer();

        if(e.getHand()==EquipmentSlot.HAND){
            if(e.getAction()==Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR){
                ItemStack itemStackMainHand = p.getInventory().getItemInMainHand();
                if(itemStackMainHand.getType() == DExpManager.getPlugin().configController.getStorageItem().getType()){
                    if(NBT.has(DExpManager.getPlugin(), itemStackMainHand, "storageExp")){
                        int exp = Integer.parseInt(NBT.get(DExpManager.getPlugin(), itemStackMainHand, "storageExp"));
                        p.getInventory().getItemInMainHand().setAmount(itemStackMainHand.getAmount() - 1);

                        useStorageItem(p, exp, e);
                    }
                }
            }
        }
        if(e.getHand()==EquipmentSlot.OFF_HAND){
            if(e.getAction()==Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR){
                ItemStack itemStackOffHand = p.getInventory().getItemInOffHand();
                if(itemStackOffHand.getType() == DExpManager.getPlugin().configController.getStorageItem().getType()){
                    if(NBT.has(DExpManager.getPlugin(), itemStackOffHand, "storageExp")){
                        int exp = Integer.parseInt(NBT.get(DExpManager.getPlugin(), itemStackOffHand, "storageExp"));
                        p.getInventory().getItemInOffHand().setAmount(itemStackOffHand.getAmount() - 1);

                        useStorageItem(p, exp, e);
                    }
                }
            }
        }
    }

    private void useStorageItem(Player p, int exp, PlayerInteractEvent e){
        Experience.changeExp(p, exp);

        p.playSound(p.getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 0.5f, 0.5f);
        Message.sendMessage(p, Locale.STORAGE_ITEM_USE.toString()
                .replace("{EXP}", String.valueOf(exp)));

        e.setCancelled(true);
    }
}
