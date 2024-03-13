package pl.dream.dexpmanager;

import org.bukkit.plugin.java.JavaPlugin;

public final class DExpManager extends JavaPlugin {
    private static DExpManager plugin;

    @Override
    public void onEnable() {
        plugin = this;

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

        Locale.loadMessages(this);
    }

    public static DExpManager getPlugin(){
        return plugin;
    }
}
