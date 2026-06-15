package com.rsscripting.serveradmin.listeners;

import com.rsscripting.serveradmin.gui.*;
import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.menuholder.RSMenuHolder;
import com.rsscripting.serveradmin.settings.SettingKey;
import com.rsscripting.serveradmin.gui.MobEquipmentWorldMenu;
import com.rsscripting.serveradmin.gui.MobEquipmentSettingsMenu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public class MainMenuListener implements Listener {

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

                            boolean blockDamageDefault =
                                    RSServerAdmin.getInstance()
                                            .getSettingsDAO()
                                            .getDefault(
                                                    SettingKey.CREEPER_BLOCK_DAMAGE
                                            );

                            boolean entityDamageDefault =
                                    RSServerAdmin.getInstance()
                                            .getSettingsDAO()
                                            .getDefault(
                                                    SettingKey.CREEPER_ENTITY_DAMAGE
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

                                RSServerAdmin.getInstance()
                                        .getCreeperSettingsDAO()
                                        .ensureWorldExists(
                                                world.getName(),
                                                blockDamageDefault,
                                                entityDamageDefault
                                        );

                                RSServerAdmin.getInstance()
                                        .getEquipmentDropSettingsDAO()
                                        .ensureWorldExists(
                                                world.getName()
                                        );

                            }

                            player.sendMessage(
                                    "§aWorld scan complete."
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "World scan failed: "
                                                    + ex.getMessage()
                                    );

                            player.sendMessage(
                                    "§cWorld scan failed."
                            );

                        }

                    }

                    /*
                    | Mob Inventory Drop
                     */

                    case 13 -> {

                        try {

                            player.openInventory(
                                    MobEquipmentWorldMenu.create(
                                            1
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to open Mob Equipment menu: "
                                                    + ex.getMessage()
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

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to open Keep Inventory menu: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                }

            }

        }

    }

}