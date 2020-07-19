package com.sopernetwork.core.game;

import com.sopernetwork.core.Core;
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

    public Game GetGame() {
        if (Game.game == null) {
            return new Game(Core.getInstance().getServer().getOnlinePlayers());
        } else {
            return Game.game;
        }
    }

    public void StartGame() {
        if (this.onlinePlayers.size() <= 1) return;
        this.registerScheduler();
    }

    public void registerScheduler() {
        List<Player> players = this.onlinePlayers;
        List<Player> processedPlayer = new ArrayList<Player>();
        Runnable teleportPlayers = () -> {
            for(int i = 0; i<players.size();i++) {
                if (players.get(i).getGameMode() == GameMode.SPECTATOR) {

                    processedPlayer.add(players.get(i));
                    players.remove(i);
                }
                if ((i+1) >= players.size()) {
                    for (int j = 0; j<players.size();j++) {
                        Player initPlayer = players.get(j);
                        if (initPlayer == null) continue;
                        players.remove(j);
                        int randomIndex = ThreadLocalRandom.current().nextInt(0, players.size() + 1);
                        Player randomPlayer = players.get(randomIndex);
                        players.remove(randomIndex);
                        Location initPlayerLocation = initPlayer.getLocation();
                        Location randomPlayerLocation = randomPlayer.getLocation();
                        initPlayer.sendMessage("Switched with " + randomPlayer.getName());
                        initPlayer.teleport(randomPlayerLocation);
                        randomPlayer.sendMessage("Switched with " + initPlayer.getName());
                        randomPlayer.teleport(initPlayerLocation);
                    }
                }
            }
        };
        long timer = 45*20;
        this.repeatedTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Core.getInstance(),teleportPlayers, 0,timer);
    }

}
