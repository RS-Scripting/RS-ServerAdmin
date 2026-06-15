package com.rsscripting.serveradmin.listeners;

import com.rsscripting.serveradmin.RSServerAdmin;
import com.rsscripting.serveradmin.gui.CreeperBlockDamageMenu;
import com.rsscripting.serveradmin.gui.CreeperEntityDamageMenu;
import com.rsscripting.serveradmin.gui.CreeperMenu;
import com.rsscripting.serveradmin.gui.MainMenu;
import com.rsscripting.serveradmin.menuholder.RSMenuHolder;
import com.rsscripting.serveradmin.settings.SettingKey;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CreeperMenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(
            InventoryClickEvent event
    ) {

        if (!(event.getInventory().getHolder()
                instanceof RSMenuHolder holder)) {

            return;

        }

        if (event.getClickedInventory() == null) {
            return;
        }

        if (event.getClickedInventory()
                != event.getView().getTopInventory()) {

            return;

        }

        event.setCancelled(
                true
        );

        switch (holder.getMenuId()) {

            case "creeper" -> {

                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                switch (event.getSlot()) {

                    case 4 -> player.openInventory(
                            com.rsscripting.serveradmin.gui.MainMenu.create()
                    );

                    case 11 -> {

                        try {

                            player.openInventory(
                                    CreeperBlockDamageMenu.create(
                                            1
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to open Creeper Block Damage menu: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    case 15 -> {

                        try {

                            player.openInventory(
                                    CreeperEntityDamageMenu.create(
                                            1
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to open Creeper Entity Damage menu: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                }

            }

            case "CREEPER_BLOCK_DAMAGE" -> {

                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                switch (event.getSlot()) {

                    case 3 -> {

                        try {

                            player.openInventory(
                                    CreeperMenu.create()
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to open Creeper menu: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    case 5 -> {

                        try {

                            RSServerAdmin.getInstance()
                                    .getSettingsDAO()
                                    .toggleDefault(
                                            SettingKey.CREEPER_BLOCK_DAMAGE
                                    );

                            player.openInventory(
                                    CreeperBlockDamageMenu.create(
                                            holder.getPage()
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to toggle Creeper Block Damage default: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    default -> {

                        if (event.getSlot() < 9) {
                            return;
                        }

                        try {

                            if (event.getCurrentItem() == null) {
                                return;
                            }

                            if (!event.getCurrentItem().hasItemMeta()) {
                                return;
                            }

                            ItemMeta meta =
                                    event.getCurrentItem()
                                            .getItemMeta();

                            if (meta == null) {
                                return;
                            }

                            String worldName =
                                    meta.getPersistentDataContainer()
                                            .get(
                                                    new NamespacedKey(
                                                            RSServerAdmin.getInstance(),
                                                            "world_name"
                                                    ),
                                                    PersistentDataType.STRING
                                            );

                            if (worldName == null
                                    || worldName.isBlank()) {

                                return;

                            }

                            boolean enabled =
                                    RSServerAdmin.getInstance()
                                            .getCreeperSettingsDAO()
                                            .getBlockDamage(
                                                    worldName
                                            );

                            RSServerAdmin.getInstance()
                                    .getCreeperSettingsDAO()
                                    .setBlockDamage(
                                            worldName,
                                            !enabled
                                    );

                            player.openInventory(
                                    CreeperBlockDamageMenu.create(
                                            holder.getPage()
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to update Creeper Block Damage setting: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                }

            }

            case "CREEPER_ENTITY_DAMAGE" -> {

                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                switch (event.getSlot()) {

                    case 3 -> {

                        try {

                            player.openInventory(
                                    CreeperMenu.create()
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to open Creeper menu: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    case 5 -> {

                        try {

                            RSServerAdmin.getInstance()
                                    .getSettingsDAO()
                                    .toggleDefault(
                                            SettingKey.CREEPER_ENTITY_DAMAGE
                                    );

                            player.openInventory(
                                    CreeperEntityDamageMenu.create(
                                            holder.getPage()
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to toggle Creeper Entity Damage default: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                    default -> {

                        if (event.getSlot() < 9) {
                            return;
                        }

                        try {

                            if (event.getCurrentItem() == null) {
                                return;
                            }

                            if (!event.getCurrentItem().hasItemMeta()) {
                                return;
                            }

                            ItemMeta meta =
                                    event.getCurrentItem()
                                            .getItemMeta();

                            if (meta == null) {
                                return;
                            }

                            String worldName =
                                    meta.getPersistentDataContainer()
                                            .get(
                                                    new NamespacedKey(
                                                            RSServerAdmin.getInstance(),
                                                            "world_name"
                                                    ),
                                                    PersistentDataType.STRING
                                            );

                            if (worldName == null
                                    || worldName.isBlank()) {

                                return;

                            }

                            boolean enabled =
                                    RSServerAdmin.getInstance()
                                            .getCreeperSettingsDAO()
                                            .getEntityDamage(
                                                    worldName
                                            );

                            RSServerAdmin.getInstance()
                                    .getCreeperSettingsDAO()
                                    .setEntityDamage(
                                            worldName,
                                            !enabled
                                    );

                            player.openInventory(
                                    CreeperEntityDamageMenu.create(
                                            holder.getPage()
                                    )
                            );

                        } catch (Exception ex) {

                            RSServerAdmin.getInstance()
                                    .getLogger()
                                    .warning(
                                            "Failed to update Creeper Entity Damage setting: "
                                                    + ex.getMessage()
                                    );

                        }

                    }

                }

            }

        }

    }

}