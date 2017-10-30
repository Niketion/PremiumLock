package me.niketion.premiumlock;

import me.niketion.premiumlock.commands.LockCommand;
import me.niketion.premiumlock.commands.PremiumCommand;
import me.niketion.premiumlock.files.Config;
import me.niketion.premiumlock.listeners.ConnectionListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * PremiumLock is a bungeecord plugin that
 * deals with "protecting" accounts chosen
 * by a admin, for set the connection to online mode.
 * This plugin was designed by various Italian servers
 *
 * @author Niketion
 */
public class PremiumLock extends Plugin {

    private static PremiumLock instance;
    public static PremiumLock getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        new LockCommand();
        new PremiumCommand();

        new ConnectionListener();

        Config.MAIN.getFileManager();
        Config.PREMIUM.getFileManager();
    }

    @Override
    public void onLoad() {
        for (String message : (":" + ChatColor.GREEN + "PremiumLock enabled," +
                " made by " + getDescription().getAuthor() + " with love and pizza.:" + ChatColor.RESET).split(":")) {
            getProxy().getConsole().sendMessage(new TextComponent(message));
        }
    }
}
