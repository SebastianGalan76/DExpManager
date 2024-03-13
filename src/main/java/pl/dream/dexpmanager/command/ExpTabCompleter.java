package pl.dream.dexpmanager.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExpTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(args.length==1){
            if(sender.hasPermission("dexpmanager.rank.admin")){
                return Arrays.asList("info", "store", "give", "take", "set", "help", "reload");
            }

            return Arrays.asList("info", "store", "give");
        }
        else if(args.length==2){
            if(args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("take") || args[0].equalsIgnoreCase("set")){
                return Collections.singletonList("[nick]");
            }
            if(args[0].equalsIgnoreCase("store")){
                return Arrays.asList("[liczba]", "[liczba]L", "max");
            }
        }
        else if(args.length==3){
            if(args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("set")){
                if(sender.hasPermission("dexpmanager.rank.admin")){
                    return Arrays.asList("[liczba]", "[liczba]L");
                }

                return Arrays.asList("[liczba]", "[liczba]L", "max");
            }
            else if(args[0].equalsIgnoreCase("take")){
                return Arrays.asList("[liczba]", "[liczba]L", "max");
            }
        }

        return null;
    }
}
