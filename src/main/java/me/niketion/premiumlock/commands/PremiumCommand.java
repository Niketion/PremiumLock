package me.niketion.premiumlock.commands;

import me.niketion.premiumlock.api.Consts;
import me.niketion.premiumlock.PremiumLock;
import me.niketion.premiumlock.files.Config;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;

/**
 * @author Niketion
 */
public class PremiumCommand extends CommandHandler {

    public PremiumCommand() {
        super("premium");
        BungeeCord.getInstance().getPluginManager().registerCommand(PremiumLock.getInstance(), this);
    }

    @Override
    String permission() {
        return Consts.premiumCommand;
    }

    @Override
    String usage() {
        return "usage-premium";
    }

    public void execute(CommandSender commandSender, String[] strings) {
        super.execute(commandSender, strings);
        if (!isExecutable()) return;

        String type;
        if (!Config.PREMIUM.getConfig().getStringList(Consts.listLocked).contains(strings[0].toLowerCase())) {
            type = ".not-locked";
        } else {
            type = ".locked";
        }

        commandSender.sendMessage(messageTranslated(Config.MAIN.getConfig().getString("player" + type)
                .replaceAll("%name", strings[0])));
    }
}
