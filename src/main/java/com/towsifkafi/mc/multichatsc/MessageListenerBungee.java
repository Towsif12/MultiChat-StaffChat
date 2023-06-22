package com.towsifkafi.mc.multichatsc;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.config.Configuration;
import xyz.olivermartin.multichat.bungee.StaffChatManager;

public class MessageListenerBungee extends ListenerAdapter {

    private MultiChatSC plugin;

    public MessageListenerBungee(MultiChatSC pl) {
        this.plugin = pl;
    }

    StaffChatManager ChatManager = new StaffChatManager();

    public void onMessageReceived(MessageReceivedEvent event) {
        Configuration channels = this.plugin.config.getSection("channels");
        User author = event.getAuthor();
        Message message = event.getMessage();
        String msg = message.getContentDisplay();
        if(!author.isBot()) {

            if (event.isFromType(ChannelType.TEXT)) {
                TextChannel channel = event.getChannel().asTextChannel();

                if (channel.getId().equals(channels.getString("staffchat"))) {
                    //plugin.logger.log("Message from " + author.getName() + " in " + channel.getName() + ": " + msg);
                    ChatManager.sendModMessage(author.getName(), "", "Discord", message.getContentDisplay());
                } else if (channel.getId().equals(channels.getString("adminchat"))) {
                    ChatManager.sendAdminMessage(author.getName(), "", "Discord", message.getContentDisplay());
                }

            }

        }

    }

}

