package me.niketion.premiumlock.commands;

import me.niketion.premiumlock.files.Config;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

/**
 * The CommandHandler of PremiumLock
 *
 * @author Niketion
 */
public abstract class CommandHandler extends Command {

    private Configuration config = Config.MAIN.getConfig();
    private boolean executable = false;

    /** The permission to run the command */
    abstract String permission();

    /** The usage of command (it take from config.yml) */
    abstract String usage();

    CommandHandler(String name) {
        super(name);
    }

    /**
     * Check if all condition for execute the command return true
     *
     * @return true if is executable
     */
    boolean isExecutable() {
        return executable;
    }

    /**
     * "Translate" the message with color using the
     * escape-char '&'
     *
     * @param message that translate
     * @return message translated
     */
    TextComponent messageTranslated(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        return new TextComponent(message);
    }

    /**
     * Send the usage of command to command sender
     * if the condition return false
     *
     * @param commandSender commandSender
     */
    void sendUsage(CommandSender commandSender) {
        for (String message : config.getStringList(usage()))
            commandSender.sendMessage(messageTranslated(message));
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!commandSender.hasPermission(permission())) {
            commandSender.sendMessage(messageTranslated(config.getString("no-permission")));
            this.executable = false;
            return;
        }

        if (!(strings.length > 0)) {
            sendUsage(commandSender);
            this.executable = false;
            return;
        }

        this.executable = true;
    }
}
