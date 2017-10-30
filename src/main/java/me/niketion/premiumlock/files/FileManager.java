package me.niketion.premiumlock.files;

import me.niketion.premiumlock.PremiumLock;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * The file manager of PremiumLock
 * for now there are only YAML files.
 *
 * @author Niketion
 */
public class FileManager {

    private final File file;
    private final Configuration config;

    FileManager(String nameFile) {
        PremiumLock main = PremiumLock.getInstance();

        if (!main.getDataFolder().exists())
            main.getDataFolder().mkdir();

        file = new File(main.getDataFolder(), nameFile);

        if (!file.exists()) {
            try (InputStream in = main.getResourceAsStream(nameFile)) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                exceptionMessage();
            }
        }

        Configuration _config;
        try {
            _config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            _config = null;
            exceptionMessage();
        }
        config = _config;
    }

    public File getFile() {
        return file;
    }

    Configuration getConfig() {
        return config;
    }

    private void exceptionMessage() {
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent(ChatColor.RED + "[PremiumLock] IOException error."));
    }

    private void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
        } catch (IOException e) {
            exceptionMessage();
        }
    }

    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }
}
