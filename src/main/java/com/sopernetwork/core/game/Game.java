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
                        Player randomPlayer = players.get(ThreadLocalRandom.current().nextInt(0, players.size() + 1));
                    }
                }
            }




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
        };
        long timer = 45*20;
        this.repeatedTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Core.getInstance(),teleportPlayers, 0,timer);
    }

}
