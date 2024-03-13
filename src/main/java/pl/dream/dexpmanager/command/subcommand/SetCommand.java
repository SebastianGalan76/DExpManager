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

public class SetCommand implements ISubCommand {
    @Override
    public void run(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!Utils.checkPermission(sender, "dexpmanager.exp.set")){
            return;
        }

        if(args.length!=3){
            Message.sendMessage(sender, Locale.COMMAND_SET_USE.toString());
            return;
        }

        Player player = Bukkit.getPlayer(args[1]);
        if(player==null){
            Message.sendMessage(sender, Locale.PLAYER_IS_OFFLINE.toString());
            return;
        }

        String value = args[2];
        if(value.matches("\\d+[Ll]")){
            value = value.substring(0, value.length() - 1);
            int level = Integer.parseInt(value);
            if(level<0){
                Message.sendMessage(sender, Locale.LESS_THAN_ZERO.toString());
                return;
            }

            int exp = Experience.getExpAtLevel(level);

            Experience.setExp(player, exp);
            success(sender, player, level, exp);
        }
        else{
            try{
                int exp = Integer.parseInt(value);
                if(exp<0){
                    Message.sendMessage(sender, Locale.LESS_THAN_ZERO.toString());
                    return;
                }
                int level = Experience.getIntLevelFromExp(exp);

                Experience.setExp(player, exp);
                success(sender, player, level, exp);
            }catch (NumberFormatException e){
                Message.sendMessage(sender, Locale.NaN.toString());
            }
        }
    }

    private void success(CommandSender sender, Player player, int level, int exp){
        String message = Locale.COMMAND_SET_SUCCESS.toString();
        message = message.replace("{PLAYER}", player.getName())
                .replace("{LEVEL}", String.valueOf(level))
                .replace("{EXP}", String.valueOf(exp));
        Message.sendMessage(sender, message);
    }
}
