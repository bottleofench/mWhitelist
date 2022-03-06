package api.bottleofench;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("mwh.*")) {
            if (args.length == 0) {
                return true;
            }
            if (args[0].equalsIgnoreCase("on")) {
                mWhitelist.getInstance().getConfig().set("whitelist", true);

                mWhitelist.getInstance().saveConfig();
                mWhitelist.getInstance().reloadConfig();

                sender.sendMessage(mWhitelist.applyFormatCodes("&aВайтлист включен!"));
            }
            if (args[0].equalsIgnoreCase("off")) {
                mWhitelist.getInstance().getConfig().set("whitelist", false);

                mWhitelist.getInstance().saveConfig();
                mWhitelist.getInstance().reloadConfig();

                sender.sendMessage(mWhitelist.applyFormatCodes("&aВайтлист выключен!"));
            }
            if (args[0].equalsIgnoreCase("add")) {
                List<String> strings = mWhitelist.getInstance().getConfig().getStringList("players");

                if (!strings.contains(args[1])) {
                    strings.add(args[1]);
                    mWhitelist.getInstance().getConfig().set("players", strings);
                    mWhitelist.getInstance().saveConfig();
                    mWhitelist.getInstance().reloadConfig();
                }
                sender.sendMessage(mWhitelist.applyFormatCodes("&f%player% &aбыл добавлен в вайтлист.".replace("%player%", args[1])));
            }
            if (args[0].equalsIgnoreCase("remove")) {
                List<String> strings = mWhitelist.getInstance().getConfig().getStringList("players");

                if (strings.contains(args[1])) {
                    strings.remove(args[1]);
                    mWhitelist.getInstance().getConfig().set("players", strings);
                    mWhitelist.getInstance().saveConfig();
                    mWhitelist.getInstance().reloadConfig();
                }
                sender.sendMessage(mWhitelist.applyFormatCodes("&f%player% &aбыл убран из вайтлиста.".replace("%player%", args[1])));
            }
            if (args[0].equalsIgnoreCase("list")) {
                List<String> list = mWhitelist.getInstance().getConfig().getStringList("players");
                sender.sendMessage(mWhitelist.applyFormatCodes("&aВайтлист: "));
                for (String s : list) sender.sendMessage(s);
            }
            if (args[0].equalsIgnoreCase("reload")) {
                mWhitelist.getInstance().reloadConfig();
                sender.sendMessage(mWhitelist.applyFormatCodes("&aКонфиг перезагружен!"));
            }
            mWhitelist.checkPlayers();
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("add");list.add("remove");list.add("list");list.add("reload");
            list.add("on"); list.add("off");
            return list;
        }
        return null;
    }
}
