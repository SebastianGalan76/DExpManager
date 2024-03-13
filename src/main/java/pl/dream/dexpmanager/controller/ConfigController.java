package pl.dream.dexpmanager.controller;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.dream.dexpmanager.DExpManager;
import pl.dream.dreamlib.Config;
import pl.dream.dreamlib.NBT;

public class ConfigController {
    private final FileConfiguration config;

    private ItemStack storageItem;

    public boolean itemCostEnable;
    public boolean moneyCostEnable;

    public int moneyCost;
    public ItemStack itemCost;

    public ConfigController(FileConfiguration config){
        this.config = config;

        loadConfig();
    }

    public ItemStack getStorageItem(){
        return storageItem.clone();
    }

    private void loadConfig(){
        storageItem = Config.getItemStack(config, "storage.item");
        if(storageItem!=null){
            NBT.add(DExpManager.getPlugin(), storageItem, "storageExp", "0");
        }

        itemCostEnable = config.getBoolean("storage.itemCost.enable", false);
        if(itemCostEnable){
            itemCost = Config.getItemStack(config, "storage.itemCost.item");

            if(itemCost==null){
                Bukkit.getLogger().warning("Incorrect itemCost!");
                itemCostEnable = false;
            }
        }

        moneyCostEnable = config.getBoolean("storage.moneyCost.enable", false);
        if(moneyCostEnable){
            moneyCost = config.getInt("storage.moneyCost.cost");

            if(moneyCost<0){
                Bukkit.getLogger().warning("Incorrect moneyCost!");
                moneyCostEnable = false;
            }
        }
    }
}
