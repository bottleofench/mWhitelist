package api.bottleofench;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class mWhitelist extends JavaPlugin {

    private static JavaPlugin plugin;

    public mWhitelist() {
        plugin = this;
    }
    public static Plugin getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        File config = new File(getDataFolder().getPath() + File.separator + "config.yml");
        if (!config.exists()) {
            this.saveDefaultConfig();
        }

        Bukkit.getPluginManager().registerEvents(new Events(), this);

        getCommand("mwh").setExecutor(new Commands());
        getCommand("mwh").setTabCompleter(new Commands());

        reloadConfig();
    }

    @Override
    public void onDisable() {}

    public static String applyFormatCodes(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static void checkPlayers() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!mWhitelist.getInstance().getConfig().getStringList("players").contains(p.getName())) {
                p.kick(Component.text(Objects.requireNonNull(mWhitelist.getInstance().getConfig().getString("whitelist_message"))));
            }
        }
    }

}
