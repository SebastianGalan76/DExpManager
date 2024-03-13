package pl.dream.dexpmanager.command.subcommand;

import jdk.vm.ci.meta.Local;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import pl.dream.dexpmanager.DExpManager;
import pl.dream.dexpmanager.Locale;
import pl.dream.dexpmanager.command.ISubCommand;
import pl.dream.dexpmanager.controller.ConfigController;
import pl.dream.dexpmanager.utils.Experience;
import pl.dream.dexpmanager.utils.Utils;
import pl.dream.dreamlib.Message;
import pl.dream.dreamlib.NBT;

import java.util.ArrayList;
import java.util.List;

public class StoreCommand implements ISubCommand {
    @Override
    public void run(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            Message.sendMessage(sender, Locale.NO_PLAYER.toString());
            return;
        }

        if(!Utils.checkPermission(sender, "dexpmanager.exp.store")){
            return;
        }

        if(args.length!=2){
            Message.sendMessage(sender, Locale.COMMAND_STORE_USE.toString());
            return;
        }

        Player p = (Player) sender;
        String value = args[1];
        if(value.matches("\\d+[Ll]")){
            value = value.substring(0, value.length() - 1);
            int level = Integer.parseInt(value);
            if(level<=0){
                Message.sendMessage(sender, Locale.LESS_THAN_ZERO.toString());
                return;
            }

            int playerLevel = p.getLevel();
            if(playerLevel<level){
                Message.sendMessage(sender, Locale.NOT_ENOUGH_LEVEL.toString());
                return;
            }

            int playerExp = Experience.getExp(p);
            int exp = playerExp - Experience.getExpAtLevel(playerLevel-level);

            success(p, exp);
        }
        else if(value.equalsIgnoreCase("max")){
            int exp = Experience.getExp(p);

            success(p, exp);
        }
        else{
            int exp;
            try{
                exp = Integer.parseInt(value);
            }catch (NumberFormatException e){
                Message.sendMessage(sender, Locale.NaN.toString());
                return;
            }

            if(exp<=0){
                Message.sendMessage(sender, Locale.LESS_THAN_ZERO.toString());
                return;
            }

            int playerExp = Experience.getExp(p);
            if(playerExp<exp){
                Message.sendMessage(sender, Locale.NOT_ENOUGH_EXP.toString());
                return;
            }

            success(p, exp);
        }
    }

    private void success(Player player, int exp){
        ConfigController config = DExpManager.getPlugin().configController;

        if(config.moneyCostEnable){
            if(DExpManager.getEconomy().getBalance(player)<config.moneyCost){
                Message.sendMessage(player, Locale.NOT_ENOUGH_MONEY.toString()
                        .replace("{MONEY}", String.valueOf(config.moneyCost)));
                return;
            }
        }
        if(config.itemCostEnable){
            if(Utils.getItemAmountInInventory(player, config.itemCost)<config.itemCost.getAmount()){
                Message.sendMessage(player, Locale.NOT_ENOUGH_ITEM.toString());
                return;
            }
        }

        String message = Locale.COMMAND_STORE_SUCCESS.toString();
        message = message.replace("{EXP}", String.valueOf(exp));
        Message.sendMessage(player, message);

        Experience.changeExp(player, -exp);

        ItemStack storageItem = config.getStorageItem();
        ItemMeta itemMeta = storageItem.getItemMeta();
        List<String> convertedLore = new ArrayList<>();
        for(String line:itemMeta.getLore()){
            line = line.replace("{PLAYER}", player.getName())
                    .replace("{EXP}", String.valueOf(exp));
            convertedLore.add(line);
        }
        itemMeta.setLore(convertedLore);
        storageItem.setItemMeta(itemMeta);

        NBT.add(DExpManager.getPlugin(), storageItem, "storageExp", String.valueOf(exp));
        Utils.giveItemToPlayer(player, storageItem);

        if(config.moneyCostEnable){
            DExpManager.getEconomy().withdrawPlayer(player, config.moneyCost);
        }
        if(config.itemCostEnable){
            Utils.takeItemFromPlayer(player, config.itemCost, config.itemCost.getAmount());
        }
    }
}
