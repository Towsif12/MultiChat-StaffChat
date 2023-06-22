package com.towsifkafi.mc.multichatsc;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.spicord.api.addon.SimpleAddon;
import org.spicord.bot.DiscordBot;

import java.util.Objects;

public class SPAddon extends SimpleAddon {

    private MultiChatSC plugin;
    private static SPAddon instance;
    private DiscordBot bot;
    public String prefix;

    public SPAddon(MultiChatSC pl) {
        super("MultiChat Staff Chat Sync", "multichat::staffchat", "TowsifKafi");
        instance = this;
        this.plugin = pl;
    }

    public void onLoad(DiscordBot bot) {
        this.bot = bot;
        this.prefix = bot.getCommandPrefix();
        bot.getJda().addEventListener(new Object[] { new MessageListenerBungee(this.plugin) });
    }

    public void onShutdown() {
        this.plugin.logger.log("&cDiscord ChatSync Disabled");
    }

    public void sendMessage(String message, String type) {
        // this.plugin.config.getString("staff-channel")
        String config = (Objects.equals(type, "mod")) ? "staffchat" : "adminchat";

        TextChannel tc = this.bot.getJda().getTextChannelById(this.plugin.config.getSection("channels").getString(config));
        tc.sendMessage(message).queue();
    }

}
