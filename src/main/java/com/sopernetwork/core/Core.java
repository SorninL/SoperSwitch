package com.sopernetwork.core;

import com.sopernetwork.core.commands.Startgame;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Core extends JavaPlugin {

    private static Core instance;

    public Core() { instance = this; }

    public static Core getInstance() { return instance; }

    private Logger log = Bukkit.getLogger();

    public Logger getCoreLogger() { return getInstance().log; }

    private File langConfigFile;
    private FileConfiguration langConfig;
    private File globalConfigFile;
    private FileConfiguration globalConfig;

    @Override
    public void onEnable() {
        this.getCoreLogger().info("SOPER SWITCH ON");
        this.initConfig();
        this.getCommand("startgame").setExecutor(new Startgame());
    }

    @Override
    public void onDisable() {
    }

    public FileConfiguration GetGlobalConfig() {
        return this.globalConfig;
    }

    public FileConfiguration GetLangConfig() {
        return this.langConfig;
    }

    private void initConfig() {
        globalConfigFile = new File(getDataFolder(), "global.yml");
        langConfigFile = new File(getDataFolder(), "lang.yml");
        if (!globalConfigFile.exists()) {
            saveResource("global.yml", false);
        }
        if (!langConfigFile.exists()) {
            saveResource("lang.yml", false);
        }
        globalConfig = new YamlConfiguration();
        langConfig= new YamlConfiguration();
        try {
            globalConfig.load(globalConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        try {
            langConfig.load(langConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
