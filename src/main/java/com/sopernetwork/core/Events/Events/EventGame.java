package com.sopernetwork.core.Events.Events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EventGame implements Listener {

    private static EventGame instance;

    public EventGame() {
        instance = this;
    }

    public static EventGame getGlobalventInstance() {
        return instance;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        player.setGameMode(GameMode.SPECTATOR);
    }
}
