package me.niketion.premiumlock.api.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

/**
 * The event is called by PlayerDisconnectEvent <tt>(BungeeCord)</tt>
 * <p>
 * Called when a player has left the proxy, it is not safe to call any methods
 * that perform an action on the passed player instance.
 *
 * @author Niketion
 */
public class PremiumQuitEvent extends Event {

    private final ProxiedPlayer player;

    public PremiumQuitEvent(ProxiedPlayer player) {
        this.player = player;
    }

    /**
     * Get the user who disconnected with the
     * online-mode connection
     *
     * @return user
     */
    public ProxiedPlayer getPlayer() {
        return player;
    }
}
