package pl.dream.dexpmanager;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import pl.dream.dexpmanager.command.ExpCommand;
import pl.dream.dexpmanager.controller.ConfigController;

public final class DExpManager extends JavaPlugin {
    private static DExpManager plugin;
    private static Economy economy;

    public ConfigController configController;

    @Override
    public void onEnable() {
        plugin = this;

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        assert rsp != null;
        economy = rsp.getProvider();

        loadPlugin();

        getCommand("exp").setExecutor(new ExpCommand());
    }

    @Override
    public void onDisable() {

    }

    public void reloadPlugin(){
        reloadConfig();

        loadPlugin();
    }

    private void loadPlugin(){
        saveDefaultConfig();
        configController = new ConfigController(getConfig());

        Locale.loadMessages(this);
    }

    public static DExpManager getPlugin(){
        return plugin;
    }
    public static Economy getEconomy(){
        return economy;
    }
}
