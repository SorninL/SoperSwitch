package com.sopernetwork.core.commands;

import com.sopernetwork.core.game.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Startgame implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Game.GetGame().StartGame();
        return true;
    }
}
