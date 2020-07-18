package com.sopernetwork.core;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Game {

    private Collection<? extends Player> onlinePlayers;

    private static Game game;
    private int repeatedTask;

    public Game(Collection<? extends Player> onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
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
        this.registerScheduler();
    }

    public void registerScheduler() {
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
        this.repeatedTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this,teleportPlayers, 0,timer);
    }

}
