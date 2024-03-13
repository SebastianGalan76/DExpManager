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

public class TakeCommand implements ISubCommand {
    @Override
    public void run(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!Utils.checkPermission(sender, "dexpmanager.exp.take")){
            return;
        }

        if(args.length!=3){
            Message.sendMessage(sender, Locale.COMMAND_TAKE_USE.toString());
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
            if(level<=0){
                Message.sendMessage(sender, Locale.LESS_THAN_ZERO.toString());
                return;
            }

            int playerLevel = player.getLevel();
            int playerExp = Experience.getExp(player);
            playerLevel -= level;

            int exp = playerExp - Experience.getExpAtLevel(playerLevel);
            success(sender, player, exp);
        }
        else if(value.equalsIgnoreCase("max")){
            int exp = Experience.getExp(player);

            success(sender, player, exp);
        }
        else{
            int exp;
            try{
                exp = Integer.parseInt(value);
            }catch (NumberFormatException e){
                Message.sendMessage(sender, Locale.NaN.toString());
                return;
            }

            if(exp<=0){
                Message.sendMessage(sender, Locale.LESS_THAN_ZERO.toString());
                return;
            }

            success(sender, player, exp);
        }
    }

    private void success(CommandSender sender, Player player, int exp){
        String message = Locale.COMMAND_TAKE_SUCCESS.toString();
        message = message.replace("{PLAYER}", player.getName())
                .replace("{EXP}", String.valueOf(exp));
        Message.sendMessage(sender, message);
        Experience.changeExp(player, -exp);
    }
}
