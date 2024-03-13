package pl.dream.dexpmanager.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pl.dream.dexpmanager.command.subcommand.InfoCommand;
import pl.dream.dexpmanager.command.subcommand.SetCommand;

public class ExpCommand implements CommandExecutor {
    private final ISubCommand info;
    private final ISubCommand set;

    public ExpCommand(){
        info = new InfoCommand();
        set = new SetCommand();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(args.length>0){
            if(args[0].equalsIgnoreCase("info")){
                info.run(sender, cmd, label, args);
            }
            if(args[0].equalsIgnoreCase("set")){
                set.run(sender, cmd, label, args);
            }
        }

        return true;
    }
}
