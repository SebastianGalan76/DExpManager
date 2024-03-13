package pl.dream.dexpmanager;

import org.bukkit.plugin.java.JavaPlugin;
import pl.dream.dexpmanager.command.ExpCommand;
import pl.dream.dexpmanager.controller.ConfigController;

public final class DExpManager extends JavaPlugin {
    private static DExpManager plugin;

    public ConfigController configController;

    @Override
    public void onEnable() {
        plugin = this;

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
}
