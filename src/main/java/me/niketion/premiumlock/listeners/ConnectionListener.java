package me.niketion.premiumlock.listeners;

import me.niketion.premiumlock.PremiumLock;
import me.niketion.premiumlock.api.Consts;
import me.niketion.premiumlock.api.PremiumLockApi;
import me.niketion.premiumlock.api.events.PremiumJoinEvent;
import me.niketion.premiumlock.api.events.PremiumQuitEvent;
import me.niketion.premiumlock.files.Config;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;

/**
 * @author Niketion
 */
public class ConnectionListener implements Listener {

    private PluginManager pluginManager = BungeeCord.getInstance().getPluginManager();

    public ConnectionListener() {
        this.pluginManager.registerListener(PremiumLock.getInstance(), this);
    }

    @EventHandler(priority = 6)
    public void on(PreLoginEvent event) {
        PendingConnection connection = event.getConnection();

        if (Config.PREMIUM.getConfig().getStringList(Consts.listLocked).contains(connection.getName().toLowerCase())) {
            connection.setOnlineMode(true);
        }
    }

    @EventHandler
    public void on(LoginEvent event) {
        if (event.getConnection().isOnlineMode() && Config.MAIN.getConfig().getBoolean("uuid-spoof-fix"))
            PremiumLockApi.getInstance().setUUID(event.getConnection());
    }

    @EventHandler
    public void on(PostLoginEvent event) throws NoSuchFieldException {
        ProxiedPlayer player = event.getPlayer();

        if (player.getPendingConnection().isOnlineMode())
            this.pluginManager.callEvent(new PremiumJoinEvent(player));
    }

    @EventHandler
    public void on(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (player.getPendingConnection().isOnlineMode())
            this.pluginManager.callEvent(new PremiumQuitEvent(player));
    }
}
