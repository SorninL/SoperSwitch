package com.sopernetwork.core;

import com.sopernetwork.core.Events.Events.EventGame;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    int repeatedTask;

    @Override
    public void onEnable() {
        this.getCoreLogger().info("SOPER SWITCH ON");
        this.initConfig();
        this.getCoreLogger().info("" + this.globalConfig.getInt("game.45")*20);
        this.getServer().getPluginManager().registerEvents(new EventGame(), this);
        Runnable teleportPlayers = new Runnable() {
            Collection<? extends Player> players = Core.getInstance().getServer().getOnlinePlayers();
            @Override public void run() {
                if (players.size() <= 1) return;
                Player firstPlayer;
                Location firstLocation;
                Player secondPlayer;
                Location secondLocation ;
                List<Player> playerTped = new ArrayList<Player>();
                for (int i = 0; i < players.size(); i++) {
                    if (players.toArray()[i] == null) continue;
                    firstPlayer = (Player) players.toArray()[i];
                    if (firstPlayer.getGameMode().equals(GameMode.SPECTATOR)) continue;
                    if (playerTped.contains(firstPlayer)) continue;
                    firstLocation = firstPlayer.getLocation();

                    for (int j = 0; j < players.size(); i++) {
                        if (players.toArray()[j] == null) continue;
                        secondPlayer = (Player) players.toArray()[i];
                        if (secondPlayer == firstPlayer) continue;
                        if (secondPlayer.getGameMode().equals(GameMode.SPECTATOR)) continue;
                        if (playerTped.contains(secondPlayer)) continue;
                        secondLocation = secondPlayer.getLocation();
                        firstPlayer.teleport(secondLocation);
                        secondPlayer.teleport(firstLocation);
                        firstPlayer.sendMessage("§cSwitch avec " + secondPlayer.getName());
                        secondPlayer.sendMessage("§cSwitch avec " + firstPlayer.getName());
                        playerTped.add(secondPlayer);
                        playerTped.add(firstPlayer);
                        break;
                    }
                }
            }
        };
        long timer = 45*20;
        this.repeatedTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, teleportPlayers, 0, timer);
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
