package tk.shanebee.breedables.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import tk.shanebee.breedables.type.Gender;

import java.util.Random;

@SuppressWarnings("unused")
public class Utils {

    private static String prefix = "&7[&bBreedables&7] ";

    public static Gender getRandomGender() {
        int random = new Random().nextInt(10) + 1;
        return (random > 5) ? Gender.MALE : Gender.FEMALE;
    }

    public static String getColString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(getColString(prefix + message));
    }

    public static void sendColMsg(CommandSender player, String message) {
        player.sendMessage(getColString(prefix + message));
    }

}
