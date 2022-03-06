package api.bottleofench;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Objects;

public class Events implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent e) {
        if (mWhitelist.getInstance().getConfig().getString("whitelist") == null) {
            Bukkit.getPluginManager().disablePlugin(mWhitelist.getInstance());
        }
        else {
            if (mWhitelist.getInstance().getConfig().getBoolean("whitelist")) {
                if (!mWhitelist.getInstance().getConfig().getStringList("players").contains(e.getPlayer().getName())) {
                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Component.text(Objects.requireNonNull(mWhitelist.getInstance().getConfig().getString("whitelist_message"))));
                }
            }
        }
    }
}
