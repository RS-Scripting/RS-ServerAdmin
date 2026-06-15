package com.rsscripting.serveradmin.listeners;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.update.GitHubUpdateChecker;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateNotificationListener
        implements Listener {

    @EventHandler
    public void onPlayerJoin(
            PlayerJoinEvent event
    ) {

        Player player =
                event.getPlayer();

        if (!player.isOp()) {
            return;
        }

        GitHubUpdateChecker checker =
                RSServerAdmin.getInstance()
                        .getGitHubUpdateChecker();

        if (checker == null) {
            return;
        }

        if (!checker.isUpdateAvailable()) {
            return;
        }

        player.sendMessage(
                ChatColor.GREEN
                        + "[RS-ServerAdmin] "
                        + ChatColor.GOLD
                        + "Update Available! "
                        + ChatColor.GREEN
                        + "Current: "
                        + RSServerAdmin.getInstance()
                        .getDescription()
                        .getVersion()
                        + " Latest: "
                        + checker.getLatestVersionFound()
        );

    }

}