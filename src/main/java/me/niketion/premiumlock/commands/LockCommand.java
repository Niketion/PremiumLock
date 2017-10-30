package me.niketion.premiumlock.commands;

import me.niketion.premiumlock.api.Consts;
import me.niketion.premiumlock.PremiumLock;
import me.niketion.premiumlock.files.Config;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.config.Configuration;

import java.util.List;

/**
 * @author Niketion
 */
public class LockCommand extends CommandHandler {

    public LockCommand() {
        super("lock");
        BungeeCord.getInstance().getPluginManager().registerCommand(PremiumLock.getInstance(), this);
    }

    @Override
    String permission() {
        return Consts.lockCommand;
    }

    @Override
    String usage() {
        return "usage-lock";
    }

    public void execute(CommandSender commandSender, String[] strings) {
        super.execute(commandSender, strings);
        if (!isExecutable()) return;

        String nameStringList =    Consts.listLocked;
        String nameToLowCase =     strings[0].toLowerCase();
        Config configPremium =     Config.PREMIUM;
        Configuration configMain = Config.MAIN.getConfig();
        List<String> listLocked =  configPremium.getConfig().getStringList(nameStringList);

        String type = null;

        if (strings.length > 1) {
            switch (strings[1]) {
                case "on":
                    listLocked.add(nameToLowCase);
                    type = "protection.add";
                    break;
                case "off":
                    if (!listLocked.contains(nameToLowCase)) {
                        type = "protection.not";
                        break;
                    }
                    listLocked.remove(nameToLowCase);
                    type = "protection.remove";
                    break;
                default:
                    sendUsage(commandSender);
                    break;
            }

            if (type != null) {
                String message = configMain.getString(type);
                commandSender.sendMessage(messageTranslated(message.replaceAll("%name", nameToLowCase)));
            }

            configPremium.getFileManager().set(nameStringList, listLocked);
            return;
        }

        if (listLocked.contains(nameToLowCase)) {
            listLocked.remove(nameToLowCase);
            type = "protection.remove";
        } else {
            listLocked.add(nameToLowCase);
            type = "protection.add";
        }

        commandSender.sendMessage(messageTranslated(
                configMain.getString(type).replaceAll("%name", nameToLowCase)));
        configPremium.getFileManager().set(nameStringList, listLocked);
    }

}
