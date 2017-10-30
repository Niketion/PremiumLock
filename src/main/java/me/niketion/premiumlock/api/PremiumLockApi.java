package me.niketion.premiumlock.api;

import com.google.common.base.Charsets;
import me.niketion.premiumlock.PremiumLock;
import me.niketion.premiumlock.files.Config;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.connection.InitialHandler;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

/**
 * The API of PremiumLock
 *
 * Recommended method of retrieving the API object:
 * <code>
 * PremiumLockApi premiumLockApi = PremiumLockApi.getInstance();
 * </code>
 *
 * @version 1.1
 * @author Niketion
 */
public class PremiumLockApi {

    private Consts constants;
    private static PremiumLockApi singleton;

    /** Constructor for PremiumLockApi */
    PremiumLockApi() { }

    /**
     * Get the API object for PremiumLock.
     *
     * @return The API object (null if plugin isn't enabled)
     */
    public static synchronized PremiumLockApi getInstance() {
        if (singleton == null)
            singleton = new PremiumLockApi();
        return singleton;
    }

    /**
     * Get the constants (and permissions) of PremiumLock
     *
     * @return The constants object
     */
    public Consts getConstants() {
        return constants;
    }

    /**
     * Get the instance of main class
     *
     * @deprecated {@link #getInstance()}
     * @return PremiumLock plugin
     */
    @Deprecated
    public PremiumLock getPlugin() {
        return PremiumLock.getInstance();
    }

    /**
     * Get the PremiumLock version
     * useful to check if you're using the latest version
     *
     * @return PremiumLock version
     */
    public String getPluginVersion() {
        return PremiumLock.getInstance().getDescription().getVersion();
    }

    /**
     * Check if player is <tt>locked</tt>,
     * then he's present in the blocked list.
     *
     * @param playerName user to check
     * @return true if he's locked
     */
    public boolean isLocked(String playerName) {
        return Config.PREMIUM.getConfig().getStringList(Consts.listLocked).contains(playerName.toLowerCase());
    }

    /**
     * Set the premium-protection for a user.
     * Then set the {@link net.md_5.bungee.api.connection.PendingConnection}
     * to online mode.
     *
     * @param playerName user to set connection
     * @param value
     */
    public void setProtection(String playerName, boolean value) {
        String nameStringList =   this.constants.listLocked;
        Config config =           Config.PREMIUM;
        List<String> listLocked = config.getConfig().getStringList(nameStringList);

        if (!value) {
            if (listLocked.contains(playerName.toLowerCase())) listLocked.remove(playerName.toLowerCase());
        } else {
            listLocked.add(playerName.toLowerCase());
        }
        config.getFileManager().set(nameStringList, listLocked);
    }

    /**
     * Useful on cracked servers that UUID
     * was replaced with a "offline-uuid"
     * This is avoided the uuid-spoof of a lot of client
     *
     * @since 1.0
     * @see #setUUID(PendingConnection)
     * @param player user to check
     * @return true if he has the real uuid
     */
    @Deprecated
    public boolean hasRealUUID(ProxiedPlayer player) {
        UUID uuidFetch = UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getName()).getBytes(Charsets.UTF_8));
        return uuidFetch.toString().contains(player.getUniqueId().toString());
    }

    /**
     * Set UUID offline-mode for premium user
     * to avoid a uuid-spoof
     *
     * @since 1.1
     * @param username user to set UUID offline
     */
    public void setUUID(PendingConnection pendingConnection) {
        try {
            UUID UUIDs = UUID.nameUUIDFromBytes(("OfflinePlayer:" + pendingConnection.getName()).getBytes(Charset.forName("UTF-8")));
            Field Field = InitialHandler.class.getDeclaredField("uniqueId");
            Field.setAccessible(true);
            Field.set(pendingConnection, UUIDs);
        }
        catch (NoSuchFieldException|IllegalAccessException Ex) {
            BungeeCord.getInstance().getConsole().sendMessage(new TextComponent(
                    ChatColor.RED + "UUID Spoof FIX doesn't work for " + pendingConnection.getName() + " :/"));
        }
    }
}
