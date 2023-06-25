package com.towsifkafi.mc.multichatsc;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.spicord.SpicordLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class MultiChatSC extends Plugin {

    private static MultiChatSC instance;
    public Logger logger;

    public Configuration config;
    public SPAddon addon;

    @Override
    public void onEnable() {

        setInstance(this);
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(loadResource(this, "config.yml"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        PluginManager pM = getProxy().getPluginManager();
        this.logger = new Logger(this);

        //Events
        pM.registerListener(this, new StaffChatEvent(this));

        //Spicord
        addon = new SPAddon(this);
        SpicordLoader.addStartupListener(spicord -> spicord.getAddonManager().registerAddon(this.addon));

        //Log
        logger.log("&cDiscord Staff Chat Started");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static MultiChatSC getInstance() {
        return instance;
    }
    public static void setInstance(MultiChatSC instance) {
        MultiChatSC.instance = instance;
    }

    public static File loadResource(Plugin plugin, String resource) {
        File folder = plugin.getDataFolder();
        if (!folder.exists())
            folder.mkdir();
        File resourceFile = new File(folder, resource);
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
                try (InputStream in = plugin.getResourceAsStream(resource);
                     OutputStream out = new FileOutputStream(resourceFile)) {
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            checkResource(plugin, resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceFile;
    }

    public static void checkResource(Plugin plugin, String resource) throws IOException {
        File folder = plugin.getDataFolder();
        if (!folder.exists())
            folder.mkdir();
        File resourceFile = new File(folder, resource);
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
                try (InputStream in = plugin.getResourceAsStream(resource);
                     OutputStream out = new FileOutputStream(resourceFile)) {
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Configuration externalYamlConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(resourceFile);

        InputStreamReader internalConfigFileStream = new InputStreamReader(plugin.getResourceAsStream(resource), StandardCharsets.UTF_8);
        Configuration internalYamlConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(internalConfigFileStream);

        for (String string : internalYamlConfig.getKeys()) {

            // Checks if the external file contains the key already.
            if (!externalYamlConfig.contains(string)) {
                // If it doesn't contain the key, we set the key based off what was found inside the plugin jar);
                MultiChatSC.getInstance().getProxy().getConsole().sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "[MultiChatSC]" + " " + "&cThe config file for " + resource + " is missing the key: \"" + string + "\" default value is \"" + internalYamlConfig.get(string) + "\"")).create());
            }
        }


    }

}
