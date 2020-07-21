package com.sopernetwork.core.game;

import com.sopernetwork.core.Core;
import com.sopernetwork.core.coreutils.LogUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private List<Player> onlinePlayers = new ArrayList<Player>();

    private static Game game;
    private int repeatedTask;

    public Game(Collection<? extends Player> onlinePlayers) {
        for(int i = 0; i<onlinePlayers.size();i++) {
            if (onlinePlayers.toArray()[i] instanceof Player) {
                this.onlinePlayers.add((Player) onlinePlayers.toArray()[i]);
            }
        }
        Game.game = this;
    }

    public static Game GetGame() {
        if (Game.game == null) {
            LogUtils.GetLogger().info("pass0.5");
            return new Game(Core.getInstance().getServer().getOnlinePlayers());
        } else {
            return Game.game;
        }
    }

    public void StartGame() {
        LogUtils.GetLogger().info("pass0.75");
        //if (this.onlinePlayers.size() <= 1) return;
        LogUtils.GetLogger().info("pass0.80");
        this.registerSchedulers();
    }


    public void registerSchedulers() {
        LogUtils.GetLogger().info("pass0");
        List<Integer> ints = new ArrayList<Integer>();
        ints.add(45);
        Runnable teleportPlayers = () -> {
            List<Player> players = this.onlinePlayers;
            List<Player> processedPlayer = new ArrayList<Player>();
            LogUtils.GetLogger().info("pass1");
            int timerLastTime = ints.get(0);
            ints.set(0, timerLastTime-1);
            if (timerLastTime > 10) return;
            LogUtils.GetLogger().info("pass2");
            if (timerLastTime <= 10 && timerLastTime !=0) {
                LogUtils.GetLogger().info("pass3");
                Core.getInstance().getServer().broadcastMessage("§cSwitch in " + timerLastTime + " s");
                return;
            }
            ints.set(0, 45);
            LogUtils.GetLogger().info("pass4");
            for(int i = 0; i<players.size();i++) {
                if (players.get(i).getGameMode() == GameMode.SPECTATOR) {
                    processedPlayer.add(players.get(i));
                    players.remove(i);
                }
                if ((i+1) >= players.size()) {
                    if ()
                        for (int j = 0; j<players.size();j++) {
                            Player initPlayer = players.get(j);
                            if (initPlayer == null) continue;
                            players.remove(j);
                            int randomIndex = ThreadLocalRandom.current().nextInt(0, players.size() + 1);
                            LogUtils.GetLogger().info(randomIndex + " yeet");
                            Player randomPlayer = players.get(randomIndex);
                            players.remove(randomIndex);
                            Location initPlayerLocation = initPlayer.getLocation();
                            Location randomPlayerLocation = randomPlayer.getLocation();
                            initPlayer.sendMessage("Switched with " + randomPlayer.getName());
                            initPlayer.teleport(randomPlayerLocation);
                            randomPlayer.sendMessage("Switched with " + initPlayer.getName());
                            randomPlayer.teleport(initPlayerLocation);
                            ints.set(0, 45);
                        }
                }
            }
        };
        long timer = 5;
        this.repeatedTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Core.getInstance(),teleportPlayers, 0,timer);
    }
}
