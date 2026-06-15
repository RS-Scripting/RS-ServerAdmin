package com.rsscripting.serveradmin.commands;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.security.UserRole;
import com.rsscripting.serveradmin.utils.WorldSyncUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class RSAdminCommand implements CommandExecutor {

    private final RSServerAdmin plugin;

    public RSAdminCommand(RSServerAdmin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NonNull [] args
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

        try {

            WorldSyncUtils.synchronizeWorlds();

        } catch (Exception ex) {

            plugin.getLogger().warning(
                    "World synchronization failed: "
                            + ex.getMessage()
            );

        }

        player.openInventory(
                com.rsscripting.serveradmin.gui.MainMenu.create()
        );

        return true;

    }
}