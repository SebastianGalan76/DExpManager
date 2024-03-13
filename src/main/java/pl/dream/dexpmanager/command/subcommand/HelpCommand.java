package pl.dream.dexpmanager.command.subcommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pl.dream.dexpmanager.Locale;
import pl.dream.dexpmanager.command.ISubCommand;
import pl.dream.dreamlib.Message;

public class HelpCommand implements ISubCommand {
    @Override
    public void run(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("dexpmanager.rank.admin")){
            Message.sendMessage(sender, Locale.COMMAND_HELP_ADMIN.getList());
            return;
        }

        Message.sendMessage(sender, Locale.COMMAND_HELP_PLAYER.getList());
    }
}
