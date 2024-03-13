package pl.dream.dexpmanager.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface ISubCommand {
    void run(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args);
}
