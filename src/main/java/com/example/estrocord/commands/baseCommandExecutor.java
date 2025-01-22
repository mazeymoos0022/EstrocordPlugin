package com.example.estrocord.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.example.estrocord.EstrocordPlugin;
import org.com.clovelib.CloveLib;

import java.util.UUID;

public class baseCommandExecutor implements CommandExecutor {
    private final EstrocordPlugin plugin;

    public baseCommandExecutor(EstrocordPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        // Check if the player is jailed via CloveLib
        if (!CloveLib.getInstance().canUseCommand("base", player)) {
            player.sendMessage(ChatColor.RED + "You cannot teleport while jailed!");
            return true;
        }

        UUID playerUUID = player.getUniqueId();
        if (!plugin.getBases().containsKey(playerUUID)) {
            player.sendMessage(ChatColor.RED + "You have not set a base!");
            return true;
        }

        Location baseLocation = plugin.getBases().get(playerUUID);
        if (player.teleport(baseLocation)) {
            player.playSound(baseLocation, Sound.valueOf(plugin.getConfig().getString("sounds.base", "ENTITY_ENDERMAN_TELEPORT")), 1.0f, 1.0f);
            player.sendMessage(ChatColor.GREEN + "Teleporting to your base!");
        } else {
            player.sendMessage(ChatColor.GOLD + "Teleport failed!");
        }

        return true;
    }
}
