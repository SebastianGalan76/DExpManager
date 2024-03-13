package pl.dream.dexpmanager.utils;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.dream.dexpmanager.Locale;
import pl.dream.dreamlib.Message;

import java.util.HashMap;

public class Utils {

    public static boolean checkPermission(CommandSender sender, String permission){
        if(sender.hasPermission(permission)){
            return true;
        }

        Message.sendMessage(sender, Locale.NO_PERMISSION.toString());
        return false;
    }

    public static void takeItemFromPlayer(Player player, ItemStack item, int amount) {
        int remainingAmount = amount;
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack != null && stack.isSimilar(item)) {
                int stackAmount = stack.getAmount();
                if (stackAmount <= remainingAmount) {
                    remainingAmount -= stackAmount;
                    player.getInventory().setItem(i, new ItemStack(Material.AIR));
                    if (remainingAmount == 0) {
                        player.updateInventory();
                        return;
                    }
                } else {
                    stack.setAmount(stackAmount - remainingAmount);
                    player.updateInventory();
                    return;
                }
            }
        }
    }
    public static int getItemAmountInInventory(Player player, ItemStack item){
        int amountInInventory = 0;
        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack != null && stack.isSimilar(item)) {
                amountInInventory += stack.getAmount();
            }
        }

        return amountInInventory;
    }

    public static void giveItemToPlayer(Player player, ItemStack itemStack){
        HashMap<Integer, ItemStack> droppedItem = player.getInventory().addItem(itemStack);

        if(!droppedItem.isEmpty()){
            World world = player.getWorld();
            for(ItemStack item: droppedItem.values()){
                world.dropItem(player.getLocation(), item);
            }
        }
    }
}
