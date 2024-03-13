package pl.dream.dexpmanager.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pl.dream.dexpmanager.command.subcommand.InfoCommand;

public class ExpCommand implements CommandExecutor {
    private final ISubCommand info;

    public ExpCommand(){
        info = new InfoCommand();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(args.length>0){
            if(args[0].equalsIgnoreCase("info")){
                info.run(sender, cmd, label, args);
            }
        }

        return true;
    }
}
