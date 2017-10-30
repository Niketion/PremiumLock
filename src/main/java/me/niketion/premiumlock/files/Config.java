package me.niketion.premiumlock.files;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;

/**
 * The configurations file (for now only YAML)
 *
 * @author Niketion
 */
public enum Config {
    MAIN    ("config.yml"),
    PREMIUM ("premium.yml");

    private FileManager fileManager;
    private Configuration config;

    Config(String nameFile) {
        this.fileManager = new FileManager(nameFile);
        this.config = fileManager.getConfig();
    }

    public FileManager getFileManager() {
        return this.fileManager;
    }

    public Configuration getConfig() {
        return config;
    }

    public TextComponent getMessageTranslated(String path) {
        return new TextComponent(ChatColor.translateAlternateColorCodes('&', config.getString(path)));
    }
}
