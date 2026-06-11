package com.rsscripting.serveradmin.commands;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.security.UserRole;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RSAdminCommand implements CommandExecutor {

    private final RSServerAdmin plugin;

    public RSAdminCommand(RSServerAdmin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Players only.");
            return true;
        }

        UserRole role =
                plugin.getSecurityManager().getRole(
                        player.getUniqueId()
                );

        if (role == UserRole.NONE) {

            player.sendMessage("§cYou do not have access.");

            return true;
        }

        player.openInventory(
                com.rsscripting.serveradmin.gui.MainMenu.create()
        );

        return true;

    }
}