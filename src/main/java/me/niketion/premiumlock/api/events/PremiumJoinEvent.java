package me.niketion.premiumlock.api.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

/**
 * The event is called by PostLoginEvent <tt>(BungeeCord)</tt>
 * <p>
 * Event called as soon as a connection has a {@link ProxiedPlayer} and is ready
 * to be connected to a server.
 *
 * @author Niketion
 */
public class PremiumJoinEvent extends Event {

    private final ProxiedPlayer player;

    public PremiumJoinEvent(ProxiedPlayer player) {
        this.player = player;
    }

    /**
     * Get the user who joined with the
     * online-mode connection
     *
     * @return user
     */
    public ProxiedPlayer getPlayer() {
        return player;
    }
}
