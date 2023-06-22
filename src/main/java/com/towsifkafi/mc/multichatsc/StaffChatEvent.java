package com.towsifkafi.mc.multichatsc;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.olivermartin.multichat.bungee.events.PostStaffChatEvent;

import java.util.Objects;

public class StaffChatEvent implements Listener {

    private MultiChatSC plugin;
    private SPAddon addon;

    public StaffChatEvent(MultiChatSC pl) {
        this.plugin = pl;
        this.addon = pl.addon;
    }

    @EventHandler
    public void chatEvent(PostStaffChatEvent event) {

        String message = event.getRawMessage();
        String type = event.getType();

        plugin.getLogger().info(message + " " + type + " ");
        String template = plugin.config.getString("message-template");

        // String botMsg = "> [" + event.getSenderServer() + "] **" + event.getRawSenderDisplayName() + "** : " + message;
        String botMsg = template
                .replace("{SERVER}", event.getSenderServer())
                .replace("{SENDER}", event.getRawSenderDisplayName())
                .replace("{MESSAGE}", message);

        if(Objects.equals(type, "mod")) {
            this.plugin.addon.sendMessage(botMsg, "mod");
        } else if (Objects.equals(type, "admin")) {
            this.plugin.addon.sendMessage(botMsg, "admin");
        }


    };

}
