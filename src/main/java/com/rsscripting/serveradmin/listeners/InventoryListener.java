package com.rsscripting.serveradmin.listeners;

import com.rsscripting.serveradmin.gui.KeepInventoryMenu;
import com.rsscripting.serveradmin.gui.RSMenuHolder;
import com.rsscripting.serveradmin.gui.CreeperMenu;
import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.settings.SettingKey;
import com.rsscripting.serveradmin.keepinventory.KeepInventoryDAO;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!(event.getInventory().getHolder() instanceof RSMenuHolder holder)) {
            return;
        }

        if (event.getClickedInventory() == null) {
            return;
        }

        if (event.getClickedInventory() != event.getView().getTopInventory()) {
            return;
        }

        event.setCancelled(true);

        switch (holder.getMenuId()) {

            case "main" -> {

                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                switch (event.getSlot()) {

                    /*
                    | Rescan for new worlds
                     */

                    case 4 -> {

                        try {

                            boolean keepInventoryDefault =
                                    RSServerAdmin.getInstance()
                                            .getSettingsDAO()
                                            .getDefault(
                                                    SettingKey.KEEP_INVENTORY_NEW_WORLD
                                            );

                            for (org.bukkit.World world
                                    : org.bukkit.Bukkit.getWorlds()) {

                                RSServerAdmin.getInstance()
                                        .getWorldsDAO()
                                        .ensureWorldExists(
                                                world.getName()
                                        );

                                RSServerAdmin.getInstance()
                                        .getKeepInventoryDAO()
                                        .ensureWorldExists(
                                                world.getName(),
                                                keepInventoryDefault
                                        );

                            }

                            player.sendMessage(
                                    "§aWorld scan complete."
                            );

                        } catch (Exception ex) {

                            ex.printStackTrace();

                            player.sendMessage(
                                    "§cWorld scan failed."
                            );

                        }

                    }

                    /*
                    | Creeper menu
                     */

                    case 11 -> player.openInventory(
                            CreeperMenu.create()
                    );

                    /*
                    | Keep Inventory Menu
                     */

                    case 15 -> {

                        try {

                            player.openInventory(
                                    KeepInventoryMenu.create(
                                            1
                                    )
                            );

                        } catch (Exception ex) {

                            ex.printStackTrace();

                        }

                    }

                }

            }

            /*
            |  Creeper menu
             */

            case "creeper" -> {

                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                switch (event.getSlot()) {

                    case 11 -> {

                        try {

                            RSServerAdmin.getInstance()
                                    .getSettingsDAO()
                                    .toggleDefault(
                                            SettingKey.CREEPER_BLOCK_DAMAGE
                                    );

                        } catch (Exception ex) {

                            ex.printStackTrace();

                        }

                        player.openInventory(
                                CreeperMenu.create()
                        );

                    }

                    case 15 -> {

                        try {

                            RSServerAdmin.getInstance()
                                    .getSettingsDAO()
                                    .toggleDefault(
                                            SettingKey.CREEPER_ENTITY_DAMAGE
                                    );

                        } catch (Exception ex) {

                            ex.printStackTrace();

                        }

                        player.openInventory(
                                CreeperMenu.create()
                        );

                    }

                    case 4 -> player.openInventory(
                            com.rsscripting.serveradmin.gui.MainMenu.create()
                    );

                }

            }

            /*
            |  Keep Inventory
            */

            case "KEEP_INVENTORY" -> {

                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                switch (event.getSlot()) {

                    case 3 -> player.openInventory(
                            com.rsscripting.serveradmin.gui.MainMenu.create()
                    );

                    case 5 -> {

                        try {

                            RSServerAdmin.getInstance()
                                    .getSettingsDAO()
                                    .toggleDefault(
                                            SettingKey.KEEP_INVENTORY_NEW_WORLD
                                    );

                            player.openInventory(
                                    KeepInventoryMenu.create(
                                            holder.getPage()
                                    )
                            );

                        } catch (Exception ex) {

                            ex.printStackTrace();

                        }

                    }

                    default -> {

                        if (event.getSlot() < 9) {
                            return;
                        }

                        try {

                            KeepInventoryDAO dao =
                                    RSServerAdmin.getInstance()
                                            .getKeepInventoryDAO();

                            java.util.List<String> worlds =
                                    RSServerAdmin.getInstance()
                                            .getWorldsDAO()
                                            .getAllWorlds();

                            int worldIndex =
                                    ((holder.getPage() - 1) * 45)
                                            + (event.getSlot() - 9);

                            if (worldIndex < 0
                                    || worldIndex >= worlds.size()) {

                                return;

                            }

                            String worldName =
                                    worlds.get(
                                            worldIndex
                                    );

                            boolean enabled =
                                    dao.getWorldSetting(
                                            worldName
                                    );

                            dao.setWorldSetting(
                                    worldName,
                                    !enabled
                            );

                            World world =
                                    Bukkit.getWorld(
                                            worldName
                                    );

                            if (world != null) {

                                world.setGameRule(
                                        GameRule.KEEP_INVENTORY,
                                        !enabled
                                );

                            }

                            player.openInventory(
                                    KeepInventoryMenu.create(
                                            holder.getPage()
                                    )
                            );

                        } catch (Exception ex) {

                            ex.printStackTrace();

                        }

                    }

                }

            }

        }

    }

}