package com.towsifkafi.mc.multichatsc;

import com.towsifkafi.mc.multichatsc.MultiChatSC;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Logger {

    MultiChatSC plugin;
    String prefix;

    public Logger(MultiChatSC pl) {
        this.plugin = pl;
        this.prefix = pl.config.getString("prefix");
    }

    //private String prefix = this.plugin.config.getString("prefix");
    public void log(String text) {
        this.plugin.getProxy().getConsole().sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', this.prefix + " " + text)).create());
    }

    public void stafflog(String text) {
        this.plugin.getProxy().getConsole().sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', this.prefix + " " + text)).create());

        for (ProxiedPlayer onlineplayer : plugin.getProxy().getPlayers()) {
            if (onlineplayer.hasPermission("multichat.staff")) {
                gameLog(onlineplayer, text);
            }
        }
    }

    public void gameLog(CommandSender sender, String message) {
        log("&c"+sender.getName()+" : "+message);
        sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', this.prefix + " " + message)).create());
    }

}
