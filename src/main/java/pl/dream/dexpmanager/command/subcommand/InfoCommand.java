package pl.dream.dexpmanager.command.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.dream.dexpmanager.Locale;
import pl.dream.dexpmanager.command.ISubCommand;
import pl.dream.dexpmanager.utils.Experience;
import pl.dream.dexpmanager.utils.Utils;
import pl.dream.dreamlib.Message;

import java.util.List;

public class InfoCommand implements ISubCommand {


    @Override
    public void run(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(args.length==1){
            if(!Utils.checkPermission(sender, "dexpmanager.exp.info")){
                return;
            }

            if(sender instanceof Player){
                Player p = (Player) sender;

                sendInfo(sender, Locale.COMMAND_INFO_SELF.getList(), p);
            }
            else{
                Message.sendMessage(sender, Locale.NO_PLAYER.toString());
            }
        }
        else if(args.length==2){
            Player player = Bukkit.getPlayer(args[1]);
            if(player==null){
                Message.sendMessage(sender, Locale.PLAYER_IS_OFFLINE.toString());
                return;
            }
            if(!Utils.checkPermission(sender, "dexpmanager.exp.info.other")){
                return;
            }

            sendInfo(sender, Locale.COMMAND_INFO_OTHER.getList(), player);
        }
        else{
            Message.sendMessage(sender, Locale.COMMAND_INFO_USE.toString());
        }
    }

    private void sendInfo(CommandSender sender, List<String> messageList, Player p){
        int level = p.getLevel();
        int exp = Experience.getExp(p);
        int expToNextLevel = Experience.getExpToNext(level);
        ;
        for(String line:messageList){
            line = line.replace("{PLAYER}", p.getName())
                    .replace("{LEVEL}", String.valueOf(level))
                    .replace("{EXP}", String.valueOf(exp))
                    .replace("{REQUIRED_EXP}", String.valueOf(expToNextLevel));
            Message.sendMessage(sender, line);
        }
    }
}
