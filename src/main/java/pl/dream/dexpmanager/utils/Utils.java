package pl.dream.dexpmanager.utils;

import org.bukkit.command.CommandSender;
import pl.dream.dexpmanager.Locale;
import pl.dream.dreamlib.Message;

public class Utils {

    public static boolean checkPermission(CommandSender sender, String permission){
        if(sender.hasPermission(permission)){
            return true;
        }

        Message.sendMessage(sender, Locale.NO_PERMISSION.toString());
        return false;
    }
}
