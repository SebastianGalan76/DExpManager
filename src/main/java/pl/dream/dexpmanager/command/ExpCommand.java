package pl.dream.dexpmanager.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pl.dream.dexpmanager.DExpManager;
import pl.dream.dexpmanager.command.subcommand.*;
import pl.dream.dexpmanager.utils.Utils;

public class ExpCommand implements CommandExecutor {
    private final ISubCommand info;
    private final ISubCommand set;
    private final ISubCommand give;
    private final ISubCommand take;
    private final ISubCommand store;

    public ExpCommand(){
        info = new InfoCommand();
        set = new SetCommand();
        give = new GiveCommand();
        take = new TakeCommand();
        store = new StoreCommand();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(args.length>0){
            if(args[0].equalsIgnoreCase("reload")){
                if(Utils.checkPermission(sender, "dexpmanager.exp.reload")){
                    DExpManager.getPlugin().reloadPlugin();
                }
            }
            else if(args[0].equalsIgnoreCase("info")){
                info.run(sender, cmd, label, args);
            }
            else if(args[0].equalsIgnoreCase("set")){
                set.run(sender, cmd, label, args);
            }
            else if(args[0].equalsIgnoreCase("give")){
                give.run(sender, cmd, label, args);
            }
            else if(args[0].equalsIgnoreCase("take")){
                take.run(sender, cmd, label, args);
            }
            else if(args[0].equalsIgnoreCase("store")){
                store.run(sender, cmd, label, args);
            }

        }

        return true;
    }
}
